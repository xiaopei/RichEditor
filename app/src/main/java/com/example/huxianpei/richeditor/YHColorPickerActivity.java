package com.example.huxianpei.richeditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.example.huxianpei.richeditor.databinding.ActivityColorPickerBinding;


public class YHColorPickerActivity extends Activity {
    private ActivityColorPickerBinding binding;
    private Context context;
    private String[] colors = new String[]{
            "#000000", "#333333", "#666666", "#999999", "#CCCCCC",
            "#FFFFFF", "#BC0505", "#FF0300", "#FF7800", "#FFEB00",
            "#78FF00", "#00FFE5", "#0001FE", "#A201FF", "#FF00DF"
    };
    private int[] drawables = new int[]{
            R.drawable.shape_color_picker1, R.drawable.shape_color_picker2, R.drawable.shape_color_picker3,
            R.drawable.shape_color_picker4, R.drawable.shape_color_picker5, R.drawable.shape_color_picker6,
            R.drawable.shape_color_picker7, R.drawable.shape_color_picker8, R.drawable.shape_color_picker9,
            R.drawable.shape_color_picker10, R.drawable.shape_color_picker11, R.drawable.shape_color_picker12,
            R.drawable.shape_color_picker13, R.drawable.shape_color_picker14, R.drawable.shape_color_picker15,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_color_picker);
        context = this;
        binding.actionbarTitle.setText("选择颜色");
        binding.actionbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.colorPicker.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return colors.length;
            }

            @Override
            public Object getItem(int position) {
                return colors[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = new View(context);
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams((int) (50 * context.getResources().getDisplayMetrics().density), (int) (50 * context.getResources().getDisplayMetrics().density));
                view.setLayoutParams(layoutParams);
                view.setBackgroundResource(drawables[position]);
                return view;
            }
        });
        binding.colorPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(YHRichContentActivity.INPUT_TEXT, colors[position]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
