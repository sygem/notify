class NativeNotificationManager : public Object, public IosAppDelegate {

public:

	void didReceiveLocalNotification(UILocalNotification *notification);

    void NativeInit();
    void NativeSetBadgeNumber(int number);
	String NativeGetExtra(String key);
	void NativeCancelAll();
	void NativeCancelScheduled(int id);

private:

	NSMutableDictionary *userInfo;
};

void NativeNotificationManager::NativeInit() {
	if ([[UIApplication sharedApplication] respondsToSelector:@selector(registerUserNotificationSettings:)]) {
		UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:(UIUserNotificationTypeBadge|UIUserNotificationTypeAlert|UIUserNotificationTypeSound) categories:nil];
		[[UIApplication sharedApplication] registerUserNotificationSettings:settings];
	} else { // iOS 7 or earlier
		UIRemoteNotificationType myTypes = UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeSound;
		[[UIApplication sharedApplication] registerForRemoteNotificationTypes:myTypes];
	}
	BBIosGame::IosGame()->AddIosAppDelegate(this);

    BBMonkeyAppDelegate *appDelegate=(BBMonkeyAppDelegate*)[[UIApplication sharedApplication] delegate];
	if (appDelegate.localNotification) {
		this->userInfo = [[appDelegate.localNotification.userInfo copy] retain];
	} else {
		this->userInfo = nil;
	}
}

void NativeNotificationManager::NativeSetBadgeNumber(int number) {
    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:number];
}

String NativeNotificationManager::NativeGetExtra(String key) {
	if (this->userInfo != nil) {
		NSString *extra = [this->userInfo objectForKey:key.ToNSString()];
		if (extra != nil) {
			[this->userInfo removeObjectForKey:key.ToNSString()];
			return [extra UTF8String];
		}
	}
	return [@"" UTF8String];
}

void NativeNotificationManager::NativeCancelAll() {
	[[UIApplication sharedApplication] cancelAllLocalNotifications];
}

void NativeNotificationManager::NativeCancelScheduled(int id) {
	for (UILocalNotification *notification in [[[UIApplication sharedApplication] scheduledLocalNotifications] copy]) {
        NSDictionary *userInfo = notification.userInfo;
        if ([[userInfo objectForKey:@"id"] intValue] == id) {
            [[UIApplication sharedApplication] cancelLocalNotification:notification];
        }
    }
}

void NativeNotificationManager::didReceiveLocalNotification(UILocalNotification *notification) {
	if (this->userInfo != NULL) {
		[this->userInfo release];
	}
	this->userInfo = [NSMutableDictionary dictionaryWithDictionary:notification.userInfo];
}