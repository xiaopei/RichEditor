package com.example.huxianpei.richeditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huxianpei.richeditor.databinding.ActivityRichContentBinding;


public class YHRichContentActivity extends Activity implements YHPermissionUtils.PermissionGrant {
    private ActivityRichContentBinding binding;
    private static final int ADD_PIC_CODE = 100;
    private static final int TXT_COLOR_PIC_CODE = 101;
    private static final int BG_COLOR_PIC_CODE = 102;
    public static final String INPUT_TEXT = "input_text";
    public int ADD_ARTICLE = 3;
    private String imgPath;
    private boolean isBold, isItalic, isSubscript, isSuperscript, isStrikeThrough, isUnderline;
    private boolean isHeading1, isHeading2, isHeading3, isHeading4, isHeading5, isHeading6;
    private boolean isAlignLeft, isAlignCenter, isAlignRight, isBullets, isNumbers;
    private boolean isIn;
    private String title;
    private String initString = "";
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rich_content);
        context = this;
        binding.setPresenter(new Presenter());
        initView();
    }

    private void initView() {
        binding.editor.setEditorHeight(300);
        binding.editor.setEditorFontSize(16);
        binding.editor.setEditorFontColor(Color.BLACK);
        //binding.editor.setEditorBackgroundColor(Color.BLUE);
        //binding.editor.setBackgroundColor(Color.BLUE);
        binding.editor.setPadding(10, 10, 10, 10);
        //binding.editor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //binding.editor.setInputEnabled(false);
//    String initString = "<div class=\"test\"></div>\n" +
//            "<!-- This is an HTML comment -->\n" +
//            "<p>This is a test of the <strong>ZSSRichTextEditor</strong> by <a title=\"Zed Said\" href=\" \">Zed Said Studio</a >\n" +
//            "</p >\n" +
//            "<p>\n" +
//            "    <br />\n" +
//            "</p >\n" +
//            "<p>Hjhjvhjhjv</p >\n" +
//            "<p><a href=\"https://www.baidu.com\">baidu</a >\n" +
//            "    <br />\n" +
//            "</p >";
        if(getIntent() != null && getIntent().getExtras() != null){
            initString = getIntent().getExtras().getString(INPUT_TEXT, "");
            if (!YHStringUtils.isEmpty(initString)) {
                binding.editor.setHtml(initString);
            }
        }
        binding.actionbarTitle.setText("富文本编辑器");
        binding.actionbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onback();
            }
        });
        binding.actionbarActionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
