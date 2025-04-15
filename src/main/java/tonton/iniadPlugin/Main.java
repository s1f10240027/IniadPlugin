package tonton.iniadPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[IniadPlugin] プラグインが有効になりました。");

        getServer().getPluginManager().registerEvents(new CancelEvent(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[IniadPlugin] プラグインが無効になりました。");
    }
}
