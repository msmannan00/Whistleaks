package com.hiddenservices.dtechservices.dataManager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hiddenservices.dtechservices.constants.keys;
import com.hiddenservices.dtechservices.constants.status;
import com.hiddenservices.dtechservices.constants.strings;

import java.util.Arrays;
import java.util.List;

import static com.hiddenservices.dtechservices.constants.constants.*;

public class bridgesDataModel {

    /* Local Variables */

    private String mBridges;
    private boolean mLoading = false;

    /* Initializations */

    public bridgesDataModel() {
    }

    /* Helper Methods */

    private void onLoad(Context pContext) {
        if (!mLoading) {
            mLoading = true;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, CONST_GENESIS_BRIDGE_WEBSITES,
                    response -> {
                        if (response.length() > 10) {
                            mBridges = response;
                            status.sBridgesDefault = response;
                            dataController.getInstance().invokePrefs(dataEnums.ePreferencesCommands.M_SET_STRING, Arrays.asList(keys.BRIDGE_DEFAULT, mBridges));
                            status.sBridgesDefault = (String) dataController.getInstance().invokePrefs(dataEnums.ePreferencesCommands.M_GET_STRING, Arrays.asList(keys.BRIDGE_DEFAULT, strings.BRIDGES_DEFAULT));
                            mLoading = false;
                        } else {
                            mBridges = status.sReferenceWebsites;
                        }
                    },
                    error -> {
                        mBridges = status.sReferenceWebsites;
                        mLoading = false;
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(pContext);
            requestQueue.add(stringRequest);
        }

    }

    private String onFetch() {
        try {
            return status.sBridgesDefault;
        } catch (Exception ignored) {
        }
        return strings.GENERIC_EMPTY_SPACE;
    }

    /* External Triggers */

    public Object onTrigger(dataEnums.eBridgeWebsiteCommands p_commands, List<Object> pData) {
        if (p_commands == dataEnums.eBridgeWebsiteCommands.M_LOAD) {
            //onLoad((Context) pData.get(0));
        }
        if (p_commands == dataEnums.eBridgeWebsiteCommands.M_FETCH) {
            return onFetch();
        }

        return null;
    }

}
