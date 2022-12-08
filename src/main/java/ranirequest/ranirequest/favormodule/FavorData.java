package ranirequest.ranirequest.favormodule;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ranirequest.ranirequest.utils.LogUtility;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FavorData {

    public static String dataFolderPath = "";

    public String uuid_;
    public String nickname_;
    public int favor_ = 0;

    public FavorData(String uuid){
        this.uuid_ = uuid;
        this.nickname_ = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
    }

    public static void setDataFolderPath(String path){
        FavorData.dataFolderPath = path;
        File dataFolder = new File(FavorData.dataFolderPath);
        if(!dataFolder.exists()) dataFolder.mkdirs();
    }

    public int addFavor(int favor){
        this.favor_ += favor;
        saveData();
        OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(UUID.fromString(this.uuid_));
        if(offPlayer.isOnline()){
            Player onPlayer = (Player)offPlayer;
            onPlayer.sendMessage("§b당신의 호감도가 " + this.favor_ + "♥로 바뀌었습니다.");
            onPlayer.getWorld().playSound(onPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
        }
        return this.favor_;
    }

    public boolean loadData(){
        if(!dataFolderPath.endsWith("/")) dataFolderPath += "/";
        String filePath = dataFolderPath + uuid_ + ".yml";
        File file = new File(filePath);

        if(!file.exists()){
            LogUtility.printLog(LogUtility.LogLevel.INFO, filePath + " is not exist");
            return false;
        }

        FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
        String nickname = fileConf.getString("nickname");
        int favor = fileConf.getInt("favor");

        this.nickname_ = nickname;
        this.favor_ = favor;

        LogUtility.printLog(LogUtility.LogLevel.INFO, filePath + " has been load");

        return true;
    }

    public void saveData(){
        if(!dataFolderPath.endsWith("/")) dataFolderPath += "/";
        String filePath = dataFolderPath + uuid_ + ".yml";;
        File file = new File(filePath);

        if(file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
        fileConf.set("nickname", this.nickname_);
        fileConf.set("favor", this.favor_);
        try {
            fileConf.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogUtility.printLog(LogUtility.LogLevel.INFO, filePath + " has been saved");
    }

}
