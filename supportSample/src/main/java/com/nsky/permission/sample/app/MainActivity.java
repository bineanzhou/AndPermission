/*
 * Copyright © Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nsky.permission.sample.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nsky.permission.NSkyPermission;
import com.nsky.permission.OnPermissionsListener;
import com.nsky.permission.rational.InstallRationale;
import com.nsky.permission.rational.NotifyListenerRationale;
import com.nsky.permission.rational.NotifyRationale;
import com.nsky.permission.rational.OverlayRationale;
import com.nsky.permission.rational.RuntimeRationale;
import com.nsky.permission.rational.WriteSettingRationale;
import com.nsky.permission.runtime.Permission;
import com.nsky.permission.sample.App;
import com.nsky.permission.sample.R;

import java.io.File;
import java.util.List;

/**
 * Created by Zhenjie Yan on 2016/9/17.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_request_camera).setOnClickListener(this);
        findViewById(R.id.btn_request_contact).setOnClickListener(this);
        findViewById(R.id.btn_request_location).setOnClickListener(this);
        findViewById(R.id.btn_request_calendar).setOnClickListener(this);
        findViewById(R.id.btn_request_recordaudio).setOnClickListener(this);
        findViewById(R.id.btn_request_storage).setOnClickListener(this);
        findViewById(R.id.btn_request_phone).setOnClickListener(this);
        findViewById(R.id.btn_request_sensors).setOnClickListener(this);
        findViewById(R.id.btn_request_sms).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);

        findViewById(R.id.btn_notification).setOnClickListener(this);
        findViewById(R.id.btn_notification_listener).setOnClickListener(this);

        findViewById(R.id.btn_install).setOnClickListener(this);
        findViewById(R.id.btn_overlay).setOnClickListener(this);
        findViewById(R.id.btn_write_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_camera: {
                requestPermission(Permission.Group.CAMERA);
                break;
            }
            case R.id.btn_request_contact: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.contacts));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getItemId();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_CONTACTS);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_CONTACTS);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.GET_ACCOUNTS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.Group.CONTACTS);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_location: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.location));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getItemId();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.ACCESS_FINE_LOCATION);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.ACCESS_COARSE_LOCATION);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.LOCATION);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_calendar: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.calendar));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_CALENDAR);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_CALENDAR);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.CALENDAR);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_recordaudio: {
                requestPermission(Permission.Group.RECORD_AUDIO);
                break;
            }
            case R.id.btn_request_storage: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.storage));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_EXTERNAL_STORAGE);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_EXTERNAL_STORAGE);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.STORAGE);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_phone: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.phone));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_PHONE_STATE);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.CALL_PHONE);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.READ_CALL_LOG);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.WRITE_CALL_LOG);
                                break;
                            }
                            case 4: {
                                requestPermission(Permission.ADD_VOICEMAIL);
                                break;
                            }
                            case 5: {
                                requestPermission(Permission.USE_SIP);
                                break;
                            }
                            case 6: {
                                requestPermission(Permission.PROCESS_OUTGOING_CALLS);
                                break;
                            }
                            case 7: {
                                requestPermission(Permission.Group.PHONE);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_sensors: {
                requestPermission(Permission.Group.SENSORS);
                break;
            }
            case R.id.btn_request_sms: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.sms));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.SEND_SMS);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.RECEIVE_SMS);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.READ_SMS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.RECEIVE_WAP_PUSH);
                                break;
                            }
                            case 4: {
                                requestPermission(Permission.RECEIVE_MMS);
                                break;
                            }
                            case 5: {
                                requestPermission(Permission.Group.SMS);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_setting: {
                NSkyPermission.startSetPermission(this, REQUEST_CODE_SETTING);
                break;
            }
            case R.id.btn_notification: {
                requestNotification();
                break;
            }
            case R.id.btn_notification_listener: {
                requestNotificationListener();
                break;
            }
            case R.id.btn_install: {
                requestPermissionForInstallPackage();
                break;
            }
            case R.id.btn_overlay: {
                requestPermissionForAlertWindow();
                break;
            }
            case R.id.btn_write_setting: {
                requestWriteSystemSetting();
                break;
            }
        }
    }

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        if (NSkyPermission.hasPermissions(this, permissions)) {
            toast("to do");
            return;
        }
        NSkyPermission.requestPermissions(this, permissions)
//            .rationale(new RuntimeRationale())
                .setOnPermissionsListener(new OnPermissionsListener<List<String>>() {
                    @Override
                    public void onPermissionsGranted(List<String> permissions) {
                        toast(R.string.successfully);
                    }

                    @Override
                    public void onPermissionsDenied(List<String> permissions) {
                        toast(R.string.failure);
                        if (NSkyPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            NSkyPermission.showSettingDialog(MainActivity.this, permissions, REQUEST_CODE_SETTING);
                        }
                    }
                })
                .start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(MainActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * Request notification permission.
     */
    private void requestNotification() {
        NSkyPermission.with(this)
                .notification()
                .permission()
                .rationale(new NotifyRationale())
                .setOnPermissionsListener(new OnPermissionsListener<Void>() {

                    @Override
                    public void onPermissionsGranted(Void data) {
                        toast(R.string.successfully);
                    }

                    @Override
                    public void onPermissionsDenied(Void data) {
                        toast(R.string.failure);
                    }
                })
                .start();
    }

    /**
     * Request notification listener.
     */
    private void requestNotificationListener() {
        NSkyPermission.with(this)
                .notification()
                .listener()
                .rationale(new NotifyListenerRationale())
                .setOnPermissionsListener(new OnPermissionsListener<Void>() {

                    @Override
                    public void onPermissionsGranted(Void data) {
                        toast(R.string.successfully);
                    }

                    @Override
                    public void onPermissionsDenied(Void data) {
                        toast(R.string.failure);
                    }
                })
                .start();
    }

    /**
     * Request to read and write external storage permissions.
     */
    private void requestPermissionForInstallPackage() {
        NSkyPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .rationale(new RuntimeRationale())
                .setOnPermissionsListener(new OnPermissionsListener<List<String>>() {

                    @Override
                    public void onPermissionsGranted(List<String> data) {
                        new WriteApkTask(MainActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                installPackage();
                            }
                        }).execute();
                    }

                    @Override
                    public void onPermissionsDenied(List<String> data) {
                        toast(R.string.permission_message_install_failed);

                    }
                })
                .start();
    }

    /**
     * Install package.
     */
    private void installPackage() {
        NSkyPermission.with(this)
                .install()
                .file(new File(Environment.getExternalStorageDirectory(), "android.apk"))
                .rationale(new InstallRationale())
                .setOnPermissionsListener(new OnPermissionsListener<File>() {

                    @Override
                    public void onPermissionsGranted(File data) {
                        // Installing.
                        toast(R.string.successfully);
                    }

                    @Override
                    public void onPermissionsDenied(File data) {
                        // The user refused to install.
                        toast(R.string.failure);
                    }
                })
                .start();
    }

    private void requestPermissionForAlertWindow() {
        NSkyPermission.with(this).overlay().rationale(new OverlayRationale()).setOnPermissionsListener(new OnPermissionsListener<Void>() {

            @Override
            public void onPermissionsGranted(Void data) {
                toast(R.string.successfully);
                showAlertWindow();
            }

            @Override
            public void onPermissionsDenied(Void data) {
                toast(R.string.failure);
            }
        }).start();
    }

    private void requestWriteSystemSetting() {
        NSkyPermission.with(this).setting().write().rationale(new WriteSettingRationale()).setOnPermissionsListener(new OnPermissionsListener<Void>() {

            @Override
            public void onPermissionsGranted(Void data) {
                toast(R.string.successfully);
            }

            @Override
            public void onPermissionsDenied(Void data) {
                toast(R.string.failure);
            }
        }).start();
    }

    private void showAlertWindow() {
        App.getInstance().showLauncherView();

        Intent backHome = new Intent(Intent.ACTION_MAIN);
        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backHome.addCategory(Intent.CATEGORY_HOME);
        startActivity(backHome);
    }

    /**
     * Create menu.
     */
    private PopupMenu createMenu(View v, String[] menuArray) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menuArray.length; i++) {
            String menuText = menuArray[i];
            menu.add(0, i, i, menuText);
        }
        return popupMenu;
    }

    protected void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}