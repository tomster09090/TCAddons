package me.tomster.tcaddons;

import com.bergerkiller.bukkit.tc.signactions.SignAction;
import me.tomster.tcaddons.signactions.SignActionMoney;
import org.bukkit.plugin.java.JavaPlugin;

public final class TCExtra extends JavaPlugin {

    public final SignActionMoney signActionMoney = new SignActionMoney();

    @Override
    public void onEnable() {
        // Plugin startup logic
        SignAction.register(signActionMoney);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SignAction.unregister(signActionMoney);
    }
}
