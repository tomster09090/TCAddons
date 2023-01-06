package me.tomster.tcaddons.signactions;

import com.bergerkiller.bukkit.tc.Permission;
import com.bergerkiller.bukkit.tc.TrainCarts;
import net.milkbowl.vault.economy.Economy;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.bergerkiller.bukkit.tc.utils.SignBuildOptions;
public class SignActionMoney extends SignAction {

    @Override
    public boolean match(SignActionEvent info) { return info.isType("money");  }

    @Override
    public void execute(SignActionEvent info) {
        payPlayersInCart(info);
    }

    public void payPlayersInCart(SignActionEvent info ) {
        // Line 3 configured to read out money values.
        int moneyamount = Integer.parseInt(info.getLine(3));

        // Send to all player passengers of the cart
        // Set up vault economy to be able to pay players within a train.
        Economy eco = TrainCarts.plugin.getEconomy();
        eco.bankDeposit(info.getMember().getLocalizedName(), moneyamount);
    }

    @Override
    public boolean build(SignChangeActionEvent event) {
        // This is executed when a player places down a sign matching this sign action
        // Permissions are checked and then a message is displayed to the player
        // For simplicity you can use the SignBuildOptions API for this.
        // You are free to use your own code here that checks permissions/etc.
        return SignBuildOptions.create()
                .setPermission(String.valueOf(Permission.valueOf("BUILD_MONEY")))
                .setName("money sign")
                .setDescription("pays players within a train")
                .setTraincartsWIKIHelp("github.io/tomster09090/TCCAddons/Readme.md")
                .handle(event.getPlayer());
    }
/*
    public TrainCarts getTrainCarts() {
        return TrainCarts.plugin;
    }
*/}