package com.hiddenservices.dtechservices.appManager.settingManager.notificationManager;

import com.hiddenservices.dtechservices.appManager.activityContextManager;
import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.eventObserver;
import com.hiddenservices.dtechservices.pluginManager.pluginController;
import com.hiddenservices.dtechservices.pluginManager.pluginEnums;

import java.util.List;

class settingNotificationModel {
    /*Variable Declaration*/

    private eventObserver.eventListener mEvent;

    /*Initializations*/

    settingNotificationModel(eventObserver.eventListener mEvent) {
        this.mEvent = mEvent;
    }


    /*Helper Methods*/

    private void updateLocalNotification(boolean pStatus) {

        int mStatus = pStatus ? 1 : 0;
        status.sNotificaionStatus = mStatus;
        if (!pStatus) {
            activityContextManager.getInstance().getHomeController().onHideDefaultNotification();
            pluginController.getInstance().onOrbotInvoke(null, pluginEnums.eOrbotManager.M_DISABLE_NOTIFICATION);
        } else {
            if(status.sTorBrowsing){
                pluginController.getInstance().onOrbotInvoke(null, pluginEnums.eOrbotManager.M_ENABLE_NOTIFICATION);
            }else {
                activityContextManager.getInstance().getHomeController().onShowDefaultNotification(true);
            }
        }
        activityContextManager.getInstance().getHomeController().onReloadProxy();
    }

    public Object onTrigger(settingNotificationEnums.eNotificationModel pCommands, List<Object> pData) {
        if (pCommands.equals(settingNotificationEnums.eNotificationModel.M_UPDATE_LOCAL_NOTIFICATION)) {
            updateLocalNotification((boolean) pData.get(0));
        }
        return null;
    }

}
