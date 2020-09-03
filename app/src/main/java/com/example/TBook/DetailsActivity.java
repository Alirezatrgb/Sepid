package com.example.TBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvInformation, tvWriter;
    ImageView imageView;
    RatingBar ratingBar;
    Button submit;
    String ImageId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();


        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        tvName.setText(intent.getStringExtra("name"));
        tvWriter.setText(intent.getStringExtra("writer"));
        tvPrice.setText(String.valueOf(intent.getLongExtra("price", 0)));
        tvInformation.setText(intent.getStringExtra("information"));
        ImageId = intent.getStringExtra("image");
        Picasso.get().load(ImageId).into(imageView);

    }


    private void init()
    {
        getSupportActionBar().setTitle("توضیحات کتاب");
        tvName = findViewById(R.id.dName);
        tvPrice = findViewById(R.id.dPrice);
        tvWriter = findViewById(R.id.dWriter);
        tvInformation = findViewById(R.id.info);
        imageView = findViewById(R.id.dImg);
        submit = findViewById(R.id.submit);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==5)
                {
                    Toast.makeText(DetailsActivity.this, "از رضایت شما شادمانیم", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareBook:
                String str = "سلام! لطفا کتاب " + tvName.getText() + " اثر " + tvWriter.getText() + " را در تی بوک مشاهده بفرمایید.";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
        }

        switch (item.getItemId()) {
            case R.id.back:
            finish();
            break;
        }
        return super.onOptionsItemSelected(item);


    }

    public void onClickAdd(View view) {
        Toast.makeText(this, tvName.getText() + " به سبد خرید شما اضافه شد", Toast.LENGTH_LONG).show();
    }

    public void onClickSubmit(View view) {
        double rating=ratingBar.getRating();
        Toast.makeText(getApplicationContext(), String.valueOf(rating), Toast.LENGTH_LONG).show();
    }
}