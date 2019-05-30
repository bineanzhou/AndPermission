/*
 * Copyright 2019 Zhenjie Yan
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
package com.nsky.permission.notify.listener;

import com.nsky.permission.OnPermissionsListener;
import com.nsky.permission.Rationale;

/**
 * Created by Zhenjie Yan on 2/14/19.
 */
public interface ListenerRequest {

    /**
     * Set request rationale.
     */
    ListenerRequest rationale(Rationale<Void> rationale);

    /**
     * OnPermissionsListener to be taken when all permissions are granted.
     */
    ListenerRequest setOnPermissionsListener(OnPermissionsListener<Void> granted);


    /**
     * Start install.
     */
    void start();

}