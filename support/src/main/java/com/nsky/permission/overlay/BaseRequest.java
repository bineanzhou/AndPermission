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
package com.nsky.permission.overlay;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import com.nsky.permission.OnPermissionsListener;
import com.nsky.permission.R;
import com.nsky.permission.Rationale;
import com.nsky.permission.RequestExecutor;
import com.nsky.permission.source.Source;

/**
 * Created by YanZhenjie on 2018/6/1.
 */
abstract class BaseRequest implements OverlayRequest {

    private Source mSource;

    private Rationale<Void> mRationale = new Rationale<Void>() {
        @Override
        public void showRationale(Context context, Void data, RequestExecutor executor) {
            executor.execute();
        }
    };
    private OnPermissionsListener<Void> mPermission;

    BaseRequest(Source source) {
        this.mSource = source;
    }

    @Override
    public final OverlayRequest rationale(Rationale<Void> rationale) {
        this.mRationale = rationale;
        return this;
    }

    @Override
    public final OverlayRequest setOnPermissionsListener(OnPermissionsListener<Void> granted) {
        this.mPermission = granted;
        return this;
    }


    /**
     * Why permissions are required.
     */
    final void showRationale(RequestExecutor executor) {
        mRationale.showRationale(mSource.getContext(), null, executor);
    }

    /**
     * Callback acceptance status.
     */
    final void callbackSucceed() {
        if (mPermission != null) {
            mPermission.onPermissionsGranted(null);
        }
    }

    /**
     * Callback rejected state.
     */
    final void callbackFailed() {
        if (mPermission != null) {
            mPermission.onPermissionsDenied(null);
        }
    }

    static boolean tryDisplayDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.Permission_Theme);
        int overlay = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        int alertWindow = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int windowType = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? overlay : alertWindow;
        dialog.getWindow().setType(windowType);
        try {
            dialog.show();
        } catch (Exception e) {
            return false;
        } finally {
            if (dialog.isShowing()) dialog.dismiss();
        }
        return true;
    }
}