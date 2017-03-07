package com.herokuapp.soliduxample.solidus.helper;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Roberto Morelos on 3/6/17.
 * This class contains useful methods which will be used along the app.
 */
public class Utility {
    /**
     * Check the connection state (WI-FI or data)
     * @param context Needed for the ConectivityManager
     * @return boolean If enabled, return true. If not, return false.
     */
    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
