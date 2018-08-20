package com.example.huxianpei.richeditor;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.huxianpei.richeditor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private String innerHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(YHRichContentActivity.INPUT_TEXT, innerHtml);
                Intent intent = new Intent(MainActivity.this, YHRichContentActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        innerHtml = data.getStringExtra(YHRichContentActivity.INPUT_TEXT);
    }
}
