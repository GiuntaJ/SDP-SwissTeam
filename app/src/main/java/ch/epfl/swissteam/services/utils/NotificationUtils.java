package ch.epfl.swissteam.services.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import ch.epfl.swissteam.services.models.ChatRelation;
import ch.epfl.swissteam.services.view.activities.ChatRoomActivity;
import ch.epfl.swissteam.services.R;

/**
 * Utils class for methods related to notifications.
 *
 * @author Adrian Baudat
 */
public class NotificationUtils {

    public static String CUSTOM_NOTIFICATION_CHANNEL_ID = "CUSTOM_NOTIFICATION";

    /**
     * Sends a chat notification to the user
     *
     * @param activity calling activity
     * @param textTitle title of notification
     * @param textContent body of notification
     * @param relationId identifier of {@link ChatRelation}
     */
    public static void sendChatNotification(Activity activity, String textTitle, String textContent, String relationId){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(activity, ChatRoomActivity.class);
        intent.putExtra(ChatRelation.RELATION_ID_TEXT, relationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity, CUSTOM_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat.from(activity).notify(1, mBuilder.build());
    }
}
