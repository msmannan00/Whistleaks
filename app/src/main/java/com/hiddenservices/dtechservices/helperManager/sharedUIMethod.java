package com.hiddenservices.dtechservices.helperManager;


import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.hiddenservices.dtechservices.appManager.activityContextManager;
import com.hiddenservices.dtechservices.appManager.activityThemeManager;
import com.hiddenservices.dtechservices.pluginManager.pluginController;
import com.hiddenservices.dtechservices.pluginManager.pluginEnums;
import com.hiddenservices.dtechservices.R;

import java.util.Collections;

public class sharedUIMethod {
    /*Shared UI Helper Methods General*/

    public static void updateStatusBar(AppCompatActivity mContext) {
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

    public static void onSharedConfigurationChanged(Configuration newConfig, AppCompatActivity pContext) {
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(pContext), pluginEnums.eLangManager.M_ACTIVITY_CREATED);

        if (newConfig.uiMode != pContext.getResources().getConfiguration().uiMode) {
            activityContextManager.getInstance().onResetTheme();
            activityThemeManager.getInstance().onConfigurationChanged(pContext);
        }
    }

}
