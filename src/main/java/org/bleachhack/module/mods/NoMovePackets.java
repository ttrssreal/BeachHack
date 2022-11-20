
package org.bleachhack.module.mods;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import org.bleachhack.event.events.EventPacket;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;

public class NoMovePackets extends Module {

    public NoMovePackets() {
        super("No Move", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Stops sending movement packets.");
    }

    @BleachSubscribe
    public void onPacketSend(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket move_packet) {
            event.setCancelled(true);
        } else if (event.getPacket() instanceof VehicleMoveC2SPacket move_packet) {
            event.setCancelled(true);
        }
    }

}