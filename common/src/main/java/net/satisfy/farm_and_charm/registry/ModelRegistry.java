package net.satisfy.farm_and_charm.registry;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.satisfy.farm_and_charm.util.FarmAndCharmIdentifier;

public enum ModelRegistry {
    ;
    public static final ModelLayerLocation CART = new ModelLayerLocation(new FarmAndCharmIdentifier("cart"), "main");
    public static final ModelLayerLocation PLOW = new ModelLayerLocation(new FarmAndCharmIdentifier("plow"), "main");

    public static final ModelLayerLocation TEST_CART = new ModelLayerLocation(new FarmAndCharmIdentifier("test_cart"), "main");
    public static final ModelLayerLocation TEST_PLOW = new ModelLayerLocation(new FarmAndCharmIdentifier("test_plow"), "main");
}
