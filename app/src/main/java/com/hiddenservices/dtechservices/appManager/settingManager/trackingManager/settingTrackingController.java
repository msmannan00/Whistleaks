package com.hiddenservices.dtechservices.appManager.settingManager.trackingManager;

import static com.hiddenservices.dtechservices.pluginManager.pluginEnums.eMessageManager.M_SETTING_CHANGED_RESTART_REQUSTED;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hiddenservices.dtechservices.appManager.activityContextManager;
import com.hiddenservices.dtechservices.appManager.helpManager.helpController;
import com.hiddenservices.dtechservices.constants.constants;
import com.hiddenservices.dtechservices.constants.keys;
import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.dataManager.dataController;
import com.hiddenservices.dtechservices.dataManager.dataEnums;
import com.hiddenservices.dtechservices.eventObserver;
import com.hiddenservices.dtechservices.helperManager.helperMethod;
import com.hiddenservices.dtechservices.appManager.activityThemeManager;
import com.hiddenservices.dtechservices.pluginManager.pluginController;
import com.hiddenservices.dtechservices.pluginManager.pluginEnums;
import com.hiddenservices.dtechservices.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class settingTrackingController extends AppCompatActivity {

    /* PRIVATE VARIABLES */
    private settingTrackingModel mSettingPrivacyModel;
    private settingTrackingViewController mSettingPrivacyViewController;
    private ArrayList<RadioButton> mTrackers = new ArrayList<>();
    private boolean mSettingChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_ACTIVITY_CREATED);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_tracking_view);

        viewsInitializations();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_ACTIVITY_CREATED);
        super.onConfigurationChanged(newConfig);
        if (newConfig.uiMode != getResources().getConfiguration().uiMode) {
            activityContextManager.getInstance().onResetTheme();
            activityThemeManager.getInstance().onConfigurationChanged(this);
        }

    }

    private void viewsInitializations() {
        mTrackers.add(findViewById(R.id.pTrackingRadioOption1));
        mTrackers.add(findViewById(R.id.pTrackingRadioOption2));
        mTrackers.add(findViewById(R.id.pTrackingRadioOption3));

        activityContextManager.getInstance().onStack(this);
        mSettingPrivacyViewController = new settingTrackingViewController(this, new settingTrackingController.settingAccessibilityViewCallback(), mTrackers);
        mSettingPrivacyModel = new settingTrackingModel(new settingTrackingController.settingAccessibilityModelCallback());
    }

    /*View Callbacks*/

    private class settingAccessibilityViewCallback implements eventObserver.eventListener {

        @Override
        public Object invokeObserver(List<Object> data, Object e_type) {
            return null;
        }
    }

    /*Model Callbacks*/

    private class settingAccessibilityModelCallback implements eventObserver.eventListener {

        @Override
        public Object invokeObserver(List<Object> data, Object e_type) {
            return null;
        }
    }

    /* LOCAL OVERRIDES */

    @Override
    public void onResume() {
        activityContextManager.getInstance().onCheckPurgeStack();
        if (mSettingChanged) {
            activityContextManager.getInstance().setCurrentActivity(this);
        }
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_RESUME);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (mSettingChanged) {
            activityContextManager.getInstance().setCurrentActivity(this);
            activityContextManager.getInstance().getHomeController().initRuntimeSettings();
        }
        activityContextManager.getInstance().onRemoveStack(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        activityContextManager.getInstance().onRemoveStack(this);
        super.onDestroy();
    }

    /*UI Redirection*/

    public void onClose(View view) {
        onBackPressed();
    }

    public void onTracking(View view) {
        int mTrackingProtectionCurrentStatus = status.sSettingTrackingProtection;
        mSettingChanged = true;
        mSettingPrivacyViewController.onTrigger(settingTrackingEnums.eTrackingViewController.M_SET_TRACKING_STATUS, Collections.singletonList(view));
        mSettingPrivacyModel.onTrigger(settingTrackingEnums.eTrackingModel.M_SET_TRACKING_PROTECTION, Collections.singletonList(view));
        dataController.getInstance().invokePrefs(dataEnums.ePreferencesCommands.M_SET_INT, Arrays.asList(keys.SETTING_TRACKING_PROTECTION, status.sSettingTrackingProtection));
        if(mTrackingProtectionCurrentStatus != status.sSettingTrackingProtection){
            pluginController.getInstance().onMessageManagerInvoke(Collections.singletonList(this), M_SETTING_CHANGED_RESTART_REQUSTED);
        }
    }

    public void onOpenInfo(View view) {
        helperMethod.openActivity(helpController.class, constants.CONST_LIST_HISTORY, this, true);
    }

}
