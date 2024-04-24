package satisfy.farm_and_charm.client.gui;

import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfy.farm_and_charm.util.FarmAndCharmIdentifier;
import satisfy.farm_and_charm.client.gui.handler.StoveGuiHandler;
import satisfy.farm_and_charm.client.recipebook.StoveRecipeBook;

@Environment(EnvType.CLIENT)
public class StoveGui extends AbstractRecipeBookGUIScreen<StoveGuiHandler> {
    public static final ResourceLocation BG = new FarmAndCharmIdentifier("textures/gui/stove_gui.png");

    public static final int ARROW_X = 93;
    public static final int ARROW_Y = 32;
    public StoveGui(StoveGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, new StoveRecipeBook(), BG);
    }

    @Override
    protected void init() {
        this.titleLabelX += 2;
        this.titleLabelY += -3;
        super.init();
    }

    @Override
    public void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(18);
        guiGraphics.blit(BG,leftPos + 93, topPos + 32, 178, 20, progress, 25);
    }

    @Override
    public void renderBurnIcon(GuiGraphics guiGraphics, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
            guiGraphics.blit(BG, posX + 62, posY + 49, 176, 0, 17, 15);
        }
    }
}