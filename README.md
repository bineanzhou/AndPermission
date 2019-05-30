﻿# NSkyPermission
1. Request for runtime permissions.  
2. Share private files.  
3. Request to install unknown source apk.  
  `android.permission.REQUEST_INSTALL_PACKAGES`
4. Request to draw at the top of other apps.  
  `android.permission.SYSTEM_ALERT_WINDOW`
5. Request to show notifications.  
6. Request to access notifications.  
  `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`
7. Request to modify system setting.  
  `android.permission.WRITE_SETTINGS`

```java
NSkyPermission.with(this)
  .runtime()
  .permission(Permission.Group.STORAGE)
  .setOnPermissionsListener(permissions -> {
    // Storage permission are allowed.
  })
  .start();
```

For documentation and additional information see [the website](https://www.yanzhenjie.com/NSkyPermission).

## Download
If you are using the android support library:
```groovy
implementation 'com.nsky.permission:support:2.0.1'
```

If you are using the android x library:
```groovy
implementation 'com.nsky.permission:x:2.0.1'
```

NSkyPermission requires at minimum Android 4.0(Api level 14) .

## Contributing
Before submitting pull requests, contributors must abide by the [agreement](CONTRIBUTING.md) .

## License
```text
Copyright 2019 Zhenjie Yan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```