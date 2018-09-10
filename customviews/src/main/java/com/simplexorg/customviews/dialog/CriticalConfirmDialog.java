package com.simplexorg.customviews.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.simplexorg.customviews.R;

import java.util.Locale;

/**
 * Shows a critical confirm dialog to the user in which they have
 * to explicitly confirm inorder for a given action to be carried out.
 */
public class CriticalConfirmDialog extends DialogFragment {
    public interface OnConfirmListener {
        void onConfirm();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    private String mInputKeyword = "DELETE";
    private OnConfirmListener mOnConfirmListener;
    private OnCancelListener mOnCancelListener;
    private EditText mConfirmInputText;

    public void setInputKeyword(@NonNull String keyword) {
        mInputKeyword = keyword;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.customviews_dialog_critical_confirm, null);
        TextView confirmMsgText = view.findViewById(R.id.customviews_confirm_msg);
        mConfirmInputText = view.findViewById(R.id.customviews_confirm_input);
        confirmMsgText.setText(String.format(Locale.CHINA, confirmMsgText.getText().toString(), mInputKeyword));

        builder.setView(view)
                .setPositiveButton(R.string.customviews_confirm,
                        (DialogInterface dialogInterface, int id) -> {
                        })
                .setNegativeButton(R.string.customviews_cancel,
                        (DialogInterface dialogInterface, int id) -> {
                            if (mOnCancelListener != null) {
                                mOnCancelListener.onCancel();
                            }
                        });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener((View view) -> {
            if (mInputKeyword.equals(mConfirmInputText.getText().toString())) {
                alertDialog.dismiss();
                if (mOnConfirmListener != null) {
                    mOnConfirmListener.onConfirm();
                }
            } else {
                mConfirmInputText.setError("Invalid");
            }
        });
    }
}
