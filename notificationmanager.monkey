Strict

#If TARGET<>"android" And TARGET<>"ios"
'#Error "The local notification module is only available on the android and ios targets"
#End

#If TARGET="android"

Import "native/android/notificationmanager.android.java"

#ElseIf TARGET="ios"

Import "native/ios/notificationmanager.ios.cpp"

#End

Class NotificationManager Extends NativeNotificationManager
    
    Function GetInstance:NotificationManager()
        If not instance
            instance = New NotificationManager()
        EndIf
        Return instance
    End

    Method CancelScheduled:Void(id:Int)
        NativeCancelScheduled(id)
    End

    Method CancelAll:Void()
        NativeCancelAll()
    End
    
    Method GetExtra:String(key:String)
        Return NativeGetExtra(key)
    End
    
    #if TARGET="ios"    
    Method SetBadgeNumber:Void(number:Int = -1)
        NativeSetBadgeNumber(number)
    End
    #end

    Private
    'Global init:Bool = False
    Global instance:NotificationManager
    
    Method New()
        'If not init
        NativeInit()
        '    init = True
        'EndIf
    End
    

End

#If TARGET<>"android" And TARGET<>"ios"

Class NativeNotificationManager

    Private
    Method NativeInit:Void()
    End
    Method NativeGetExtra:String(key:String)
        Return ""
    End
    Method NativeCancelAll:Void()
    End
    Method NativeCancelScheduled:Void(id:Int)
    End

End
#else

Extern
Class NativeNotificationManager
    Method NativeInit:Void()
    Method NativeGetExtra:String(key:String)
    Method NativeCancelAll:Void()
    #if TARGET="ios"
    Method NativeSetBadgeNumber:Void(number:Int)
    #end
    Method NativeCancelScheduled:Void(id:Int)
End

#end