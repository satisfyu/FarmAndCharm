package net.satisfy.farm_and_charm.registry;

import de.cristelknight.doapi.common.util.datafixer.DataFixers;
import de.cristelknight.doapi.common.util.datafixer.StringPairs;
import net.satisfy.farm_and_charm.FarmAndCharm;

public class DataFixerRegistry {

    public static void init(){
        StringPairs p = DataFixers.getOrCreate(FarmAndCharm.MOD_ID);
        p.add("candlelight:pasta", "candlelight:pasta_with_mozzarella");
        p.add("candlelight:lettuce_salad", "candlelight:salad");
        p.add("candlelight:cooked_beef", "candlelight:fillet_steak");
        p.add("candlelight:pasta_bolognese", "candlelight:pasta_with_bolognese");
        p.add("candlelight:salmon_wine", "candlelight:salmon_on_white_wine");
        p.add("candlelight:pancake", "candlelight:omelet");
        p.add("candlelight:ettuce_beef", "candlelight:roasted_lamb_with_lettuce");
        p.add("candlelight:lettuce_tomato_block", "candlelight:fresh_garden_salad_block");
        p.add("candlelight:lettuce_tomato", "candlelight:fresh_garden_salad");
        p.add("candlelight:veggie_plate", "candlelight:harvest_plate");
        p.add("candlelight:fricasse", "candlelight:beef_with_mushroom_in_wine_and_potatoes");
        p.add("candlelight:chocolate", "candlelight:chocolate_mousse");
        p.add("candlelight:roastbeef_carrots", "candlelight:roastbeef_with_glazed_carrots");
        p.add("candlelight:lasagna_block", "candlelight:lasagne_block");
        p.add("candlelight:lasagna", "candlelight:lasagne");
        p.add("candlelight:chicken", "candlelight:chicken_with_vegetables");
        p.add("candlelight:glass_block", "candlelight:wine_glass");
        p.add("candlelight:tomato_crop", "farm_and_charm:tomato_crop");
        p.add("candlelight:tomato_seeds", "farm_and_charm:tomato_seeds");
        p.add("candlelight:lettuce_crop", "farm_and_charm:lettuce_crop");
        p.add("candlelight:lettuce_seeds", "farm_and_charm:lettuce_seeds");
        p.add("candlelight:wild_lettuce", "farm_and_charm:wild_lettuce");
        p.add("candlelight:wild_tomatoes", "farm_and_charm:wild_tomatoes");
        p.add("candlelight:tool_rack", "farm_and_charm:tool_rack");
        p.add("candlelight:tomato_bag", "farm_and_charm:tomato_bag");
        p.add("candlelight:lettuce_bag", "farm_and_charm:lettuce_bag");
        p.add("candlelight:carrot_bag", "farm_and_charm:carrot_bag");
        p.add("candlelight:potato_bag", "farm_and_charm:potato_bag");
        p.add("candlelight:beetroot_bag", "farm_and_charm:beetroot_bag");
        p.add("candlelight:dough", "farm_and_charm:dough");
        p.add("candlelight:pasta_raw", "farm_and_charm:raw_pasta");
        p.add("candlelight:rotten_tomato", "farm_and_charm:rotten_tomato");
        p.add("brewery:barley_crop", "farm_and_charm:barley_crop");
        p.add("brewery:barley_seeds", "farm_and_charm:barley_seeds");
        p.add("brewery:barley", "farm_and_charm:barley");
        p.add("brewery:corn_crop", "farm_and_charm:corn_crop");
        p.add("brewery:corn_seeds", "farm_and_charm:kernels");
        p.add("brewery:corn", "farm_and_charm:corn");
        p.add("brewery:silo_wood", "farm_and_charm:silo_wood");
        p.add("brewery:silo_copper", "farm_and_charm:silo_copper");
        p.add("bakery:wild_strawberries", "farm_and_charm:wild_strawberries");
        p.add("bakery:strawberry_crop", "farm_and_charm:strawberry_crop");
        p.add("bakery:strawberry_seeds", "farm_and_charm:strawberry_seeds");
        p.add("bakery:strawberry", "farm_and_charm:strawberry");
        p.add("bakery:oat_crop", "farm_and_charm:oat_crop");
        p.add("bakery:oat_seeds", "farm_and_charm:oat_seeds");
        p.add("bakery:oat", "farm_and_charm:oat");
        p.add("bakery:crafting_bowl", "farm_and_charm:crafting_bowl");
        p.add("bakery:small_cooking_pot", "farm_and_charm:small_cooking_pot");
        p.add("bakery:strawberry_crate", "farm_and_charm:strawberry_bag");
        p.add("bakery:oat_block", "farm_and_charm:oat_ball");
        p.add("bakery:oat_crate", "brewery:oat_ball");
        p.add("bakery:brick_stove", "farm_and_charm:stove");
        p.add("bakery:mud_stove", "farm_and_charm:stove");
        p.add("bakery:end_stove", "farm_and_charm:stove");
        p.add("bakery:deepslate_stove", "farm_and_charm:stove");
        p.add("bakery:cobblestone_stove", "farm_and_charm:stove");
        p.add("bakery:stone_bricks_stove", "farm_and_charm:stove");
        p.add("bakery:sandstone_stove", "farm_and_charm:stove");
        p.add("bakery:granite_stove", "farm_and_charm:stove");
        p.add("bakery:red_nether_bricks_stove", "farm_and_charm:stove");
        p.add("bakery:bamboo_stove", "farm_and_charm:stove");
    }
}
