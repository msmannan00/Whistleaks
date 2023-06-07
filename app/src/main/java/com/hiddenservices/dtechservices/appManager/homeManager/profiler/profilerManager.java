package com.hiddenservices.dtechservices.appManager.homeManager.profiler;

import android.webkit.URLUtil;

import androidx.appcompat.app.AppCompatActivity;

import com.hiddenservices.dtechservices.constants.status;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class profilerManager {

    profilerModel mProfilerModel;
    public profilerManager(AppCompatActivity pContext){
    }

    public boolean initJSON(AppCompatActivity pContext, String mCode){
        String json ;
        try {
            if(mCode.contains(".") && URLUtil.isValidUrl(mCode)){
                mProfilerModel = new profilerModel(mCode, null);
                status.sBusinessCode = mCode;
                status.sBusinessCodeURL = status.sBusinessCode;
                return true;
            }else {
                InputStream is = pContext.getAssets().open("profiles.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
                JSONObject mProfiles = new JSONObject(json);
                JSONObject mBusinessProfile = new JSONObject(mProfiles.get(mCode).toString());

                mProfilerModel = new profilerModel(mBusinessProfile.getString("URL"), mBusinessProfile.getString("Login"));
                status.sBusinessCode = mCode;
                status.sBusinessCodeURL = mBusinessProfile.getString("URL");
                status.sBusinessCodeLogin = mBusinessProfile.getString("Login");
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getHomeURL(){

        return mProfilerModel.getURL();
    }

    public String getLoginURL(){

        return mProfilerModel.getLogin();
    }

}
