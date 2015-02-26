static int notificationID = 0;

class NativeLocalNotification : public Object {

    NSString *body;
    NSMutableArray *_extras;
    int _id;

public:
    NativeLocalNotification() : _id(0) {}
    void Init();
    void NativeSetBody(String body);
    void NativeSetAction(String action);
    int NativeSchedule();
    int NativeScheduleAfter(int seconds);
    void NativeSetID(int id);
    void NativeAddExtra(String extra, String key);
private:
    int scheduleAfterSeconds(int seconds);
};

void NativeLocalNotification::Init() {
    _extras = [NSMutableArray arrayWithCapacity:2];
}

int NativeLocalNotification::scheduleAfterSeconds(int seconds) {

    // Schedule the notification
    UILocalNotification* localNotification = [[UILocalNotification alloc] init];
    localNotification.fireDate = [NSDate dateWithTimeIntervalSinceNow:seconds];
    localNotification.alertBody = this->body;
    localNotification.timeZone = [NSTimeZone defaultTimeZone];

    NSMutableArray *objects = [NSMutableArray arrayWithCapacity:1];
    NSMutableArray *keys = [NSMutableArray arrayWithCapacity:1];

    [objects addObject:[NSString stringWithFormat:@"%i",_id]];
    [keys addObject:@"id"];

    for (int i=0;i<[_extras count];i+=2) {
        [objects addObject:[_extras objectAtIndex:i]];
        [keys addObject:[_extras objectAtIndex:i+1]];
    }

    NSDictionary *userInfo = [NSDictionary dictionaryWithObjects:objects forKeys:keys];

    [localNotification setUserInfo:userInfo]; 

    [[UIApplication sharedApplication] scheduleLocalNotification:localNotification];

    return notificationID++;
}

int NativeLocalNotification::NativeScheduleAfter(int seconds) {
    return scheduleAfterSeconds(seconds);
}

int NativeLocalNotification::NativeSchedule() {
    return scheduleAfterSeconds(0);
}

void NativeLocalNotification::NativeSetBody(String body) {
    this->body = body.ToNSString();
}

void NativeLocalNotification::NativeSetAction(String action) {
    // currently unsupported
}

void NativeLocalNotification::NativeSetID(int id) {
    _id = id;
}

void NativeLocalNotification::NativeAddExtra(String extra, String key) {
    [_extras addObject:extra.ToNSString()];
    [_extras addObject:key.ToNSString()];
}