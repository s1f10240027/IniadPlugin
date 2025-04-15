package tonton.iniadPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("プラグインが有効になりました。");

        getServer().getPluginManager().registerEvents(new CancelEvent(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("プラグインが無効になりました。");
    }
}
