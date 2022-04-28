package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.gui.ui.requirement.PermissionRequirement;
import com.rainchat.rinventory.gui.ui.requirement.Requirement;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.utils.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequirementBuilder extends Builder<String, Requirement> {

    /**
     * The instance of the requirement builder
     */
    public static final RequirementBuilder INSTANCE = new RequirementBuilder();

    private RequirementBuilder() {
        registerDefaultRequirements();
    }

    private void registerDefaultRequirements() {
        register(PermissionRequirement::new, "permission");
    }

    /**
     * Build the requirement
     *
     * @param menu  the menu
     * @param type  the type of the requirement
     * @param name  the name of the requirement
     * @param value the value
     * @return the requirement
     */
    public Optional<Requirement> getRequirement(SimpleInventory menu, String type, String name, Object value) {
        return build(type, name).map(requirement -> {
            requirement.setMenu(menu);
            requirement.setValue(value);
            return requirement;
        });
    }

    public List<Requirement> getRequirement(SimpleInventory menu, Object object) {
        return CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .map(string -> {
                    System.out.println(string);
                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";

                    Requirement action = build(name.trim(), value.trim()).orElseGet(() -> new PermissionRequirement(string.trim()));
                    action.setMenu(menu);
                    return action;
                })
                .collect(Collectors.toList());
    }

}
