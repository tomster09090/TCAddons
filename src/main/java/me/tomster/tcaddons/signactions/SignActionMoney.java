package me.tomster.tcaddons.signactions;

import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.bergerkiller.bukkit.tc.signactions.SignActionType;
import com.bergerkiller.bukkit.tc.utils.SignBuildOptions;

/**
 * Displays a welcome message to the players in the train
 */
public class SignActionMoney extends SignAction {

    @Override
    public boolean match(SignActionEvent info) {
        // Checks that the second line starts with 'money' (case-insensitive)
        return info.isType("money");
    }

    @Override
    public void execute(SignActionEvent info) {
        /* When a [train] sign is placed, activate when powered by redstone when the train
        goes over the sign, or when redstone is activated.*/
        if (info.isTrainSign()
                && info.isAction(SignActionType.GROUP_ENTER, SignActionType.REDSTONE_ON)
                && info.isPowered() && info.hasGroup()
        ) {
            for (MinecartMember<?> member : info.getGroup()){
                playPlayersInCart(info, member);
            }
            return;
        }

        // When a [cart] sign is placed, activate when powered by redstone when each cart
        // goes over the sign, or when redstone is activated.
        if (info.isCartSign()
                && info.isAction(SignActionType.MEMBER_ENTER, SignActionType.REDSTONE_ON)
                && info.isPowered() && info.hasMember()
        ) {
            playPlayersInCart(info, info.getMember());
            return;
        }
    }

    public void playPlayersInCart(SignActionEvent info, MinecartMember<?> member) {
        // Lines 3 and 4 configure the message to send
        String message = info.getLine(2) + info.getLine(3);

        // Send to all player passengers of the cart (could be multiple seats!)
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + member.getLocalizedName() + " " + info.getLine(2));
    }

    @Override
    public boolean build(SignChangeActionEvent event) {
        // This is executed when a player places down a sign matching this sign action
        // Permissions are checked and then a message is displayed to the player
        // For simplicity you can use the SignBuildOptions API for this.
        // You are free to use your own code here that checks permissions/etc.
        return SignBuildOptions.create()
                .setName(event.isCartSign() && event.isRCSign() ? "cart money distributor" : "train money distributor")
                .setDescription("pays all players within the train/cart a specified amount")
                .handle(event.getPlayer());
    }
}