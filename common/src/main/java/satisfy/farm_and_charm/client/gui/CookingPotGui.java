package satisfy.farm_and_charm.client.gui;


import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.client.gui.handler.CookingPotGuiHandler;
import satisfy.farm_and_charm.client.recipebook.CookingPotRecipeBook;

@Environment(EnvType.CLIENT)
public class CookingPotGui extends AbstractRecipeBookGUIScreen<CookingPotGuiHandler> {
    public static final ResourceLocation BACKGROUND;

    public static final int ARROW_X = 95;
    public static final int ARROW_Y = 14;

    public CookingPotGui(CookingPotGuiHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title, new CookingPotRecipeBook(), BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX += 20;
    }

    @Override
    public void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.blit(BACKGROUND, this.leftPos + 95, this.topPos + 14, 178, 15, progress, 30);
    }

    @Override
    public void renderBurnIcon(GuiGraphics guiGraphics, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
            guiGraphics.blit(BACKGROUND, posX + 124, posY + 56, 176, 0, 17, 15);
        }
    }

    static {
        BACKGROUND = new Farm_And_CharmIdentifier("textures/gui/pot_gui.png");
    }
}
