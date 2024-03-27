package example.modname.registry;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.Map;

public class CustomArmorRegistry {
    public static void registerArmorModelLayers(){
    }
    public static  <T extends LivingEntity> void registerHatModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
    }
}