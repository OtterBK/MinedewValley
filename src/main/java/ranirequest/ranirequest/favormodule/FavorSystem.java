package ranirequest.ranirequest.favormodule;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import ranirequest.ranirequest.utils.LogUtility;

import java.io.File;
import java.util.HashMap;

public class FavorSystem {

    public Plugin serverPlugin_;
    public static HashMap<String, FavorData> favorDataMap = new HashMap<>();

    public FavorSystem(Plugin plugin){

        this.serverPlugin_ = plugin;
        serverPlugin_.getServer().getPluginManager().registerEvents(new FavorSystemEvent(), serverPlugin_);

        FavorData.setDataFolderPath(serverPlugin_.getDataFolder().getAbsolutePath() + "/favorData/");

        startShowFavorScheduler();
        startAutoSave();
    }

    public static FavorData getFavorData(String uuid){
        FavorData favorData = favorDataMap.get(uuid);
        if(favorData == null){
            favorData = new FavorData(uuid);
            favorData.loadData();
            favorDataMap.put(uuid, favorData);
        }
        return favorData;
    }

    public void saveAllData(){
        for(FavorData favorData : favorDataMap.values()){
            favorData.saveData();
        }
    }

    public void startAutoSave(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(serverPlugin_, new Runnable(){
            public void run(){
                LogUtility.printLog(LogUtility.LogLevel.INFO, "호감도 데이터 자동 저장 중...");
                saveAllData();
            }
        }, 0, 1200l);
    }

    public void startShowFavorScheduler(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard favorBoard = manager.getNewScoreboard();
        final Objective favorObjective = favorBoard.registerNewObjective("favor", "dummy");
        favorObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        favorObjective.setDisplayName("§d♥호감도♥" );

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(serverPlugin_, new Runnable() {
            public void run() {

                for(Player player : Bukkit.getOnlinePlayers()){

                    String uuid = player.getUniqueId().toString();
                    FavorData favorData = getFavorData(uuid);
                    if(favorData == null) continue;;

                    player.setScoreboard(favorBoard);
                    Score score = favorObjective.getScore(player.getName());
                    score.setScore(favorData.favor_);

                }
            }
        },0, 20 * 1);
    }

    class FavorSystemEvent implements Listener {

        @EventHandler
        public void playerJoinEvent(PlayerJoinEvent evt){
            Player player = evt.getPlayer();
            String uuid = player.getUniqueId().toString();

            getFavorData(uuid); //favor 데이터 로드
        }

        @EventHandler
        public void playerInputCommand(PlayerCommandPreprocessEvent evt){
            Player player = evt.getPlayer();

            String commandLine = evt.getMessage();
            String commandArgs[] = commandLine.split(" ");
            String command = commandArgs[0];

            if(command.equalsIgnoreCase("/호감도")){
                evt.setCancelled(true);

                if(commandArgs.length > 1){
                    //호감도 증감 명령어
                    if(commandArgs[1].equalsIgnoreCase("증감")){
                        if(commandArgs.length > 3){
                            String targetName = commandArgs[2];
                            String additionValueString = commandArgs[3];
                            int additionValue = 0;

                            try{
                                additionValue = Integer.parseInt(additionValueString);
                            }catch (Exception exc){
                                player.sendMessage("§a" + additionValueString + "은 정수가 아닙니다.");
                            }

                            //요청 들어와서 뻄
//                            if(targetName.equalsIgnoreCase(player.getName())){
//                                player.sendMessage("§a자기 자신의 호감도를 바꿀 순 없습니다.");
//                                return;
//                            }

                            OfflinePlayer targetOfflinePlayer = Bukkit.getOfflinePlayer(targetName);
                            if(targetOfflinePlayer == null){
                                player.sendMessage("§a" + targetName + "에 대한 플레이어 데이터가 존재하지 않습니다.");
                                return;
                            }
                            String targetUUID = targetOfflinePlayer.getUniqueId().toString();
                            FavorData favorData = getFavorData(targetUUID);
                            if(favorData == null){
                                player.sendMessage("§a" + targetName + "에 대한 호감도 데이터가 존재하지 않습니다.");
                                return;
                            }

                            favorData.addFavor(additionValue);
                            player.sendMessage("§b" + targetName + "님의 호감도에 " + additionValue + "♥!");

                            return;
                        } else {
                            player.sendMessage("§a/호감도 증감 닉네임 수치");
                        }

                        return;
                    }

                    if(commandArgs[1].equalsIgnoreCase("확인")){
                        FavorData favorData = getFavorData(player.getUniqueId().toString());
                        player.sendMessage("§b현재 호감도: "+ favorData.favor_ + "♥");

                        return;
                    }

                }

                player.sendMessage("§a/호감도 증감 닉네임 수치");
                player.sendMessage("§a/호감도 확인");
                return;
            }
        }

    }

}
