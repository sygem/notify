Strict

#If TARGET<>"android" And TARGET<>"ios"
'#Error "The local notification module is only available on the android and ios targets"
#End

#If TARGET="android"

Import "native/android/localnotifications.android.java"
#LIBS+="${CD}/native/android/alarmreceiver.jar"
#LIBS+="${CD}/native/android/android-support-v4.jar"
#ANDROID_MANIFEST_APPLICATION+="<receiver android:name=~qcom.sygem.monkey.notify.AlarmNotification~q />"

#ElseIf TARGET="ios"

Import "native/ios/localnotifications.ios.cpp"

#End

Class LocalNotification Extends NativeLocalNotification
    
    Method New()
        Init()
    End
    
    Method SetFireTime:Void()
        
    End
    
    Method SetBody:Void(body:String)
        NativeSetBody(body)
    End
    
    Method SetAction:Void(action:String)
        NativeSetAction(action)
    End
    
    Method Schedule:Void()
        NativeSchedule()
    End
    
    Method ScheduleAfter:Void(seconds:Int)
        NativeScheduleAfter(seconds)
    End
    
    Method Cancel:Void()
        NativeCancel()
    End
    
    Method SetID:Void(id:Int)
        NativeSetID(id)
    End

    Method AddExtra:Void(extra:String, key:String)
        NativeAddExtra(extra, key)
    End
End

Private

#If TARGET<>"android" And TARGET<>"ios"

Class NativeLocalNotification

    Private
    Method Init:Void()
    End
    Method NativeSchedule:Void()
    End
    Method NativeScheduleAfter:Void(seconds:Int)
    End
    Method NativeSetBody:Void(body:String)
    End
    Method NativeSetAction:Void(action:String)
    End
    Method NativeCancel:Void()
    End
    Method NativeAddExtra:Void(extra:String, key:String)
    End
    Method NativeSetID:Void(id:Int)
    End

End

#Else

Extern

Class NativeLocalNotification

    Private
    Method Init:Void()
    Method NativeSchedule:Void()
    Method NativeScheduleAfter:Void(seconds:Int)
    Method NativeSetBody:Void(body:String)
    Method NativeSetAction:Void(action:String)
    Method NativeCancel:Void()
    Method NativeAddExtra:Void(extra:String, key:String)
    Method NativeSetID:Void(id:Int)

End

#End