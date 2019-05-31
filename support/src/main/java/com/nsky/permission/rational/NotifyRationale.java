/*
 * Copyright 2018 Zhenjie Yan
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
package com.nsky.permission.rational;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nsky.permission.R;
import com.nsky.permission.Rationale;
import com.nsky.permission.RequestExecutor;

/**
 * Created by Zhenjie Yan on 2018/5/30.
 */
public class NotifyRationale implements Rationale<Void> {

    @Override
    public void showRationale(Context context, Void data, final RequestExecutor executor) {
        new AlertDialog.Builder(context, R.style.Permission_Dialog).setCancelable(false)
            .setTitle(R.string.permission_title_dialog)
            .setMessage(R.string.permission_message_notification_rationale)
            .setPositiveButton(R.string.permission_setting, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    executor.execute();
                }
            })
            .setNegativeButton(R.string.permission_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    executor.cancel();
                }
            })
            .show();
    }
}