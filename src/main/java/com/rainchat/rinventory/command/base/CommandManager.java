/*
 * MIT License
 *
 * Copyright (c) 2019 Matt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.rainchat.rinventory.command.base;

import com.rainchat.rinventory.command.annotations.Alias;
import com.rainchat.rinventory.command.annotations.Command;
import com.rainchat.rinventory.command.exceptions.CmException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("unused")
public final class CommandManager implements Listener {

    // The plugin's main class
    private final Plugin plugin;

    // The command map
    private final CommandMap commandMap;

    // List of commands;
    private final Map<String, CommandHandler> commands = new HashMap<>();
    private Map<String, org.bukkit.command.Command> bukkitCommands = new HashMap<>();

    // The parameter handler
    private final ParameterHandler parameterHandler = new ParameterHandler();
    // The completion handler
    private final CompletionHandler completionHandler = new CompletionHandler();
    // The messages handler
    private final MessageHandler messageHandler = new MessageHandler();

    // If should or not hide tab complete for no permissions
    private boolean hideTab;

    /**
     * me.mattstudios.mf.Main constructor for the manager
     *
     * @param plugin The plugin's main class
     */
    public CommandManager(final Plugin plugin) {
        this(plugin, false);
    }

    /**
     * Constructor for the manager
     *
     * @param plugin  The plugin's main class
     * @param hideTab If should or not hide tab
     */
    public CommandManager(final Plugin plugin, final boolean hideTab) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.hideTab = hideTab;

        this.commandMap = getCommandMap();
    }

    /**
     * Gets the parameter types class to register new ones and to check too.
     *
     * @return The parameter types class.
     */
    public ParameterHandler getParameterHandler() {
        return parameterHandler;
    }

    /**
     * Gets the completion handler class, which handles all the command completions in the plugin.
     *
     * @return The completion handler.
     */
    public CompletionHandler getCompletionHandler() {
        return completionHandler;
    }

    /**
     * Gets the message handler, which handles all the messages autogenerated by the framework.
     *
     * @return The message handler.
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Registers commands.
     *
     * @param commands The list of command classes to register.
     */
    public void register(final CommandBase... commands) {
        for (CommandBase command : commands) {
            register(command);
        }
    }

    /**
     * Registers a command.
     *
     * @param command The command class to register.
     */
    public void register(final CommandBase command) {
        // Calls the code to run on command register
        command.onRegister();

        final Class<?> commandClass = command.getClass();

        // Checks for the command annotation.
        if (!commandClass.isAnnotationPresent(Command.class)) {
            throw new CmException("Class " + command.getClass().getName() + " needs to have @Command!");
        }

        // Gets the command annotation value.
        final String commandName = commandClass.getAnnotation(Command.class).value();
        // Gets the aliases from the setAlias method
        final List<String> aliases = command.getAliases();

        //Checks if the class has some alias and adds them.
        if (commandClass.isAnnotationPresent(Alias.class)) {
            aliases.addAll(Arrays.asList(commandClass.getAnnotation(Alias.class).value()));
        }

        org.bukkit.command.Command oldCommand = commandMap.getCommand(commandName);

        // From ACF
        // To allow commands to be registered on the plugin.yml
        if (oldCommand instanceof PluginIdentifiableCommand && ((PluginIdentifiableCommand) oldCommand).getPlugin() == this.plugin) {
            bukkitCommands.remove(commandName);
            oldCommand.unregister(commandMap);
        }

        // Used to get the command map to register the commands.
        try {
            final CommandHandler commandHandler;
            if (commands.containsKey(commandName)) {
                commands.get(commandName).addSubCommands(command);
                return;
            }

            // Sets the message handler to be used in the command class
            command.setMessageHandler(messageHandler);

            // Creates the command handler
            commandHandler = new CommandHandler(parameterHandler, completionHandler,
                    messageHandler, command, commandName, aliases, hideTab);

            // Registers the command
            commandMap.register(commandName, plugin.getName(), commandHandler);

            // Puts the handler in the list to unregister later.
            commands.put(commandName, commandHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether you want to hide or not commands from tab completion if players don't have permission to use them.
     *
     * @param hideTab Hide or Not.
     */
    public void hideTabComplete(final boolean hideTab) {
        this.hideTab = hideTab;

        for (final String cmdName : commands.keySet()) {
            commands.get(cmdName).setHideTab(hideTab);
        }
    }

    /**
     * Unregisters all the commands on the disable of the plugin.
     *
     * @param event PluginDisableEvent.
     */
    @EventHandler
    public void onPluginDisable(final PluginDisableEvent event) {
        if (event.getPlugin() != plugin) return;
        unregisterAll();
    }

    /**
     * Unregisters all commands.
     */
    private void unregisterAll() {
        commands.values().forEach(command -> command.unregister(commandMap));
    }

    /**
     * Gets the Command Map to register the commands
     *
     * @return The Command Map
     */
    private CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            final Server server = Bukkit.getServer();
            final Method getCommandMap = server.getClass().getDeclaredMethod("getCommandMap");
            getCommandMap.setAccessible(true);

            commandMap = (CommandMap) getCommandMap.invoke(server);

            final Field bukkitCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            bukkitCommands.setAccessible(true);

            //noinspection unchecked
            this.bukkitCommands = (Map<String, org.bukkit.command.Command>) bukkitCommands.get(commandMap);
        } catch (final Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not get Command Map, Commands won't be registered!");
        }

        return commandMap;
    }
}
