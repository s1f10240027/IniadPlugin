package tonton.iniadPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import java.util.List;
import java.util.Objects;

public class sitdown implements Listener {

    private final JavaPlugin plugin;

    public sitdown(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void SitOnBlocks(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block clicked = event.getClickedBlock();
        if (clicked == null) return;

        Material type = clicked.getType();
        if (type == Material.IRON_TRAPDOOR || type.toString().contains("STAIRS")) {

            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) return;
            Location loc1 = player.getLocation();
            Location loc2 = clicked.getLocation().add(0.5, 0.5, 0.5);
            if (loc1.distance(loc2) > 4) return;
            if (player.isSneaking()) return;
            if (loc1.getY() + 1 < loc2.getY()) return;

            Location sitloc = clicked.getLocation();
            if (type.toString().contains("STAIRS")) {
                Stairs stairs = (Stairs) clicked.getBlockData();
                if (stairs.getHalf() == Bisected.Half.TOP) return;
                sitloc.add(0.5, 0.5, 0.5);
                BlockData blockdata = clicked.getBlockData();
                Directional directional = (Directional) blockdata;
                BlockFace facing = directional.getFacing();
                float yaw = 0;

                switch (facing) {
                    case NORTH:
                        yaw = 0;  // 北
                        break;
                    case EAST:
                        yaw = 90;  // 東
                        break;
                    case SOUTH:
                        yaw = 180;  // 南
                        break;
                    case WEST:
                        yaw = 270;  // 西
                        break;
                    default:
                        break;
                }
                sitloc.setYaw(yaw);
            }

            if (clicked.getBlockData() instanceof TrapDoor td) {

                if (td.getHalf() == Bisected.Half.TOP) {
                    sitloc.add(0.5, 1, 0.5);
                    float facing_y = player.getLocation().getYaw() + 180;
                    if (facing_y > 360) facing_y -= 360;
                    sitloc.setYaw((facing_y));
                } else return;
            }

            for (Entity entity : Objects.requireNonNull(sitloc.getWorld()).getEntities()) {
                if (entity instanceof ArmorStand && entity.getLocation().equals(sitloc)) return;
            }


            Location hiddenLoc = clicked.getLocation().clone();
            hiddenLoc.setY(0);



            ArmorStand seat = (ArmorStand) clicked.getWorld().spawnEntity(hiddenLoc, EntityType.ARMOR_STAND);
            seat.setCustomName("seat: " + player.getName());
            seat.setCustomNameVisible(false);
            seat.setInvisible(true);
            seat.setVisible(false);
            seat.setMarker(true);
            seat.setGravity(false);
            seat.setSmall(true);
            seat.setSilent(true);

            seat.teleport(sitloc);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                seat.addPassenger(player);
            }, 3L);
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        Entity entity = event.getEntity();               // プレイヤー
        Entity dismounted = event.getDismounted();       // ArmorStand
        if (entity instanceof Player && dismounted instanceof ArmorStand stand) {
            String name = "seat: " + ((Player) entity).getDisplayName();
            if (name.equals(stand.getCustomName())) {
                Bukkit.getScheduler().runTaskLater(plugin, stand::remove, 1L);
            }
        }
    }

}
