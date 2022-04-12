package draylar.intotheomega.impl.event.client.color;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.gen.random.SimpleRandom;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CrystaliteColorProvider implements BlockColorProvider {

    private final SimplexNoiseSampler noise = new SimplexNoiseSampler(new SimpleRandom(new Random().nextLong()));

    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        double rSample =(noise.sample((pos.getX() + 5000) / 100f, (pos.getZ() - 500) / 100f) + 1) / 2;
        double sample = (noise.sample(pos.getX() / 100f, pos.getZ() / 100f) + 1) / 2;
        double bSample = (noise.sample((pos.getX() - 5000) / 100f, (pos.getZ() + 500) / 100f) + 1) / 2;

        int r = Math.min(255, Math.max(0, (int) (rSample * 255)));
        int g = Math.min(255, Math.max(0, (int) (sample * 255)));
        int b = Math.min(255, Math.max(0, (int) (bSample * 255)));
        return Integer.parseInt(String.format("%02x%02x%02x", r, g, b), 16);
    }
}
