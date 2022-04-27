package com.rainchat.rinventory.gui.ui.pagination;

import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import com.rainchat.rinventory.gui.ui.items.button.SimpleItem;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SimplePagination implements BasePagination {

    private SimpleInventory simpleMenu;
    private Player player;

    private int page;
    private List<Integer> itemSlots;
    private List<SimpleItem> paginationItems;

    @Override
    public SimpleInventory getMenu() {
        return simpleMenu;
    }

    @Override
    public void setMenu(SimpleInventory menu) {
        this.simpleMenu = menu;
    }

    abstract public void setupItems();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void nextPage() {
        this.setPage(this.page + 1);
    }

    public void previousPage() {
        this.setPage(this.page - 1);
    }

    public void firstPage() {
        this.setPage(0);
    }

    public void lastPage() {
        this.setPage(this.getLastPage());
    }

    public int getPage() {
        return this.page;
    }

    public int getFirstPage() {
        return 0;
    }

    public List<SimpleItem> getPaginationItems() {
        return this.paginationItems;
    }

    public List<Integer> getItemSlots() {
        return this.itemSlots;
    }

    public int getLastPage() {
        int m = (int) Math.ceil((double) getPaginationItems().size() / getItemSlots().size()) - 1;
        return m != -1 ? m : 0;
    }

    public void setPage(int page) {
        if (this.paginationItems.size() == 0) return;
        else if (this.itemSlots.size() == 0) return;

        int oldPage = this.page;
        this.page = page;
        if (!getMenu().updateInventory()) this.page = oldPage;
    }

    public void setItemSlots(List<Integer> ints) {
        this.itemSlots = ints;
    }

    public void setItems(List<SimpleItem> clickableItems) {
        this.paginationItems = clickableItems;
    }

    public boolean isLastPage() {
        return this.page == this.getLastPage();
    }

    public boolean isFirstPage() {
        return this.page == 0;
    }
}
