package com.example.todo.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * ActivityのUtils
 */
public class ActivityUtils {

    /**
     * ActivityにFragmentを加えて遷移
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragmentToActivity(@NonNull final FragmentManager fragmentManager,
                                             @NonNull final Fragment fragment, final int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}
