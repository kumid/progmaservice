package com.ru.progmaservice.testutil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    @Override
    public void onNewToken(String s) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("My Token",token);
                    }
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("My Token","1");
        if(remoteMessage.getData().size() >0){
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("image"));
        }

        if(remoteMessage.getNotification()!=null){
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null);
        }
    }

    private RemoteViews getCustomDesign(String title,String message){
        Log.e("My Token","getCustomDesign start");
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), com.ru.progmaservice.testutil.R.layout.notification);
        remoteViews.setTextViewText(com.ru.progmaservice.testutil.R.id.title, title);
        remoteViews.setTextViewText(com.ru.progmaservice.testutil.R.id.message, message);
        //remoteViews.setImageViewResource(com.ru.progmaservice.testutil.R.id.icon, com.ru.progmaservice.testutil.R.drawable.ic_notifications_active_black_24dp);
        remoteViews.setImageViewBitmap(com.ru.progmaservice.testutil.R.id.icon, bitmap);

        Log.e("My Token","getCustomDesign end");
        return remoteViews;
    }

    Bitmap bitmap;
    public void showNotification(String title,String message, String image){
        Log.e("My Token","interpolator start");
        Intent intent= null;

        Log.e("My Token","interpolator image - " +image );

        if(image != null){
            bitmap = getBitmapfromUrl(image);
            if(bitmap != null)
                Log.e("My Token", String.valueOf(bitmap.getByteCount()));
            else
                Log.e("My Token", "Error Error Error Error Error Error Error Error Error");
        }

        try {
            String activityName = mysettings.GetActivities();
            Log.e("My Token","activityName = " + activityName);

            Class<?> cls = Class.forName(activityName);

            intent = new Intent(this, cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("My Token",e.getMessage());
        }

        String channel_id = "proc_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),channel_id)
                .setSmallIcon(com.ru.progmaservice.testutil.R.drawable.ic_notifications_active_black_24dp)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            builder=builder.setContent(getCustomDesign(title,message));
        }
        else{
            builder=builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(com.ru.progmaservice.testutil.R.drawable.ic_notifications_active_black_24dp);
        }

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel_id,"megapolis_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0,builder.build());
    }

    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}