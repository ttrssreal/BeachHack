
package org.bleachhack.module.mods;

import net.minecraft.network.packet.s2c.play.*;
import org.bleachhack.event.events.EventPacket;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;
import org.bleachhack.setting.module.SettingToggle;

public class LiveOverflowBypass extends Module {

    String seed = "64149200";

    public LiveOverflowBypass() {
        super("LiveOverflow", KEY_UNBOUND, ModuleCategory.MISC, "Bypasses stuff for the LO server. WB and creative mode",
                new SettingToggle("Block gamestate", true).withDesc("Stops demo mode & creative packets when joining the server."),
                new SettingToggle("Block border", true).withDesc("Stops border init packets when joining the server."));
    }

    @BleachSubscribe
    public void onReadPacket(EventPacket.Read event) {
        if (getSetting(0).asToggle().getState()) {
            if (event.getPacket() instanceof GameStateChangeS2CPacket gsc_packet) {
                if (gsc_packet.getReason() == GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN) {
                    event.setCancelled(true);
                }
                if (gsc_packet.getReason() == GameStateChangeS2CPacket.GAME_MODE_CHANGED) {
                    // creative
                    if (gsc_packet.getValue() == 1) {
                        event.setCancelled(true);
                    }
                }
                if (gsc_packet.getReason() == GameStateChangeS2CPacket.GAME_WON) {
                    if (gsc_packet.getValue() == 1) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if (getSetting(1).asToggle().getState()) {
            if (event.getPacket() instanceof WorldBorderInitializeS2CPacket) {
                event.setCancelled(true);
            }
        }
    }
}