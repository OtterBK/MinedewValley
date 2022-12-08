package ranirequest.ranirequest.itemmodule.customitems;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ranirequest.ranirequest.utils.MyUtility;

public class HardWood extends CustomItem implements Listener{

    final private String itemName = "§c단단한 나무";

    public HardWood(){
        super(Material.ACACIA_LOG, 1);

        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(itemName);

        this.setItemMeta(itemMeta);

        registerEvent(this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent evt){
        Block brokenBlock = evt.getBlock();
        Material blockType = brokenBlock.getType();
        Player player = evt.getPlayer();

        if(blockType == Material.ACACIA_LOG
                || blockType == Material.BIRCH_LOG
                || blockType == Material.DARK_OAK_LOG
                || blockType == Material.JUNGLE_LOG
                || blockType == Material.OAK_LOG
                || blockType == Material.SPRUCE_LOG
                || blockType == Material.STRIPPED_ACACIA_LOG
                || blockType == Material.STRIPPED_BIRCH_LOG
                || blockType == Material.STRIPPED_JUNGLE_LOG
                || blockType == Material.STRIPPED_OAK_LOG
                || blockType == Material.STRIPPED_SPRUCE_LOG
                || blockType == Material.STRIPPED_DARK_OAK_LOG
        ){

            if(MyUtility.getRandom(0, 4) != 0){ //20퍼 확률
                return;
            }

            ItemStack hardWood = this.clone(); //단단한 나무 드랍
            Location blockLocation = brokenBlock.getLocation();
            brokenBlock.getWorld().dropItem(blockLocation, hardWood);
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent evt){

        Player player = evt.getPlayer();
        ItemStack blockItemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = blockItemStack.getItemMeta();
        if(itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(itemName)){
            evt.setCancelled(true);
        }


    }

}
