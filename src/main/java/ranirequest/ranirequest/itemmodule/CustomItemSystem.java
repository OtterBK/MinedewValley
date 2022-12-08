package ranirequest.ranirequest.itemmodule;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ranirequest.ranirequest.itemmodule.customitems.CustomItem;
import ranirequest.ranirequest.itemmodule.customitems.HardWood;

public class CustomItemSystem  {

    private Plugin serverPlugin;

    public CustomItemSystem(Plugin serverPlugin){

        this.serverPlugin = serverPlugin;
        CustomItem.setServerPlugin(serverPlugin);

        createItems();

    }

    public void createItems(){
        new HardWood();
    }
}
