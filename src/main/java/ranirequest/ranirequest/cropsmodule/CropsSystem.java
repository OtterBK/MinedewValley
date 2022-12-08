package ranirequest.ranirequest.cropsmodule;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import ranirequest.ranirequest.utils.LogUtility;

import java.util.ArrayList;
import java.util.List;

public class CropsSystem {

    public Plugin serverPlugin;

    public List<Material> cropsList = new ArrayList<Material>();

    public CropsSystem(Plugin plugin){
        setCropList();

        this.serverPlugin = plugin;

        serverPlugin.getServer().getPluginManager().registerEvents(new CropsEvent(), serverPlugin);
    }

    public void setCropList(){
        cropsList.add(Material.WHEAT);
        cropsList.add(Material.POTATO);
        cropsList.add(Material.CARROT);
    }

    //이벤트
    class CropsEvent implements Listener{

        // 작물 성장속도 2배
        @EventHandler
        public void onCropsGrow(BlockGrowEvent evt){
            Block grewBlock = evt.getBlock();
            if(cropsList.contains(grewBlock.getType())){
                BlockData blockData = grewBlock.getBlockData();

                Ageable ageableData = (Ageable)blockData;
                int nextAge = ageableData.getAge() + 1;
                if(nextAge > ageableData.getMaximumAge()){
                    nextAge = ageableData.getMaximumAge();
                }

//                LogUtility.printLog(LogUtility.LogLevel.INFO, ageableData.getAge() + " -> " + nextAge);
                ageableData.setAge(nextAge);

                grewBlock.setBlockData(ageableData);
            }
        }

    }

}
