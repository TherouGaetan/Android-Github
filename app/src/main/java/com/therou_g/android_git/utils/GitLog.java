package com.therou_g.android_git.utils;

import android.util.Log;

import com.therou_g.android_git.BuildConfig;

/**
 * Created by gaetan
 *
 * On 18-02-24 at 17:03.
 */

public class GitLog {
    public enum InalityLogType {
        ERROR,
        DEBUG,
        INFO,
    }

    /**
     * Check if app is on Debug mode
     *
     * @param pTag  Name of class to generate Message
     * @param pMsg  Message at display
     */
    public static void log(String pTag, String pMsg) {
        log(pTag, pMsg, InalityLogType.DEBUG);
    }

    /**
     * Check if app is on Debug mode
     *
     * @param pTag  Name of class to generate Message
     * @param pMsg  Message at display
     * @param pType Type of message
     */
    public static void log(String pTag, String pMsg, InalityLogType pType) {
        if (BuildConfig.DEBUG) {
            switch (pType) {
                case INFO:
                    Log.i(pTag, pMsg);
                    break;
                case DEBUG:
                    Log.d(pTag, pMsg);
                    break;
                case ERROR:
                    Log.e(pTag, pMsg);
                    break;
            }
        }
    }
}
