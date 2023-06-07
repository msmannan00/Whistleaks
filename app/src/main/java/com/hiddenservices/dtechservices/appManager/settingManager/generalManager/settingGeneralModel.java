package com.hiddenservices.dtechservices.appManager.settingManager.generalManager;

import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.eventObserver;

import java.util.List;

class settingGeneralModel {
    /*Variable Declaration*/

    private eventObserver.eventListener mEvent;

    /*Initializations*/

    settingGeneralModel(eventObserver.eventListener mEvent) {
        this.mEvent = mEvent;
    }

    /*Helper Methods*/
    private void onFullScreenBrowsing(boolean pStatus) {
        status.sFullScreenBrowsing = pStatus;
    }

    private void onSelectThemeLight(int pStatus) {
        status.sTheme = pStatus;
    }

    private void onURLInNewTab(boolean pStatus) {
        status.sOpenURLInNewTab = pStatus;
    }

    public Object onTrigger(settingGeneralEnums.eGeneralModel pCommands, List<Object> pData) {
        if (pCommands.equals(settingGeneralEnums.eGeneralModel.M_FULL_SCREEN_BROWSING)) {
            onFullScreenBrowsing((boolean) pData.get(0));
        } else if (pCommands.equals(settingGeneralEnums.eGeneralModel.M_SELECT_THEME)) {
            onSelectThemeLight((int) pData.get(0));
        } else if (pCommands.equals(settingGeneralEnums.eGeneralModel.M_URL_NEW_TAB)) {
            onURLInNewTab((boolean) pData.get(0));
        }
        return null;
    }

}
