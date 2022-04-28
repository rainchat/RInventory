package com.rainchat.rinventory.gui.builder;





import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.gui.ui.pagination.FromConfigPagination;
import com.rainchat.rinventory.gui.ui.pagination.OnlinePagination;
import com.rainchat.rinventory.utils.builder.Builder;

import java.util.Map;
import java.util.Optional;

public class PaginationBuilder extends Builder<String, FromConfigPagination> {

    public static final PaginationBuilder INSTANCE = new PaginationBuilder();

    private PaginationBuilder() {
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        register(s -> new OnlinePagination(), "online-players", "online");

    }


    public FromConfigPagination getPagination(SimpleInventory inventory, Map<String, Object> keys) {

        FromConfigPagination pagination = Optional.ofNullable(keys.get("menu-settings.menu-type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, "name"))
                .orElseGet(() -> build("online-players", "name").orElse(null));

        if (pagination != null) {
            pagination.setMenu(inventory);
            pagination.setFromSection(keys);

        }
        return pagination;
    }
}