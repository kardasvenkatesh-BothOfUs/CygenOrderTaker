package com.cygen.cygendineinpos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class CommonUtilities {

    static final String DISPLAY_MESSAGE_ACTION =
            "com.cygen.retailpos.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static boolean checkConn(Context ctx) {
        ConnectivityManager conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr != null) {
            NetworkInfo i = conMgr.getActiveNetworkInfo();
            if (i != null) {
                if (!i.isConnected())
                    return false;
                if (!i.isAvailable())
                    return false;
            }

            if (i == null)
                return false;

        } else
            return false;

        return true;
    }
}
