# DataWedge-Notify-Sample

This sample shows how to use the Notify API to communicate with the Bluetooth scanner LEDs and beeper

API documentation:
[https://techdocs.zebra.com/datawedge/latest/guide/api/notify/](https://techdocs.zebra.com/datawedge/latest/guide/api/notify/)

[![Video Demo](https://img.youtube.com/vi/9lAqXa-aLeA/0.jpg)](https://www.youtube.com/watch?v=9lAqXa-aLeA)

```kotlin
val i = Intent()
val bundleNotify = Bundle()
val bundleNotificationConfig = Bundle()
i.action = "com.symbol.datawedge.api.ACTION";
//  Device identifier is available from the scanner enumeration
//  See the API docs for supported scanners.
bundleNotificationConfig.putString("DEVICE_IDENTIFIER", "BLUETOOTH_RS6000")
//  Pass in up to 5 parameters (one of which may be an LED instruction)
//  to be executed in series.  Mapping of these numbers to what they represent 
//  is available in the documentation
bundleNotificationConfig.putIntArray("NOTIFICATION_SETTINGS", intArrayOf(17, 23, 8))
bundleNotify.putBundle("NOTIFICATION_CONFIG", bundleNotificationConfig)
i.putExtra("NOTIFY_EXTRA", bundleNotify)
i.putExtra("SEND_RESULT", "true")
this.sendBroadcast(i)
```

## Notes:
- The Notify API accepts up to 5 parameters but only one of these can be an LED instruction (e.g. turn red LED on).  If you want to flash the LEDs or alternate between red and green, for example, you will need to call the API multiple times.
- The device ID is available from [https://techdocs.zebra.com/datawedge/latest/guide/api/setconfig/#scanneridentifiers](https://techdocs.zebra.com/datawedge/latest/guide/api/setconfig/#scanneridentifiers) as well as the response from scanner enumeration.
- The mapping between the integers passed to 'Notification_Settings' and what those integers mean is defined in the [API documentation](https://techdocs.zebra.com/datawedge/latest/guide/api/notify/)


![Sample](https://raw.githubusercontent.com/darryncampbell/DataWedge-Notify-Sample/main/screenshots/001.jpg)

![Sample](https://raw.githubusercontent.com/darryncampbell/DataWedge-Notify-Sample/main/screenshots/002.jpg)




