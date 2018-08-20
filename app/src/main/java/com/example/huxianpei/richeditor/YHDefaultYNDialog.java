package com.example.huxianpei.richeditor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 提示需要绑定手机的提示框
 * Created by HXP on 2017/1/17.
 */
public class YHDefaultYNDialog extends Dialog implements DialogInterface {
    private TextView title, mainTitle;
    private TextView btn_ok, btn_no,btn_cancel;
    private EditText inputText;

    public YHDefaultYNDialog(Context context) {
        super(context, R.style.dialog);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setContentView(R.layout.dialog_band);
        title = (TextView) findViewById(R.id.tv_default_title);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 3 / 4);
        getWindow().setAttributes(lp);
        btn_ok = (TextView) findViewById(R.id.btn_default_ok);
        btn_no = (TextView) findViewById(R.id.btn_default_no);
        btn_cancel = (TextView) findViewById(R.id.btn_default_cancel);
        mainTitle = (TextView) findViewById(R.id.main_title);
        inputText = (EditText) findViewById(R.id.input_text);
        btn_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YHDefaultYNDialog.this.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YHDefaultYNDialog.this.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YHDefaultYNDialog.this.dismiss();
            }
        });
    }

    public YHDefaultYNDialog(Context context, boolean okOnly) {
        this(context);
        if (okOnly) {
            btn_no.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }
    }

    public YHDefaultYNDialog setTitleText(CharSequence data) {
        mainTitle.setText(data);
        mainTitle.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public void setTitle(CharSequence data) {
        title.setText(data);
    }

    public YHDefaultYNDialog setText(CharSequence data) {
        title.setText(data);
        return this;
    }

    public YHDefaultYNDialog setText(SpannableString data) {
        title.setText(data);
        return this;
    }

    public YHDefaultYNDialog changeGravityLeft() {
        title.setGravity(Gravity.LEFT);
        return this;
    }

    public YHDefaultYNDialog setCanceledOutside(boolean canceledOnTouchOutside) {
        super.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    public YHDefaultYNDialog setIsCancelable(boolean canceledOnTouchOutside) {
        super.setCancelable(canceledOnTouchOutside);
        return this;
    }

    public YHDefaultYNDialog setDismissListener(OnDismissListener canceledOnTouchOutside) {
        super.setOnDismissListener(canceledOnTouchOutside);
        return this;
    }

    public YHDefaultYNDialog setMainTitle(String string) {
        if (YHStringUtils.isEmpty(string)) return this;
        mainTitle.setVisibility(View.VISIBLE);
        mainTitle.setText(string);
        return this;
    }

    public YHDefaultYNDialog setInputHint(String string) {
        inputText.setVisibility(View.VISIBLE);
        inputText.setHint(string);
        return this;
    }

    public YHDefaultYNDialog setInputText(String string) {
        if (!YHStringUtils.isEmpty(string)) {
            inputText.setVisibility(View.VISIBLE);
            inputText.setText(string);
        }
        return this;
    }

    public YHDefaultYNDialog setEnsure(CharSequence data) {
        btn_ok.setText(data);
        return this;
    }

    public YHDefaultYNDialog setCancel(CharSequence data) {
        btn_cancel.setText(data);
        return this;
    }

    public YHDefaultYNDialog setNo(CharSequence data) {
        btn_no.setText(data);
        btn_no.setVisibility(View.VISIBLE);
        return this;
    }

    public String getInputText() {
        return inputText.getText().toString().trim();
    }

    public YHDefaultYNDialog setNoButton(final View.OnClickListener onClickListener) {
        if (btn_cancel != null) {
            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }
        return this;
    }

    public YHDefaultYNDialog setNo1Button(final View.OnClickListener onClickListener) {
        if (btn_no != null) {
            btn_no.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }
        return this;
    }

    public YHDefaultYNDialog setOKDissMissButton(final View.OnClickListener onClickListener) {
        if (btn_no != null) {
            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }
        return this;
    }

    public YHDefaultYNDialog setOkButton(final View.OnClickListener onClickListener) {
        if (btn_ok != null) {
            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    onClickListener.onClick(v);
                }
            });
        }
        return this;
    }

    public YHDefaultYNDialog setOkButton(final View.OnClickListener onClickListener, final boolean dis) {
        if (btn_ok != null) {
            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v);
                    if (dis) {
                        dismiss();
                    }
                }
            });
        }
        return this;
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
        }
    }
}