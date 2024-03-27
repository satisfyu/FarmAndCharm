package example.modname.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import example.modname.Modname;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Modname.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MODNAME_TAB = CREATIVE_MODE_TABS.register("modname", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .icon(() -> new ItemStack(Items.GRASS))
            .title(Component.translatable("creativetab.modname.tab"))
            .displayItems((parameters, out) -> {

            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
