package com.hiddenservices.dtechservices.appManager.settingManager.searchEngineManager;

import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.eventObserver;

import java.util.List;


class settingSearchModel {
    /*Variable Declaration*/

    private eventObserver.eventListener mEvent;

    /*Initializations*/

    settingSearchModel(eventObserver.eventListener mEvent) {
        this.mEvent = mEvent;
    }


    /*Helper Methods*/

    private void onSetSearchEngine(String pUrl) {
        status.sSettingDefaultSearchEngine = pUrl;
    }

    private void setSearchHistory(boolean pStatus) {
        status.sSettingSearchHistory = pStatus;
    }

    private void setSearchSuggestionStatus(boolean pStatus) {
        status.sSearchSuggestionStatus = pStatus;
        if (!pStatus) {
            setSearchHistory(false);
        }
    }

    public Object onTrigger(settingSearchEnums.eSearchModel pCommands, List<Object> pData) {
        if (pCommands.equals(settingSearchEnums.eSearchModel.M_SET_SEARCH_ENGINE)) {
            onSetSearchEngine((String) pData.get(0));
        } else if (pCommands.equals(settingSearchEnums.eSearchModel.M_SET_SEARCH_HISTORY)) {

            setSearchHistory((boolean) pData.get(0));
        } else if (pCommands.equals(settingSearchEnums.eSearchModel.M_SET_SEARCH_SUGGESTION_STATUS)) {

            setSearchSuggestionStatus((boolean) pData.get(0));
        }
        return null;
    }

}
