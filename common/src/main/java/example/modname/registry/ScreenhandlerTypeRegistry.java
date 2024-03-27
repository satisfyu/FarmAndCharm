package example.modname.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import example.modname.Modname;
import example.modname.ModnameIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ScreenhandlerTypeRegistry {

    private static final Registrar<MenuType<?>> MENU_TYPES = DeferredRegister.create(Modname.MOD_ID, Registries.MENU).getRegistrar();

    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType){
        return MENU_TYPES.register(new ModnameIdentifier(name), menuType);
    }

    public static void init() {
    }
}