//        mPreview = (TextView) findViewById(R.id.preview);
//        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                YHBaseFragActivity.launchFragment(YHRichContentActivity.this, YHWebViewFragment.class.getName(),
//                        new YHWebviewBundleBuilder().createUrl(binding.editor.getHtml())
//                                .createShowTitle(true)
//                                .createFragmentType(YHWebViewFragment.TYPE_DATA)
//                                .createFragmentTypeName("专家简介")
//                                .build());
//            }
//        });
//        binding.editor.setOnTextChangeListener(new YHRichEditor.OnTextChangeListener() {
//            @Override
//            public void onTextChange(String text) {
//                mPreview.setText(text);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        onback();
    }

    private void onback() {
        if (!(YHStringUtils.isEmpty(initString) && YHStringUtils.isEmpty(binding.editor.getHtml()))
                && !initString.equals(binding.editor.getHtml())) {
            new YHDefaultYNDialog(context).setText("您输入的内容发生了变化，是否保存修改")
                    .setOkButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            save();
                        }
                    }).setNoButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).show();
        } else {
            finish();
        }
    }

    private void save() {
        if (YHStringUtils.isEmpty(binding.editor.getHtml())) {
            new YHDefaultYNDialog(context).setEnsure("离开")
                    .setText("您还未输入任何内容，确定要离开吗 ？")
                    .setOkButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(INPUT_TEXT, "");
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }).show();
            return;
        }
        if (binding.editor.getHtml().length() > 20000) {
            Toast.makeText(context,"输入超限 ,请重新编辑",Toast.LENGTH_LONG).show();
            return;
        }
        Log.e("NetWorkBas", binding.editor.getHtml());
        Intent intent = new Intent();
        intent.putExtra(INPUT_TEXT, binding.editor.getHtml());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openKeybord(binding.editor, this);
        if (!isIn) {
            isIn = true;
            binding.editor.focusEditor();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            binding.editor.onResume(); // 恢复视频播放状态
        }
    }

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(View mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) ((Activity) mContext).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editor.getWindowToken(), 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            binding.editor.onPause(); // 暂停网页中正在播放的视频
        }
    }

    private void uploadPic(String picPath) {
        if (YHStringUtils.isEmpty(picPath)) return;
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        takePic();
    }

    @Override
    public void onPermissionToSetting(boolean toSetting) {
    }

    public class Presenter {

        public void undo(View v) {
            binding.editor.undo();
        }

        public void redo(View v) {
            binding.editor.redo();
        }

        public void setBold(View v) {
            binding.editor.setBold();
            if (isBold) {
                isBold = false;
                binding.actionBold.setImageResource(R.mipmap.bold_down);
            } else {
                isBold = true;
                binding.actionBold.setImageResource(R.mipmap.bold);
            }
        }

        public void setItalic(View v) {
            binding.editor.setItalic();
            if (isItalic) {
                isItalic = false;
                binding.actionItalic.setImageResource(R.mipmap.italic_down);
            } else {
                isItalic = true;
                binding.actionItalic.setImageResource(R.mipmap.italic);
            }
        }

        public void setSubscript(View v) {
            binding.editor.setSubscript();
            if (isSubscript) {
                isSubscript = false;
                binding.actionSubscript.setImageResource(R.mipmap.subscript_down);
            } else {
                isSubscript = true;
                isSuperscript = false;
                binding.actionSubscript.setImageResource(R.mipmap.subscript);
                binding.actionSuperscript.setImageResource(R.mipmap.superscript_down);
            }
        }

        public void setSuperscript(View v) {
            binding.editor.setSuperscript();
            if (isSuperscript) {
                isSuperscript = false;
                binding.actionSuperscript.setImageResource(R.mipmap.superscript_down);
            } else {
                isSuperscript = true;
                isSubscript = false;
                binding.actionSuperscript.setImageResource(R.mipmap.superscript);
                binding.actionSubscript.setImageResource(R.mipmap.subscript_down);
            }
        }

        public void setStrikeThrough(View v) {
            binding.editor.setStrikeThrough();
            if (isStrikeThrough) {
                isStrikeThrough = false;
                binding.actionStrikethrough.setImageResource(R.mipmap.strikethrough_down);
            } else {
                isStrikeThrough = true;
                binding.actionStrikethrough.setImageResource(R.mipmap.strikethrough);
            }
        }

        public void setUnderline(View v) {
            binding.editor.setUnderline();
            if (isUnderline) {
                isUnderline = false;
                binding.actionUnderline.setImageResource(R.mipmap.underline_down);
            } else {
                isUnderline = true;
                binding.actionUnderline.setImageResource(R.mipmap.underline);
            }
        }

        public void setHeading1(View v) {
            if (isHeading1) {
                isHeading1 = false;
                binding.editor.setEditorFontSize(16);
                binding.editor.removeHeading();
                binding.actionHeading1.setImageResource(R.mipmap.h1_down);
            } else {
                setNormalHeading();
                isHeading1 = true;

                binding.editor.setHeading(1);
                binding.actionHeading1.setImageResource(R.mipmap.h1);
            }
        }

        public void setHeading2(View v) {
            if (isHeading2) {
                isHeading2 = false;
                binding.editor.removeHeading();
                binding.actionHeading2.setImageResource(R.mipmap.h2_down);
            } else {
                setNormalHeading();
                isHeading2 = true;

                binding.editor.setHeading(2);
                binding.actionHeading2.setImageResource(R.mipmap.h2);
            }
        }

        public void setHeading3(View v) {
            if (isHeading3) {
                isHeading3 = false;
                binding.editor.removeHeading();
                binding.actionHeading3.setImageResource(R.mipmap.h3_down);
            } else {
                setNormalHeading();
                isHeading3 = true;

                binding.editor.setHeading(3);
                binding.actionHeading3.setImageResource(R.mipmap.h3);
            }
        }

        public void setHeading4(View v) {
            if (isHeading4) {
                isHeading4 = false;
                binding.editor.removeHeading();
                binding.actionHeading4.setImageResource(R.mipmap.h4_down);
            } else {
                setNormalHeading();
                isHeading4 = true;

                binding.editor.setHeading(4);
                binding.actionHeading4.setImageResource(R.mipmap.h4);
            }
        }

        public void setHeading5(View v) {
            if (isHeading5) {
                isHeading5 = false;
                binding.editor.removeHeading();
                binding.actionHeading5.setImageResource(R.mipmap.h5_down);
            } else {
                setNormalHeading();
                isHeading5 = true;

                binding.editor.setHeading(5);
                binding.actionHeading5.setImageResource(R.mipmap.h5);
            }
        }

        public void setHeading6(View v) {
            if (isHeading6) {
                isHeading6 = false;
                binding.editor.removeHeading();
                binding.actionHeading6.setImageResource(R.mipmap.h6_down);
            } else {
                setNormalHeading();
                isHeading6 = true;

                binding.editor.setHeading(6);
                binding.actionHeading6.setImageResource(R.mipmap.h6);
            }
        }

        public void setTextColor(View v) {
            startActivityForResult(new Intent(YHRichContentActivity.this, YHColorPickerActivity.class), TXT_COLOR_PIC_CODE);
        }

        public void setTextBackgroundColor(View v) {
            startActivityForResult(new Intent(YHRichContentActivity.this, YHColorPickerActivity.class), BG_COLOR_PIC_CODE);
        }

        public void setAlignLeft(View v) {
            if (isAlignLeft) {
                isAlignLeft = false;
                binding.editor.setAlignFull();
                binding.actionAlignLeft.setImageResource(R.mipmap.justify_left_down);
            } else {
                setNormalAlign();
                isAlignLeft = true;

                binding.editor.setAlignLeft();
                binding.actionAlignLeft.setImageResource(R.mipmap.justify_left);
            }
        }

        public void setAlignCenter(View v) {
            if (isAlignCenter) {
                isAlignCenter = false;
                binding.editor.setAlignFull();
                binding.actionAlignCenter.setImageResource(R.mipmap.justify_center_down);
            } else {
                setNormalAlign();
                isAlignCenter = true;

                binding.editor.setAlignCenter();
                binding.actionAlignCenter.setImageResource(R.mipmap.justify_center);
            }
        }

        public void setAlignRight(View v) {
            if (isAlignRight) {
                isAlignRight = false;
                binding.editor.setAlignFull();
                binding.actionAlignRight.setImageResource(R.mipmap.justify_right_down);
            } else {
                setNormalAlign();
                isAlignRight = true;
                binding.editor.setAlignRight();
                binding.editor.focusEditor();
                binding.actionAlignRight.setImageResource(R.mipmap.justify_right);
            }
        }

        public void setBullets(View v) {
            binding.editor.setBullets();
            if (isBullets) {
                isBullets = false;
                binding.actionInsertBullets.setImageResource(R.mipmap.bullets_down);
            } else {
                isBullets = true;
                isNumbers = false;
                binding.actionInsertNumbers.setImageResource(R.mipmap.numbers_down);
                binding.actionInsertBullets.setImageResource(R.mipmap.bullets);
            }
        }

        public void setNumbers(View v) {
            binding.editor.setNumbers();
            if (isNumbers) {
                isNumbers = false;
                binding.actionInsertNumbers.setImageResource(R.mipmap.numbers_down);
            } else {
                isNumbers = true;
                isBullets = false;
                binding.actionInsertNumbers.setImageResource(R.mipmap.numbers);
                binding.actionInsertBullets.setImageResource(R.mipmap.bullets_down);
            }
        }

        public void insertImage(View v) {
            //选择照片上传
//            String fileName = System.currentTimeMillis() + ".jpg";
//            imgPath = YHCacheFileUtils.getDiskCacheDir(YHApp.getApp()) + YHConstant.PIC_CACHE + fileName;
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setType("image/*");
//            startActivityForResult(intent, ADD_PIC_CODE);
            binding.editor.insertImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534154014176&di=60e79906584772d43e82d3d28229e0e4&imgtype=0&src=http%3A%2F%2Fp14.go007.com%2F2014_11_17_22%2F7bd664c5db82224f_1.jpg",
                    "仓鼠图片");
        }

        public void insertVideo(View v) {//选择相册视频
            //选择相册视频
            binding.editor.insertVideo("https://yhyd-resources.oss-cn-beijing.aliyuncs.com/video/308af4a84912c0c34c8c1f7cf991730d@640x480.mp4",
                    "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=3caa4dab566034a83defb0c2a37a2321/5fdf8db1cb134954acac2cce5c4e9258d0094aee.jpg");
        }

        public void shootVideo(View v) {//拍摄视频
            // 拍摄视频
            binding.editor.insertVideo("https://yhyd-resources.oss-cn-beijing.aliyuncs.com/video/308af4a84912c0c34c8c1f7cf991730d@640x480.mp4",
                    "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=3caa4dab566034a83defb0c2a37a2321/5fdf8db1cb134954acac2cce5c4e9258d0094aee.jpg");
        }

        public void takePhoto(View v) {
            binding.editor.insertImage("http://imgsrc.baidu.com/image/c0%3Dpixel_huitu%2C0%2C0%2C294%2C40/sign=1db0b375952f07084b082240805cddf5/b3fb43166d224f4abac2d22902f790529822d1b5.jpg",
                    "萨摩耶犬图片");
            //检查权限拍照上传
//            YHPermissionUtils.requestPermission(context, YHPermissionUtils.CODE_CAMERA, YHRichContentActivity.this);
        }

        public void insertLink(View v) {
            new YHAddLinkDialog(YHRichContentActivity.this).setOkClick(new YHAddLinkDialog.InsertLinckLiseter() {
                @Override
                public void insert(String url, String text) {
                    binding.editor.insertLink(url, text);
                }
            }).show();
        }

        public void hideSoftInput(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.editor.getWindowToken(), 0);
        }
    }

    private void setNormalAlign() {
        isAlignLeft = false;
        isAlignCenter = false;
        isAlignRight = false;

        binding.actionAlignLeft.setImageResource(R.mipmap.justify_left_down);
        binding.actionAlignCenter.setImageResource(R.mipmap.justify_center_down);
        binding.actionAlignRight.setImageResource(R.mipmap.justify_right_down);
    }

    private void setNormalHeading() {
        isHeading2 = false;
        isHeading1 = false;
        isHeading3 = false;
        isHeading4 = false;
        isHeading5 = false;
        isHeading6 = false;

        binding.actionHeading1.setImageResource(R.mipmap.h1_down);
        binding.actionHeading2.setImageResource(R.mipmap.h2_down);
        binding.actionHeading3.setImageResource(R.mipmap.h3_down);
        binding.actionHeading4.setImageResource(R.mipmap.h4_down);
        binding.actionHeading5.setImageResource(R.mipmap.h5_down);
        binding.actionHeading6.setImageResource(R.mipmap.h6_down);
    }

    private void takePic() {
      binding.editor.insertImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534154014176&di=60e79906584772d43e82d3d28229e0e4&imgtype=0&src=http%3A%2F%2Fp14.go007.com%2F2014_11_17_22%2F7bd664c5db82224f_1.jpg",
            "仓鼠图片");
        //拍照上传
