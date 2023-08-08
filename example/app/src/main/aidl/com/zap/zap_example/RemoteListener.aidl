package com.zap.zap_example;

import com.zap.zap_example.RemoteCallback;

interface RemoteListener {
    void registerCallback(RemoteCallback callback);
    void unregisterCallback(RemoteCallback callback);
}