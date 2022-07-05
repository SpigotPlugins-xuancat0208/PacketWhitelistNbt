package xuan.cat.packetwhitelistnbt.code.data;

import org.bukkit.entity.Player;

public final class PlayerPermissions {
    private final ConfigData configData;
    private final Player player;
    /** 檢查權限 */
    private Long permissionsCheck = null;
    /** 權限需要檢查 */
    public boolean permissionsNeed = true;
    /** 忽略標籤限制 */
    public boolean ignoreItemTagLimit;

    public PlayerPermissions(ConfigData configData, Player player) {
        this.configData = configData;
        this.player = player;
        check();
    }

    public void check() {
        if (permissionsNeed || (configData.permissionsPeriodicMillisecondCheck != -1 && (permissionsCheck == null || permissionsCheck <= System.currentTimeMillis() - configData.permissionsPeriodicMillisecondCheck))) {
            permissionsNeed = false;
            permissionsCheck = System.currentTimeMillis();
            // 檢查權限節點
            ignoreItemTagLimit = player.hasPermission(configData.permissionsNode_ignoreItemTagLimit);
        }
    }
}