//        String fileName = System.currentTimeMillis() + ".jpg";
//        imgPath = YHCacheFileUtils.getDiskCacheDir(YHApp.getApp()) + YHConstant.PIC_CACHE + fileName;
//        //拍照
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (!YHStringUtils.isEmpty(fileName)) {
//            File outDir = new File(YHCacheFileUtils.getDiskCacheDir(YHApp.getApp()) + YHConstant.PIC_CACHE);
//            if (!outDir.exists()) {
//                outDir.mkdirs();
//            }
//            File outFile = new File(outDir, fileName);
//            Uri uri = Uri.fromFile(outFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//        startActivityForResult(intent, ADD_PIC_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == YHPermissionUtils.CODE_CAMERA) {
                takePic();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_PIC_CODE:
//                    String picPath = "";
//                    if (data == null) {
//                        picPath = imgPath;
//                    } else {
//                        picPath = YHImageUtils.getPicPathFromData(data);
//                        if (!YHStringUtils.isEmpty(picPath)) {
//                            imgPath = picPath;
//                        }
//                    }
//                    uploadPic(picPath);
                    break;
                case TXT_COLOR_PIC_CODE:
                    String textColor = data.getStringExtra(INPUT_TEXT);
                    binding.editor.setTextColor(Color.parseColor(textColor));
                    break;
                case BG_COLOR_PIC_CODE:
                    String textBg = data.getStringExtra(INPUT_TEXT);
                    binding.editor.setTextBackgroundColor(Color.parseColor(textBg));
                    break;
            }
        }
    }
}
