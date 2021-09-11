package draylar.intotheomega.mixin.world;

import com.google.common.collect.Lists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(EndSpikeFeature.SpikeCache.class)
public class EndPillarSpikeCacheMixin {

    /**
     * @author Draylar
     */
    @Overwrite
    public List<EndSpikeFeature.Spike> load(Long seed) {
        List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Collections.shuffle(list, new Random(seed));
        List<EndSpikeFeature.Spike> spikes = Lists.newArrayList();

        for(int i = 0; i < 10; ++i) {
            // Determine X/Y position of the spike. Into The Omega moves the spikes out (42.0D => 64.0D).
            int x = MathHelper.floor(64.0D * Math.cos(2.0D * (-3.141592653589793D + 0.3141592653589793D * (double)i)));
            int z = MathHelper.floor(64.0D * Math.sin(2.0D * (-3.141592653589793D + 0.3141592653589793D * (double)i)));
            int index = list.get(i);
            int radius = 2 + index / 3;

            // ITO has higher pillars (76 base => 100).
            int height = 100 + index * 3;

            boolean guarded = index == 1 || index == 2;
            spikes.add(new EndSpikeFeature.Spike(x, z, radius, height, guarded));
        }

        return spikes;
    }
}
