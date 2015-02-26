
class NativeNotificationManager {
    
    private static boolean removeExtras = true;
    
    public void NativeInit() {
        if (!init) {
            BBAndroidGame.AndroidGame().AddActivityDelegate(new ActivityDelegate() {
                public void onNewIntent( Intent intent ){
                    BBAndroidGame.AndroidGame().GetActivity().setIntent(intent);
                }
            });
            init = true;
        }
    }
    
    private boolean init = false;
    
    public String NativeGetExtra(String key) {
        //Log.i("Notify","Looking for extra: "+key);
        Intent intent = BBAndroidGame.AndroidGame().GetActivity().getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //Log.i("Notify","Got some extras");
            if (extras.containsKey(key)) {
                //Log.i("Notify","Got extra: "+key);
                String extra = extras.getString(key);
                //Log.i("Notify","Extra value: "+extra);
                if (extra != null) {
                    if (removeExtras) {
                        intent.removeExtra(key);
                    }
                    return extra;
                }
            }
        }
        return "";
    }
    
    public void NativeCancelAll() {
        NotificationManager notificationManager = (NotificationManager)BBAndroidGame.AndroidGame().GetActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
    
    public void NativeCancelScheduled(int id) {
        Intent intent = new Intent(BBAndroidGame.AndroidGame().GetActivity(), AlarmNotification.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(BBAndroidGame.AndroidGame().GetActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) BBAndroidGame.AndroidGame().GetActivity().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pIntent);
    }
    
}