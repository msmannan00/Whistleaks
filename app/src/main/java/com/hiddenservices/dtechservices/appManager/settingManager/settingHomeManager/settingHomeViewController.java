package com.hiddenservices.dtechservices.appManager.settingManager.settingHomeManager;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.eventObserver;
import com.hiddenservices.dtechservices.R;
import java.util.List;

class settingHomeViewController {
    /*Private Variables*/

    private eventObserver.eventListener mEvent;
    private AppCompatActivity mContext;
    private TextView mModeHeader;
    private TextView mModeDescription;

    /*Initializations*/

    settingHomeViewController(settingHomeController mContext, eventObserver.eventListener mEvent, TextView pModeHeader, TextView pModeDescription) {
        this.mEvent = mEvent;
        this.mContext = mContext;
        this.mModeHeader = pModeHeader;
        this.mModeDescription = pModeDescription;

        initPostUI();
    }

    private void initPostUI() {
        if(status.mWhistleMode){
            mModeHeader.setText(R.string.SETTING_GENERAL_BROWSER_MODE);
            mModeDescription.setText(R.string.SETTING_GENERAL_BROWSER_MODE_DESCRIPTION);
        }else {
            mModeHeader.setText(R.string.HOME_MENU__WHISTLE_MODE);
            mModeDescription.setText(R.string.SETTING_GENERAL_WHISTLE_MODE_DESCRIPTION);
        }

        Window window = mContext.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            window.setStatusBarColor(mContext.getResources().getColor(R.color.blue_dark));
            mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.landing_ease_blue));
        } else {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.c_background));
        }
    }

    public Object onTrigger(settingHomeEnums.eHomeViewController pCommands, List<Object> pData) {
        if (pCommands.equals(settingHomeEnums.eHomeViewController.M_INIT)) {
            initPostUI();
        }
        return null;
    }

}
