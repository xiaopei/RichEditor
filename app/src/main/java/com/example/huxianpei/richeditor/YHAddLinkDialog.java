package com.example.huxianpei.richeditor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 富文本添加链接
 * Created by HXP on 2017/1/17.
 */
public class YHAddLinkDialog extends Dialog implements DialogInterface {
    private TextView title, mainTitle;
    private TextView btn_ok, btn_no;
    private EditText inputText;
    private EditText inputName;
    private Context context;

    public YHAddLinkDialog(final Context context) {
        super(context, R.style.dialog);
        this.context = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setContentView(R.layout.dialog_add_link);
        title = (TextView) findViewById(R.id.tv_default_title);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels  * 3 / 4);
        getWindow().setAttributes(lp);
        btn_ok = (TextView) findViewById(R.id.btn_default_ok);
        btn_no = (TextView) findViewById(R.id.btn_default_no);
        mainTitle = (TextView) findViewById(R.id.main_title);
        inputText = (EditText) findViewById(R.id.input_text);
        inputName = (EditText) findViewById(R.id.input_name);
        btn_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YHAddLinkDialog.this.dismiss();
            }
        });
    }

    @Override
    public void setTitle(CharSequence data) {
        title.setText(data);
    }

    public YHAddLinkDialog setText(CharSequence data) {
        title.setText(data);
        return this;
    }

    public YHAddLinkDialog setOkClick(final InsertLinckLiseter listener){
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!YHStringUtils.isUrl(inputText.getText().toString())){
                    Toast.makeText(context,"请输入正确的链接",Toast.LENGTH_LONG).show();
                    return;
                }
                if(inputName.getText().length() < 0){
                    Toast.makeText(context,"链接文字不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                listener.insert(inputText.getText().toString(),inputName.getText().toString());
                dismiss();
            }
        });
        return this;
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
        }
    }

    public interface InsertLinckLiseter{
        public void insert(String url, String text);
    }
}