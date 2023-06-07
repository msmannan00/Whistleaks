package com.hiddenservices.dtechservices.pluginManager.orbotPluginManager;

import com.hiddenservices.dtechservices.constants.strings;
import java.util.List;
import static com.hiddenservices.dtechservices.pluginManager.orbotPluginManager.orbotPluginEnums.eLogManager.M_GET_CLEANED_LOGS;

public class orbotLogManager {

    private String onGetCleanedLogs(String pLogs) {
        String logs = pLogs;

        if (logs.equals("Starting Orion | Please Wait ...")) {
            return logs;
        }

        if (pLogs.equals("No internet connection")) {
            return "Warning | " + pLogs;
        } else if (pLogs.startsWith("Invalid Configuration")) {
            return pLogs;
        }

        if (!logs.equals(strings.GENERIC_EMPTY_STR)) {
            String Logs = logs;
            Logs = "Installing | " + Logs.replace("FAILED", "Securing");
            return Logs;
        }
        return "Loading Please Wait...";
    }

    /*External Triggers*/

    public Object onTrigger(List<Object> pData, orbotPluginEnums.eLogManager pEventType) {
        if (pEventType.equals(M_GET_CLEANED_LOGS)) {
            return onGetCleanedLogs((String) pData.get(0));
        }
        return null;
    }

}
