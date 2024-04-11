package satisfy.farm_and_charm.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.world.feature.Farm_And_CharmPlacedFeature;

import java.util.function.Predicate;

public class Farm_And_CharmBiomeModification {
    public static void init() {
        BiomeModification world = BiomeModifications.create(new Farm_And_CharmIdentifier("world_features"));
        Predicate<BiomeSelectionContext> spawnsWildFlowersBiomes = getFarm_And_CharmSelector("spawns_wild_flowers");
        Predicate<BiomeSelectionContext> spawnsWildEmmerBiomes = getFarm_And_CharmSelector("spawns_wild_emmer");
        Predicate<BiomeSelectionContext> spawnsWildOatBiomes = getFarm_And_CharmSelector("spawns_wild_oat");
        Predicate<BiomeSelectionContext> spawnsWildBarleyBiomes = getFarm_And_CharmSelector("spawns_wild_barley");
        Predicate<BiomeSelectionContext> spawnsWildTomatoesandCornBiomes = getFarm_And_CharmSelector("spawns_wild_tomatoes_and_corn");
        Predicate<BiomeSelectionContext> spawnsWildStrawberriesBiomes = getFarm_And_CharmSelector("spawns_wild_strawberries");
        Predicate<BiomeSelectionContext> spawnsWildCrops = getFarm_And_CharmSelector("spawns_wild_crops");
        Predicate<BiomeSelectionContext> spawnsWildPotatoes = getFarm_And_CharmSelector("spawns_wild_potatoes");

        world.add(ModificationPhase.ADDITIONS, spawnsWildPotatoes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_POTATOES_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildFlowersBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_NETTLE_PATCH_CHANCE_KEY);
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_RIBWORT_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildEmmerBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_EMMER_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildTomatoesandCornBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_TOMATOES_PATCH_CHANCE_KEY);
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_CORN_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildStrawberriesBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_STRAWBERRY_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildCrops, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_LETTUCE_PATCH_CHANCE_KEY);
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_CARROTS_PATCH_CHANCE_KEY);
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_ONIONS_PATCH_CHANCE_KEY);
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_BEETROOTS_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildOatBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_OAT_PATCH_CHANCE_KEY);
        });

        world.add(ModificationPhase.ADDITIONS, spawnsWildBarleyBiomes, ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Farm_And_CharmPlacedFeature.WILD_BARLEY_PATCH_CHANCE_KEY);
        });
    }

    private static Predicate<BiomeSelectionContext> getFarm_And_CharmSelector(String path) {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new Farm_And_CharmIdentifier(path)));
    }
}
