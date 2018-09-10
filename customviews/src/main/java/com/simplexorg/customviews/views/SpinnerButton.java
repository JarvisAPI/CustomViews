package com.simplexorg.customviews.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.simplexorg.customviews.R;

/**
 * Button with loading spinner.
 */
public class SpinnerButton extends RelativeLayout {
    private Button mButton;
    private CharSequence mText;
    private ProgressBar mProgressBar;

    public SpinnerButton(Context context) {
        super(context);
        init();
    }

    public SpinnerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttributes(context, attrs);
    }

    public SpinnerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinnerButton);

        ColorStateList buttonColor = typedArray.getColorStateList(R.styleable.SpinnerButton_button_color);
        if (buttonColor != null) {
            mButton.setBackgroundTintList(buttonColor);
        }

        ColorStateList textColor = typedArray.getColorStateList(R.styleable.SpinnerButton_text_color);
        if (textColor != null) {
            mButton.setTextColor(textColor);
        }

        mText = typedArray.getText(R.styleable.SpinnerButton_text);
        mButton.setText(mText);

        typedArray.recycle();
    }

    @Override
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        super.setBackgroundTintList(tint);
    }

    private void init() {
        inflate(getContext(), R.layout.customviews_button_spinner, this);
        mButton = findViewById(R.id.customviews_button);
        mProgressBar = findViewById(R.id.customviews_progress);
    }

    public void setText(CharSequence text) {
        mText = text;
        mButton.setText(text);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        mButton.setOnClickListener((View view) ->
                onClickListener.onClick(SpinnerButton.this));
    }

    @Override
    public void setEnabled(boolean enabled) {
        mButton.setEnabled(enabled);
    }

    public void spin(boolean spin) {
        if (spin) {
            mButton.setEnabled(false);
            mButton.setText(null);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mButton.setEnabled(true);
            mButton.setText(mText);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
