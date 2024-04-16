package satisfy.farm_and_charm.registry;

import net.minecraft.client.model.geom.ModelLayerLocation;
import satisfy.farm_and_charm.FarmAndCharmIdentifier;

public enum ModelRegistry {
    ;
    public static final ModelLayerLocation CART = new ModelLayerLocation(new FarmAndCharmIdentifier("cart"), "main");
    public static final ModelLayerLocation PLOW = new ModelLayerLocation(new FarmAndCharmIdentifier("plow"), "main");
}
