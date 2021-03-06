package ch.epfl.swissteam.services.providers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import ch.epfl.swissteam.services.R;

/**
 * Broadcast receiver for network status.
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    private Activity activity_;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean connected = isConnected(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            Log.i("NetworkChange", "onReceive triggered");
            int[] attrs = new int[] {R.attr.noNetwork, R.attr.statusBarColor};
            TypedArray ta = activity_.obtainStyledAttributes(attrs);
            if (!connected) {
                Toast.makeText(context, "No Internet connection!", Toast.LENGTH_SHORT).show();
                setStatusBarColor(ta.getResourceId(0,0));
            } else {
                setStatusBarColor(ta.getResourceId(1,0));
            }
            ta.recycle();
        }
    }

    /**
     * Set the status bar's color given a color in parameter
     *
     * @param color color to use
     */
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity_.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity_.getResources().getColor(color));
        }
    }

    /**
     * Set the activity that is check in the NetworkStatusReceiver
     *
     * @param activity an activity
     */
    public void setActivity_(Activity activity) {
        this.activity_ = activity;
    }


    /**
     * Returns the current internet connectivity status.
     *
     * @param context calling context
     * @return current internet connectivity
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

}
