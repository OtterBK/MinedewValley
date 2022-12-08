package ranirequest.ranirequest.itemmodule.customitems;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ranirequest.ranirequest.utils.LogUtility;

import java.util.HashMap;

public class CustomItem extends ItemStack {

    public static Plugin serverPlugin = null;
    public static HashMap<ItemStack, Listener> eventMap = new HashMap<>();

    public static void setServerPlugin(Plugin plugin){
        serverPlugin = plugin;
    }

    public CustomItem(Material mt, int amt, short type){
        super(mt, amt, type);
    }

    public CustomItem(Material mt, int amt){
        this(mt, 1, (short)0);
    }

    public CustomItem(Material mt){
        this(mt, 1);
    }

    public boolean registerEvent(Listener eventListener){
        if(serverPlugin == null){
            LogUtility.printLog(LogUtility.LogLevel.ERROR, "failed to register event for custom item: server plugin is null");
            return false;
        } else {

            if(eventMap.containsKey(this)){
                LogUtility.printLog(LogUtility.LogLevel.INFO, this.getClass().getName()+" was already registered");
                return false;
            }

            serverPlugin.getServer().getPluginManager().registerEvents(eventListener, serverPlugin);
            eventMap.put(this, eventListener);
            LogUtility.printLog(LogUtility.LogLevel.INFO, "custom item event has been registered");
            return true;
        }
    }
}
