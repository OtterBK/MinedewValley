package ranirequest.ranirequest;

import org.bukkit.plugin.java.JavaPlugin;
import ranirequest.ranirequest.cropsmodule.CropsSystem;
import ranirequest.ranirequest.favormodule.FavorSystem;
import ranirequest.ranirequest.itemmodule.CustomItemSystem;
import ranirequest.ranirequest.itemmodule.customitems.CustomItem;
import ranirequest.ranirequest.timemodule.CustomTimeSystem;
import ranirequest.ranirequest.utils.LogUtility;

public final class RaniRequest extends JavaPlugin {

    public CropsSystem cropsSystem;
    public FavorSystem favorSystem;
    public CustomItemSystem customItemSystem;
    public CustomTimeSystem customTimeSystem;

    @Override
    public void onEnable() {

        cropsSystem = new CropsSystem(this);
        LogUtility.printLog(LogUtility.LogLevel.INFO, "작물 모듈 로드완료");
        favorSystem = new FavorSystem(this);
        LogUtility.printLog(LogUtility.LogLevel.INFO, "호감도 모듈 로드완료");
        customItemSystem = new CustomItemSystem(this);
        LogUtility.printLog(LogUtility.LogLevel.INFO, "아이템 모듈 로드완료");
        customTimeSystem = new CustomTimeSystem(this);
        LogUtility.printLog(LogUtility.LogLevel.INFO, "시간 모듈 로드완료");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
