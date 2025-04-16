package tonton.iniadPlugin;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("プラグインが有効になりました。");

        getCommand("InvisibleFrame").setExecutor(new Commands(this));
        getCommand("getNBT").setExecutor(new Commands(this));

        getServer().getPluginManager().registerEvents(new CancelEvent(), this);
        getServer().getPluginManager().registerEvents(new totyo(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("プラグインが無効になりました。");
    }
}
