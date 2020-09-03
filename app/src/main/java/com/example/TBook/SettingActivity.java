package com.example.TBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class SettingActivity extends AppCompatActivity {


    SeekBar seek;
    EditText ed1;
    Button b;
    private final String TEXT_VALUE_KEY="textvalue";
    private final String FONT_SIZE_KEY="fontsize";
    final String MyPref="preference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("تنظیمات");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        seek =findViewById(R.id.seekBar1);
        ed1=findViewById(R.id.editText1);
        b=findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences pref=getSharedPreferences(MyPref,MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putFloat(FONT_SIZE_KEY,ed1.getTextSize());
                editor.putString(TEXT_VALUE_KEY, ed1.getText().toString());
                editor.commit();



            }

        });

        SharedPreferences prefs=getSharedPreferences(MyPref, MODE_PRIVATE);

        final float fontsize=prefs.getFloat(FONT_SIZE_KEY, 12);

        seek.setProgress((int) fontsize);

        ed1.setText(prefs.getString(TEXT_VALUE_KEY, ""));

        ed1.setTextSize(seek.getProgress());

        seek.setOnSeekBarChangeListener(new  SeekBar.OnSeekBarChangeListener() {


            public void onStopTrackingTouch(SeekBar arg0) {



            }

            public void onStartTrackingTouch(SeekBar arg0) {


            }

            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                ed1.setTextSize(fontsize);
    }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
