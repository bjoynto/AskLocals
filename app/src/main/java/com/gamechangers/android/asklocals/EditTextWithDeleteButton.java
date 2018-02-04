package com.gamechangers.android.asklocals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class EditTextWithDeleteButton extends LinearLayout {
    protected EditText editText;
    protected ImageButton clearTextButton;

    public interface TextChangedListener extends TextWatcher {
    }

    TextChangedListener editTextListener = null;

    public void addTextChangedListener(TextChangedListener listener) {
        this.editTextListener = listener;
    }

    public EditTextWithDeleteButton(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.activity_main, this);
    }

    public EditTextWithDeleteButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public EditTextWithDeleteButton(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.EditTextWithDeleteButton, 0, 0);
        String hintText;
        int deleteButtonRes;
        try {
            hintText = getResources().getString(R.string.edit_text_hint);
            deleteButtonRes = a.getResourceId(
                    R.styleable.EditTextWithDeleteButton_deleteButtonRes,
                    android.R.drawable.ic_menu_close_clear_cancel);

        } finally {
            a.recycle();
        }
        editText = createEditText(context, hintText);
        clearTextButton = createImageButton(context, deleteButtonRes);

        final InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        this.addView(editText);
        this.addView(clearTextButton);

        editText.addTextChangedListener(txtEntered());


        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (hasFocus && editText.getText().toString().length() > 0) {
                    clearTextButton.setVisibility(View.VISIBLE);
                } else {
                    clearTextButton.setVisibility(View.GONE);
                }
            }
        });
        clearTextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                editText.setText("");
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public TextWatcher txtEntered() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (editTextListener != null)
                    editTextListener.onTextChanged(s, start, before, count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextListener != null)
                    editTextListener.afterTextChanged(s);
                if (editText.getText().toString().length() > 0)
                    clearTextButton.setVisibility(View.VISIBLE);
                else
                    clearTextButton.setVisibility(View.GONE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if (editTextListener != null)
                    editTextListener.beforeTextChanged(s, start, count, after);

            }

        };
    }

    @SuppressLint("NewApi")
    private EditText createEditText(Context context, String hintText) {
        editText = new EditText(context);
        editText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editText.setLayoutParams(new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
        editText.setHorizontallyScrolling(false);
        editText.setVerticalScrollBarEnabled(true);
        editText.setGravity(Gravity.LEFT);
        editText.setBackground(null);
        editText.setHint(hintText);
        return editText;
    }

    private ImageButton createImageButton(Context context, int deleteButtonRes) {
        clearTextButton = new ImageButton(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER_VERTICAL;
        clearTextButton.setLayoutParams(params);
        clearTextButton.setBackgroundResource(deleteButtonRes);
        clearTextButton.setVisibility(View.GONE);
        return clearTextButton;
    }

}
