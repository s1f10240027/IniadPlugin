package tonton.iniadPlugin;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CancelEvent implements Listener {
    @EventHandler
    public void onItemFrameInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            Player player = event.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemFrameBreak(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ItemFrame) {
            if (event.getDamager() instanceof Player player) {
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onMapUse(PlayerInteractEvent event) {
        ItemStack Item = event.getItem();
        if (Item == null || Item.getType() != Material.MAP) return;
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onShulkerOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null) return;
            Material type = clickedBlock.getType();
            if (type.name().endsWith("SHULKER_BOX")) {
                ItemStack Item = event.getItem();
                if (Item == null || Item.getItemMeta() == null || !Item.getItemMeta().getDisplayName().equals("登録済みICカード")) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    player.sendMessage("§cICカードを持った状態でクリックしてください。");
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1.0f, 1.0f);
                }
            }
        }
    }
}
