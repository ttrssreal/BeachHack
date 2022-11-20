
package org.bleachhack.module.mods;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.bleachhack.event.events.EventTick;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;

public class WorldGuardAreaBypass extends Module {

    public WorldGuardAreaBypass() {
        super("WG Area Bypass", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Bypasses area restriction from WorldGuard.");
    }

    @BleachSubscribe
    public void onTick(EventTick event) {
        mc.player.setVelocity(Vec3d.ZERO);
        // sqrt(1/256) = 1/16
        double step_size = 1/16d;
        Vec3d move = Vec3d.ZERO;
        Vec3d forward = new Vec3d(0, 0, step_size).rotateY(-(float) Math.toRadians(mc.player.getYaw()));
        Vec3d strafe = forward.rotateY((float) Math.toRadians(90));
        if (mc.options.jumpKey.isPressed()) {
            move = mc.player.getPos().add(0, 0.05d, 0);
        } else if (mc.options.sneakKey.isPressed()) {
            move = mc.player.getPos().add(0, -0.05d, 0);
        } else if (mc.options.backKey.isPressed()) {
            move = mc.player.getPos().add(-forward.x, 0, -forward.z);
        } else if (mc.options.forwardKey.isPressed()) {
            move = mc.player.getPos().add(forward.x, 0, forward.z);
        } else if (mc.options.leftKey.isPressed()) {
            move = mc.player.getPos().add(strafe.x, 0, strafe.z);
        } else if (mc.options.rightKey.isPressed()){
            move = mc.player.getPos().add(-strafe.x, 0, -strafe.z);
        }
        if (!move.equals(Vec3d.ZERO)) {
            mc.player.setPos(move.x, move.y, move.z);
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(move.x, move.y, move.z, true));
                                                                                                        // funny number (proven optimal)
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(move.x, -69, move.z, true));
        }
    }
}