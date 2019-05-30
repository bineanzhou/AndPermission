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
package com.nsky.permission.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nsky.permission.OnPermissionsListener;
import com.nsky.permission.NSkyPermission;
import com.nsky.permission.Rationale;
import com.nsky.permission.RequestExecutor;
import com.nsky.permission.source.Source;

import java.io.File;

/**
 * Created by Zhenjie Yan on 2018/6/1.
 */
abstract class BaseRequest implements InstallRequest {

    private Source mSource;

    private File mFile;
    private Rationale<File> mRationale = new Rationale<File>() {
        @Override
        public void showRationale(Context context, File data, RequestExecutor executor) {
            executor.execute();
        }
    };
    private OnPermissionsListener<File> mPermission;

    BaseRequest(Source source) {
        this.mSource = source;
    }

    @Override
    public final InstallRequest file(File file) {
        this.mFile = file;
        return this;
    }

    @Override
    public final InstallRequest rationale(Rationale<File> rationale) {
        this.mRationale = rationale;
        return this;
    }

    @Override
    public final InstallRequest setOnPermissionsListener(OnPermissionsListener<File> granted) {
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
     * Start the installation.
     */
    final void install() {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = NSkyPermission.getFileUri(mSource.getContext(), mFile);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mSource.startActivity(intent);
    }

    /**
     * Callback acceptance status.
     */
    final void callbackSucceed() {
        if (mPermission != null) {
            mPermission.onPermissionsGranted(mFile);
        }
    }

    /**
     * Callback rejected state.
     */
    final void callbackFailed() {
        if (mPermission != null) {
            mPermission.onPermissionsDenied(mFile);
        }
    }
}