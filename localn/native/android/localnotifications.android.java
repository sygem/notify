import android.support.v4.app.NotificationCompat;
import com.sygem.monkey.notify.AlarmNotification;

class NativeLocalNotification {

    private String title = MonkeyConfig.ANDROID_APP_LABEL;
    private String text = "";
    private Map<String,String> extras = new HashMap<String,String>();
    private int _id = 0;

    public NativeLocalNotification() {
    }
    
    public void Init() {
    }

    public void NativeSchedule() {
        
        Intent intent = new Intent(BBAndroidGame.AndroidGame().GetActivity(), MonkeyGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        for (Map.Entry<String, String> entry : extras.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            intent.putExtra(key, value);
        }
        
        PendingIntent pIntent = PendingIntent.getActivity(BBAndroidGame.AndroidGame().GetActivity(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder b = new NotificationCompat.Builder(BBAndroidGame.AndroidGame().GetActivity());
        Notification noti = b.setContentTitle(title)
                            .setContentText(text)
                            .setSmallIcon(R.drawable.icon)
                            .setContentIntent(pIntent)
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                            .setAutoCancel(true)
                            .build();
                                            
        NotificationManager notificationManager = (NotificationManager)BBAndroidGame.AndroidGame().GetActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        
        notificationManager.notify(_id, noti);
    
    }

    public void NativeScheduleAfter(int seconds) {
        
        Log.d("Notify","NativeScheduleAfter: "+seconds);
        
        AlarmManager alarmManager = (AlarmManager)BBAndroidGame.AndroidGame().GetActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(BBAndroidGame.AndroidGame().GetActivity(), AlarmNotification.class);
        intent.putExtra("_title",title);
        intent.putExtra("_text",text);
        intent.putExtra("_id",_id);
        intent.putExtra("_icon",R.drawable.icon);
        intent.putExtra("_classname",MonkeyGame.class.getCanonicalName());

        ArrayList<String> extraKeys = new ArrayList<String>();
        ArrayList<String> extraValues = new ArrayList<String>();
        for (Map.Entry<String, String> entry : extras.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            extraKeys.add(key);
            extraValues.add(value);
            Log.d("Notify","Key: "+key+" Value: "+value);
        }
        intent.putStringArrayListExtra("_keys", extraKeys);
        intent.putStringArrayListExtra("_values", extraValues);
        
        Calendar nextAlarm = Calendar.getInstance();
        nextAlarm.add(Calendar.SECOND, seconds);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BBAndroidGame.AndroidGame().GetActivity(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarm.getTimeInMillis(), pendingIntent);
        
    }
    
    public void NativeSetBody(String body) {
        text = body;
    }

    public void NativeSetAction(String action) {
        title = action;
    }

    public void NativeCancel() {
        
    }
    
    public void NativeAddExtra(String extra, String key) {
        extras.put(key,extra);
    }
    
    public void NativeSetID(int id) {
        _id = id;
    }

}

