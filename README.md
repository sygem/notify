notify
=======

Local & Push Notifications module for [MonkeyX]. Push notifications to follow shortly, using [Parse]!

Supported Targets
-----------------
- Android
- iOS

Installation Instructions
-------------------------

###Android
- Nothing to do
 
###iOS
- Requires my pull request : https://github.com/blitz-research/monkey/pull/56

Usage Example
-------------
###Fire local notification
```
Local localn:LocalNotification = New LocalNotification()
localn.SetBody("Some body text")            ' Will appear in the notification
localn.Schedule()                           ' Will be raised immediately
```

###Schedule local notification
```
Method OnSuspend:Int()

    Local localn:LocalNotification = New LocalNotification()
    localn.SetBody("Some body text")            ' Will appear in the notification
    localn.AddExtra("monkey rules", "data")     ' optional, can be retrieved later
    localn.SetID(0)                             ' a unique id - use this to cancel notifications
    localn.ScheduleAfter(60)                    ' number of seconds to wait before firing
    
    Return 0
End
```

###Cancelling notifications
```
Method OnResume:Int()
    NotificationManager.GetInstance().CancelScheduled(0)    ' the same ID used when creating the notification
    Return 0
End
```

###Extract data from notification
```
Method OnCreate:Int()   ' if notification clicked on when app is not running
	
    Local extra:String = NotificationManager.GetInstance().GetExtra("data")
    If extra
        Print("Got some data from notification!! (onCreate) : " + extra)
    EndIf

    Return 0
End

   Method OnResume:Int() ' if notification clicked on when app is still running

    Local extra:String = NotificationManager.GetInstance().GetExtra("data")
    If extra
        Print("Got some data from notification!! (onResume) : " + extra)
    EndIf

    Return 0
End	

```

Version
-------
0.1a

[MonkeyX]:http://http://www.monkey-x.com/   
[Parse]:https://parse.com/

