package com.hiddenservices.dtechservices.appManager.homeManager.profiler;

public class profilerModel {
    /*Private Variables*/

    private String mURL;
    private String mLogin;

    public profilerModel(String pURL, String pLogin) {
        mURL = pURL;
        mLogin = pLogin;
    }

    public String getURL() {
        return mURL;
    }

    public String getLogin() {
        return mLogin;
    }

}