package com.example.todo.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

/**
 * Dialog表示Utils
 */
public class DialogUtils {
    /**
     * Positive押下時にコールバックするDialog表示
     *
     * @param context
     * @param titleId
     * @param positiveTextId
     * @param negativeTextId
     * @param onPositiveClick
     */
    public static void showCallbackPositiveDialog(final Context context, final int titleId,
                                                  final int positiveTextId, final int negativeTextId,
                                                  final DialogInterface.OnClickListener onPositiveClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getString(titleId));
            alertDialog.setNegativeButton(negativeTextId,
                    (dialog, which) -> dialog.cancel());
            alertDialog.setPositiveButton(positiveTextId, onPositiveClick);
            alertDialog.show();
        }
    }
}
