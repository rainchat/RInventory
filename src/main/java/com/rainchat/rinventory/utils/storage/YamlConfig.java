package com.rainchat.rinventory.utils.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class YamlConfig implements Config{

    /**
     * Creates file and include resource to in it.
     *
     * @param file         File
     * @param resourceName resource from resources
     * @return YamlConfig class
     */
    
    public static YamlConfig create(JavaPlugin plugin, File file, String resourceName) {
        Validate.notNull(plugin, "plugin cannot be null!");
        Validate.notNull(file, "file cannot be null!");
        Validate.notNull(resourceName, "resourceName cannot be null!");

        try {
            if (!file.exists()) {
                YamlConfig.createFile(file.getPath());

                InputStream inputStream = plugin.getClass().getResourceAsStream("/" + resourceName);
                if (inputStream != null) FileUtils.copyInputStreamToFile(inputStream, file);
            }

            return new YamlConfig(file);
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Creates file and include resource to in it.
     *
     * @param path         File url
     * @param resourceName resource from resources
     * @return YamlConfig class
     */
    
    public static YamlConfig create(JavaPlugin plugin, String path, String resourceName) {
        return YamlConfig.create(plugin, new File(plugin.getDataFolder() + "/" + path), resourceName);
    }

    /**
     * Creates file.
     *
     * @param file File
     * @return YamlConfig class
     */
    
    public static YamlConfig create(File file) {
        YamlConfig.createFile(Objects.requireNonNull(file, "file cannot be null!").getPath());
        return new YamlConfig(file);
    }

    /**
     * Creates file.
     *
     * @param path File url
     * @return YamlConfig class
     */
    
    public static YamlConfig create(String path) {
        return YamlConfig.create(new File(path));
    }

    /**
     * Creates file.
     *
     * @param path File path
     */
    
    public static File createFile(String path) {
        Validate.notNull(path, "path cannot be null!");

        if (new File(path).exists()) return new File(path);
        path = path.replace("/", "\\");
        String[] sp = path.split(Pattern.quote("\\"));
        String folder = path.substring(0, path.length() - sp[sp.length - 1].length());

        try {
            new File(folder.replace("\\", "/")).mkdirs();
            new File(path.replace("\\", "/")).createNewFile();
            return new File(path.replace("\\", "/"));
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
    }


    private final File file;
    private final FileConfiguration fileConfiguration;

    /**
     * Create instance of this class
     *
     * @param file file
     */
    public YamlConfig(File file) {
        this.file = Objects.requireNonNull(file, "file cannot be null!");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Gets file
     *
     * @return file
     */
    
    public File getFile() {
        return this.file;
    }

    /**
     * Gets file configuration
     *
     * @return file configuration
     */
    
    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }

    /**
     * Saves yml
     */
    public void save() {
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads yml from cache
     */
    public void reload() {
        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes yml.
     *
     * @return If yml removes successfully, returns true
     */
    public boolean delete() {
        return this.file.delete();
    }

    public Object get(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.get(path);
    }

    public Object get(String path, Object def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.get(path, def);
    }

    public void set(String path, Object value) {
        Validate.notNull(path, "path cannot be null!");
        this.fileConfiguration.set(path, value);
    }

    public boolean isSet(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isSet(path);
    }

    public String getString(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getString(path);
    }

    public String getString(String path, String def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getString(path, def);
    }

    public boolean isString(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isString(path);
    }

    public int getInt(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getInt(path);
    }

    public int getInt(String path, int def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getInt(path, def);
    }

    public boolean isInt(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isInt(path);
    }

    public boolean getBoolean(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getBoolean(path, def);
    }

    public boolean isBoolean(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isBoolean(path);
    }

    public double getDouble(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getDouble(path);
    }

    public double getDouble(String path, double def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getDouble(path, def);
    }

    public boolean isDouble(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isDouble(path);
    }

    public long getLong(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getLong(path);
    }

    public long getLong(String path, long def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getLong(path, def);
    }

    public boolean isLong(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isLong(path);
    }

    public List<?> getList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getList(path, def);
    }

    public boolean isList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isList(path);
    }

    public List<String> getStringList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getIntegerList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getBooleanList(path);
    }

    public List<Double> getDoubleList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getDoubleList(path);
    }

    public List<Float> getFloatList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getFloatList(path);
    }

    public List<Long> getLongList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getLongList(path);
    }

    public List<Byte> getByteList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getCharacterList(path);
    }

    public List<Short> getShortList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getMapList(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getOfflinePlayer(path);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getOfflinePlayer(path, def);
    }

    public boolean isOfflinePlayer(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isOfflinePlayer(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.getConfigurationSection(path);
    }

    public boolean isConfigurationSection(String path) {
        Validate.notNull(path, "path cannot be null!");
        return this.fileConfiguration.isConfigurationSection(path);
    }

    @Override
    public Map<String, Object> getValues(String path, boolean deep) {
        if (path == null || path.isEmpty()) {
            return this.getValues(deep);
        } else {
            return Optional.ofNullable(this.getConfigurationSection(path))
                    .map(configurationSection -> configurationSection.getValues(deep))
                    .orElse(Collections.emptyMap());
        }
    }

    @Override
    public Object normalize(Object object) {
        if (object instanceof ConfigurationSection) {
            return ((ConfigurationSection) object).getValues(false);
        }
        return object;
    }

    @Override
    public boolean isNormalizable(Object object) {
        return object instanceof ConfigurationSection;
    }

}