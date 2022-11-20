
package org.bleachhack.module.mods;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import org.bleachhack.event.events.EventPacket;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;

public class BotMovement extends Module {

    public BotMovement() {
        super("BotMovement", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Round your position (LO server challenge).");
    }

    @BleachSubscribe
    public void onPacketLeave(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket move_packet) {
            if (move_packet instanceof PlayerMoveC2SPacket.PositionAndOnGround || move_packet instanceof PlayerMoveC2SPacket.Full) {
                double x = Math.round(move_packet.getX(0)*100)/100.0;
                double z = Math.round(move_packet.getZ(0)*100)/100.0;
                double x_double = (double) Math.nextAfter(x, x + Math.signum(x));
                double z_double = (double) Math.nextAfter(z, z + Math.signum(z));
                move_packet.x = x_double;
                move_packet.z = z_double;
                event.setPacket(move_packet);
            }
        } else if (event.getPacket() instanceof VehicleMoveC2SPacket move_packet) {
            double x = Math.round(move_packet.getX()*100)/100.0;
            double z = Math.round(move_packet.getZ()*100)/100.0;
            double x_double = (double) Math.nextAfter(x, x + Math.signum(x));
            double z_double = (double) Math.nextAfter(z, z + Math.signum(z));
            move_packet.x = x;
            move_packet.z = z;
            event.setPacket(move_packet);
        }
    }
}