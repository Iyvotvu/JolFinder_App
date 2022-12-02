package com.example.jolfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class filter_search extends AppCompatActivity {

    SeekBar seekBar;
    TextView range;
    Button confirm;
    int Range_Dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);

        seekBar = (SeekBar) findViewById(R.id.range);
        range = (TextView) findViewById(R.id.range_tx);

        confirm = (Button) findViewById(R.id.f_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String range = String.valueOf(Range_Dis);
                intent.putExtra("Range", range);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                range.setText(String.valueOf(i) + " Km");
                Range_Dis = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}