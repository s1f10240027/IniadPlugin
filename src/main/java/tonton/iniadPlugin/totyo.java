package tonton.iniadPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.Arrays;

public class totyo implements Listener {

    private ItemStack getMenuItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
    @EventHandler
    public void onItemFrameInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame itemframe) {
            Location loc = itemframe.getLocation();
            if (loc.getX() == 53.5 && loc.getZ() == -49.96875) {
                if (loc.getY() - 67 < 0) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    Inventory inv = Bukkit.createInventory(null, 45, "§m         §r§o 糖朝カフェ: メニュー §m         ");

                    ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta glass_meta = glass.getItemMeta();
                    glass_meta.setDisplayName(" ");
                    glass.setItemMeta(glass_meta);

                    ItemStack close = new ItemStack(Material.STRUCTURE_VOID);
                    ItemMeta void_meta = close.getItemMeta();
                    void_meta.setDisplayName("§fメニューを閉じる");
                    close.setItemMeta(void_meta);
                    for (int i = 0; i < 9; i++) {
                        inv.setItem(i, glass);
                        if (i != 4) inv.setItem(i + 36, glass);
                    }
                    for (int i = 9; i < 45; i += 9) {
                        inv.setItem(i, glass);
                        inv.setItem(i-1, glass);
                    }
                    inv.setItem(40, close);


                    ItemStack item1 = getMenuItem(Material.MUSHROOM_STEW, "§b東洋大粥", "§f → 550円");
                    ItemStack item2 = getMenuItem(Material.RABBIT_STEW, "§bサンラータン麺", "§f → 550円");
                    ItemStack item3 = getMenuItem(Material.COOKED_CHICKEN, "§b油淋鶏", "§f → 550円");
                    ItemStack item4 = getMenuItem(Material.COOKIE, "§bあんかけきのこ堅焼きそば", "§f → 600円");
                    ItemStack item5 = getMenuItem(Material.SNOWBALL, "§b週替わり丼", "§f → 550円");

                    ItemStack item6 = getMenuItem(Material.HONEYCOMB, "§bマンゴプリン", "§f → 300円");
                    ItemStack item7 = getMenuItem(Material.SUGAR, "§b杏仁豆腐", "§f → 250円");
                    ItemStack item8 = getMenuItem(Material.CLAY_BALL, "§b丼大盛ご飯", "§f → 130円");

                    ItemStack item9 = getMenuItem(Material.BROWN_CANDLE, "§bコーヒー (ホット / アイス)", "§f → 150円");
                    ItemStack item10 = getMenuItem(Material.ORANGE_CANDLE, "§bウーロン茶 (ホット / アイス)", "§f → 150円");
                    ItemStack item11 = getMenuItem(Material.YELLOW_CANDLE, "§bジャスミン茶 (ホット / アイス)", "§f → 150円");

                    inv.setItem(11, item1);
                    inv.setItem(12, item2);
                    inv.setItem(13, item3);
                    inv.setItem(14, item4);
                    inv.setItem(15, item5);
                    inv.setItem(20, item6);
                    inv.setItem(21, item7);
                    inv.setItem(24, item8);
                    inv.setItem(30, item9);
                    inv.setItem(31, item10);
                    inv.setItem(32, item11);
                    player.openInventory(inv);
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 0);

                }
            }
        }
    }

    @EventHandler
    public void onInventoryCLick(InventoryClickEvent event) {
        InventoryView inv = event.getView();
        if (inv.getTitle().equals("§m         §r§o 糖朝カフェ: メニュー §m         ")) {
            Player player = (Player) event.getWhoClicked();
            ItemStack ClickedItem = event.getCurrentItem();
            if (ClickedItem == null) return;
            event.setCancelled(true);
            if (event.getRawSlot() >= inv.getTopInventory().getSize()) return;
            Material ItemType = ClickedItem.getType();
            if (ItemType == Material.STRUCTURE_VOID) {
                player.closeInventory();
            } else if (ItemType != Material.GRAY_STAINED_GLASS_PANE){
                if (event.getSlot() < 45) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    ItemStack item = ClickedItem.clone();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(Arrays.asList("§f糖朝CAFEで買ったもの"));
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                }
            }
        }
    }
}
