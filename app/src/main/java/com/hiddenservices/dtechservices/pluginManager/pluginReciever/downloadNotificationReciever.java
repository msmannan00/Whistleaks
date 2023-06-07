package com.hiddenservices.dtechservices.pluginManager.pluginReciever;

import static com.hiddenservices.dtechservices.constants.constants.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hiddenservices.dtechservices.constants.enums;
import com.hiddenservices.dtechservices.pluginManager.pluginController;
import com.hiddenservices.dtechservices.pluginManager.pluginEnums;

import java.util.Collections;

public class downloadNotificationReciever extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        int mCommand = intent.getExtras().getInt(CONST_NOTIFICATION_COMMAND);
        if (mCommand == enums.DownloadNotificationReciever.DOWNLOAD_OPEN) {
            pluginController.getInstance().onDownloadInvoke(Collections.singletonList(intent.getExtras().getInt(CONST_NOTIFICATION_INTENT_KEY)), pluginEnums.eDownloadManager.M_URL_DOWNLOAD_REQUEST);
        } else if (mCommand == enums.DownloadNotificationReciever.DOWNLOAD_CANCEL) {
            pluginController.getInstance().onDownloadInvoke(Collections.singletonList(intent.getExtras().getInt(CONST_NOTIFICATION_INTENT_KEY)), pluginEnums.eDownloadManager.M_CANCEL_DOWNLOAD);
        }
    }
}
