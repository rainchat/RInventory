package com.rainchat.rinventory.gui.ui.button;

public abstract class CommandAction extends BaseAction {
    /**
     * Create a new action
     *
     * @param string the action string
     */
    protected CommandAction(String string) {
        super(string);
    }

    /**
     * Get the final command to use in the dispatch method
     *
     * @param string the string
     *
     * @return the final command
     */
    protected String getFinalCommand(String string) {
        return string.startsWith("/") ? string : "/" + string;
    }
}
