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
package com.nsky.permission.runtime;

import android.os.AsyncTask;
import android.util.Log;

import com.nsky.permission.OnPermissionsListener;
import com.nsky.permission.Rationale;
import com.nsky.permission.checker.PermissionChecker;
import com.nsky.permission.checker.StrictChecker;
import com.nsky.permission.source.Source;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by YanZhenjie on 2018/1/25.
 */
class LRequest implements PermissionRequest {

    private static final PermissionChecker STRICT_CHECKER = new StrictChecker();

    private Source mSource;

    private String[] mPermissions;
    private OnPermissionsListener<List<String>> mPermission;

    LRequest(Source source) {
        this.mSource = source;
    }

    @Override
    public PermissionRequest permission(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    @Override
    public PermissionRequest rationale(Rationale<List<String>> rationale) {
        return this;
    }

    @Override
    public PermissionRequest setOnPermissionsListener(OnPermissionsListener<List<String>> granted) {
        this.mPermission = granted;
        return this;
    }


    @Override
    public void start() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return getDeniedPermissions(STRICT_CHECKER, mSource, mPermissions);
            }

            @Override
            protected void onPostExecute(List<String> deniedList) {
                if (deniedList.isEmpty()) {
                    callbackSucceed();
                } else {
                    callbackFailed(deniedList);
                }
            }
        }.execute();
    }

    /**
     * Callback acceptance status.
     */
    private void callbackSucceed() {
        if (mPermission != null) {
            List<String> permissionList = asList(mPermissions);
            try {
                mPermission.onPermissionsGranted(permissionList);
            } catch (Exception e) {
                Log.e("NSkyPermission", "Please check the setOnPermissionsListener() method body for bugs.", e);
                if (mPermission != null) {
                    mPermission.onPermissionsDenied(permissionList);
                }
            }
        }
    }

    /**
     * Callback rejected state.
     */
    private void callbackFailed(List<String> deniedList) {
        if (mPermission != null) {
            mPermission.onPermissionsDenied(deniedList);
        }
    }

    /**
     * Get denied permissions.
     */
    private static List<String> getDeniedPermissions(PermissionChecker checker, Source source, String... permissions) {
        List<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions) {
            if (!checker.hasPermission(source.getContext(), permission)) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }
}