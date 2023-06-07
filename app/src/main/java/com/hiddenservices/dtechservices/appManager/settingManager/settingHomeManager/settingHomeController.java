package com.hiddenservices.dtechservices.appManager.settingManager.settingHomeManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.hiddenservices.dtechservices.appManager.activityContextManager;
import com.hiddenservices.dtechservices.appManager.helpManager.helpController;
import com.hiddenservices.dtechservices.appManager.settingManager.proxyStatusManager.proxyStatusController;
import com.hiddenservices.dtechservices.appManager.settingManager.accessibilityManager.settingAccessibilityController;
import com.hiddenservices.dtechservices.appManager.settingManager.advanceManager.settingAdvanceController;
import com.hiddenservices.dtechservices.appManager.settingManager.clearManager.settingClearController;
import com.hiddenservices.dtechservices.appManager.settingManager.generalManager.settingGeneralController;
import com.hiddenservices.dtechservices.appManager.settingManager.notificationManager.settingNotificationController;
import com.hiddenservices.dtechservices.appManager.settingManager.privacyManager.settingPrivacyController;
import com.hiddenservices.dtechservices.appManager.settingManager.searchEngineManager.settingSearchController;
import com.hiddenservices.dtechservices.appManager.settingManager.trackingManager.settingTrackingController;
import com.hiddenservices.dtechservices.constants.constants;
import com.hiddenservices.dtechservices.constants.enums;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;
import static com.hiddenservices.dtechservices.pluginManager.pluginEnums.eMessageManager.*;

public class settingHomeController extends AppCompatActivity {
    /*Private Observer Classes*/

    private settingHomeViewController mSettingViewController;
    private settingHomeModel mSettingModel;
    private TextView mModeHeader;
    private TextView mModeDescription;



    /*Initializations*/

    public settingHomeController() {
        mSettingModel = new settingHomeModel(new settingModelCallback());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_ACTIVITY_CREATED);
        super.onCreate(savedInstanceState);
        if (!status.mThemeApplying) {
            activityContextManager.getInstance().onStack(this);
        }

        setContentView(R.layout.setting);
        viewsInitializations();
        listenersInitializations();
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        try {
            pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_ACTIVITY_CREATED);
            if (activityThemeManager.getInstance().onInitTheme(this) && !status.mThemeApplying) {
                activityContextManager.getInstance().onResetTheme();
            }

            helperMethod.updateResources(this, status.mSystemLocale.getDisplayName());
            activityContextManager.getInstance().getHomeController().updateResources(this, status.mSystemLocale.getDisplayName());
            pluginController.getInstance().onMessageManagerInvoke(null, M_RESET);
        }catch (Exception ex){}
        super.onConfigurationChanged(newConfig);
    }

    public void onInitTheme() {

        if (status.mThemeApplying) {
            if (status.sTheme == enums.Theme.THEME_DARK) {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (status.sTheme == enums.Theme.THEME_LIGHT) {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                if (!status.sDefaultNightMode) {
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        }
        recreate();
    }

    private void viewsInitializations() {
        activityContextManager.getInstance().setSettingController(this);

        mModeHeader = findViewById(R.id.pModeHeader);
        mModeDescription = findViewById(R.id.pModeDescription);
        mSettingViewController = new settingHomeViewController(this, new settingViewCallback(), mModeHeader, mModeDescription);
    }

    private void listenersInitializations() {
    }

    /*View Callbacks*/

    private class settingViewCallback implements eventObserver.eventListener {

        @Override
        public Object invokeObserver(List<Object> data, Object e_type) {
            return null;
        }
    }

    /*Model Callbacks*/

    private class settingModelCallback implements eventObserver.eventListener {

        @Override
        public Object invokeObserver(List<Object> data, Object e_type) {
            return null;
        }
    }

    /*Local Overrides*/

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (status.sSettingIsAppPaused && (level == 80 || level == 15)) {
            dataController.getInstance().invokePrefs(dataEnums.ePreferencesCommands.M_SET_BOOL, Arrays.asList(keys.HOME_LOW_MEMORY, true));
            finish();
        }
    }

    @Override
    public void onResume() {
        activityContextManager.getInstance().onCheckPurgeStack();
        if (status.mThemeApplying) {
            // activityContextManager.getInstance().onStack(this);
        }
        //onInitTheme();
        pluginController.getInstance().onLanguageInvoke(Collections.singletonList(this), pluginEnums.eLangManager.M_RESUME);
        activityContextManager.getInstance().setCurrentActivity(this);
        status.sSettingIsAppPaused = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (!status.mThemeApplying) {
            activityContextManager.getInstance().onRemoveStack(this);
        }
        activityContextManager.getInstance().setSettingController(null);
        super.onDestroy();
    }

    /*External Redirection*/

    public void onRedrawXML() {
        setContentView(R.layout.setting);
    }

    public void onReInitTheme() {
        recreate();
    }

    /*UI Redirection*/

    public void onNavigationBackPressed(View view) {
        finish();
    }

    public void onManageNotification(View view) {
        helperMethod.openActivity(settingNotificationController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageSearchEngine(View view) {
        helperMethod.openActivity(settingSearchController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageSearchAccessibility(View view) {
        helperMethod.openActivity(settingAccessibilityController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageGeneral(View view) {
        helperMethod.openActivity(settingGeneralController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageBrowserModeGeneral(View view) {
        finish();
        activityContextManager.getInstance().getHomeController().toggleBrowserMode();
    }
    public void onManageSearchClearData(View view) {
        helperMethod.openActivity(settingClearController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageSearchPrivacy(View view) {
        helperMethod.openActivity(settingPrivacyController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageSearchAdvanced(View view) {
        helperMethod.openActivity(settingAdvanceController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onManageTracking(View view) {
        helperMethod.openActivity(settingTrackingController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onOpenInfo(View view) {
        helperMethod.openActivity(helpController.class, constants.CONST_LIST_HISTORY, this, true);
    }

    public void onReset(View view) {
        activityContextManager.getInstance().getHomeController().onResetData();
        pluginController.getInstance().onMessageManagerInvoke(null, M_RESET);
        pluginController.getInstance().onMessageManagerInvoke(Collections.singletonList(this), M_PANIC_RESET);
    }

    public void onPrivacyPolicy(View view) {
        finish();
        if (!status.sTorBrowsing) {
            activityContextManager.getInstance().getHomeController().onLoadURL(constants.CONST_PRIVACY_POLICY_URL_NON_TOR);
        } else {
            activityContextManager.getInstance().getHomeController().onLoadURL(helperMethod.setGenesisVerificationToken(constants.CONST_PRIVACY_POLICY_URL));
        }
    }

    public void onOpenProxyStatus(View view) {
        helperMethod.openActivity(proxyStatusController.class, constants.CONST_LIST_HISTORY, this, true);
    }

}