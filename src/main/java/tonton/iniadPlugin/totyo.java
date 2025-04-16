package tonton.iniadPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class totyo implements Listener {

    @EventHandler
    public void onItemFrameInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame itemframe) {
            Location loc = itemframe.getLocation();
            if (loc.getX() == 53.5 && loc.getZ() == -49.96875) {
                if (loc.getY() - 67 < 0) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    Inventory inv = Bukkit.createInventory(null, 54, "メニュー");

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
                        if (i != 4) inv.setItem(i + 45, glass);
                    }
                    for (int i = 9; i < 54; i += 9) {
                        inv.setItem(i, glass);
                        inv.setItem(i-1, glass);
                    }
                    inv.setItem(49, close);
                    player.openInventory(inv);

                }
            }
        }
    }

    @EventHandler
    public void onInventoryCLick(InventoryClickEvent event) {
        InventoryView inv = event.getView();
        if (inv.getTitle().equals("メニュー")) {
            event.setCancelled(true);
        }
    }
}
