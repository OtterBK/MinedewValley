package ranirequest.ranirequest.timemodule;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CustomTimeSystem {

    private Plugin serverPlugin_;
    private int timeMultiple = 5; //몇배 더 느리게할 지
    private int waitCount = 0;
    private String worldName = "world"; //영향을 미칠 world

    public CustomTimeSystem(Plugin plugin){
        this.serverPlugin_ = plugin;

        startCustomTimeScheduler();
    }

    public void startCustomTimeScheduler(){
        Bukkit.getLogger().info("Start Custom Timer");
        long period = 20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.serverPlugin_, new Runnable(){
            public void run(){
                World world = Bukkit.getWorld("world");
                if(world == null) return;
                if(++waitCount < timeMultiple){ //아직 기다려야하면

                    world.setTime(world.getTime() - period); //-20틱 전으로
                    Bukkit.getLogger().info("Hold time: " + world.getTime() % 24000);

                }else{ //waitCount 다 기다렸으면
                    waitCount = 0; //카운터 초기화

                    Bukkit.getLogger().info("Continue time: " + world.getTime() % 24000);

                    long timeTick = (world.getTime() + 6000) % 24000; //+6000하는 이유는 마크의 0틱은 06시임

                    //+6000 하는 이유는 마크의 시작은 06:00시이기 때문 0틱은 6시임, realtimesecond 로 환산하면 21600초
                    long asRealTimeOfSecond = (long)((timeTick) * 3.6); //마크의 1틱은 현실의 3.6초
                    long asRealTimeOfMinute = (long)((asRealTimeOfSecond / 60));
                    long asRealTimeOfHour = (long)(asRealTimeOfMinute / 60);

                    String displayMinute =  (asRealTimeOfMinute % 60 < 10 ? "0" : "") + asRealTimeOfMinute % 60;
                    String displayHour = (asRealTimeOfHour < 10 ? "0" : "") + asRealTimeOfHour;

                    String displayTime = "\n§b시계\n"+ displayHour + ":" + displayMinute + "\n";

                    //시간 표시 설정
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.setPlayerListHeader(displayTime);
                    }
                }
            }
        }, 0, period);
    }

}
