package tonton.iniadPlugin;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor {
    private final JavaPlugin plugin;

    public Commands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (command.getName().equals("InvisibleFrame")) {
            player.performCommand("give @s item_frame{EntityTag:{Invisible:1b}}");
            return true;
        }

        if (command.getName().equals("gm")) {
            if (args.length == 0) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.setGameMode(GameMode.ADVENTURE);
                } else {
                    player.setGameMode(GameMode.CREATIVE);
                }
            } else {
                switch (args[0]) {
                    case "0", "survival", "s" -> player.setGameMode(GameMode.SURVIVAL);
                    case "1", "creative", "c" -> player.setGameMode(GameMode.CREATIVE);
                    case "2", "adventure", "a" -> player.setGameMode(GameMode.ADVENTURE);
                    case "3", "spectator", "sp", "spec" -> player.setGameMode(GameMode.SPECTATOR);
                    default -> player.sendMessage("§c不明な引数です。");
                }
            }
            return true;
        }
        return false;
    }

}
