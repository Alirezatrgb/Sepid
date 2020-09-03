package com.example.TBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.TBook.adapter.ProductListViewAdapter;
import com.example.TBook.adapter.ProductsAdapter;
import com.example.TBook.entity.Products;
import com.example.TBook.webservice.ProductsApi;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ListView listView;
    ProductsAdapter adapter;
    MyWifiBroadCast broadCastWifi;
    ProductListViewAdapter adapterListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ProductsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductsApi api = retrofit.create(ProductsApi.class);
        Call<ArrayList<Products>> request = api.getResult();

        request.enqueue(new Callback<ArrayList<Products>>() {
            @Override
            public void onResponse(Call<ArrayList<Products>> call, Response<ArrayList<Products>> response) {
                ArrayList<Products> arrayList = response.body();
                adapter = new ProductsAdapter(MainActivity.this, arrayList);
                adapterListView = new ProductListViewAdapter(MainActivity.this, arrayList);
                gridView.setAdapter(adapter);
                listView.setAdapter(adapterListView);
            }


            @Override
            public void onFailure(Call<ArrayList<Products>> call, Throwable t) {

            }
        });

    }


    private void init()
    {
        gridView = findViewById(R.id.gridView);
        listView = findViewById(R.id.listView);
        ImageSlider imageSlider = findViewById(R.id.slider);

        getSupportActionBar().setTitle("TBook");

        broadCastWifi = new MyWifiBroadCast();

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://www.sooremehr.ir/assets/images/slider/file5dca73f1aaf4a.png", null));
        slideModels.add(new SlideModel("https://www.sooremehr.ir/assets/images/slider/file5ecd9ff861a49.png", null));
        slideModels.add(new SlideModel("https://www.sooremehr.ir/assets/images/slider/file5ecd9ff861a31.png", null));
        slideModels.add(new SlideModel("https://www.sooremehr.ir/assets/images/slider/file5ecd9f49258b8.png", null));
        imageSlider.setImageList(slideModels, true);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Products products = (Products) adapterView.getItemAtPosition(position);
                String str = "id = " + products.getId() + " name " + products.getName() + "  price " + products.getPrice() + "  writer " + products.getWriter() + "  information " + products.getInformation();


                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", products.getName());
                intent.putExtra("price", products.getPrice());
                intent.putExtra("writer", products.getWriter());
                intent.putExtra("information", products.getInformation());
                intent.putExtra("image", products.getImage());

                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Products products = (Products) adapterView.getItemAtPosition(position);
                String str = "id = " + products.getId() + " name " + products.getName() + "  price " + products.getPrice() + "  writer " + products.getWriter() + "  information " + products.getInformation();


                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", products.getName());
                intent.putExtra("price", products.getPrice());
                intent.putExtra("writer", products.getWriter());
                intent.putExtra("information", products.getInformation());
                intent.putExtra("image", products.getImage());

                startActivity(intent);

            }
        });


        setGridView();



    }


    private void setListView() {
        listView.setAdapter(adapterListView);
    }

    private void setGridView() {
        gridView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem item1 = menu.findItem(R.id.gridViewShow);
        MenuItem item2 = menu.findItem(R.id.listViewShow);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
        }
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
        }
        switch (item.getItemId()) {
            case R.id.listViewShow:
                setListView();
                gridView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

        }
        switch (item.getItemId()) {
            case R.id.gridViewShow:
                setListView();
                listView.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
        }
        switch (item.getItemId()) {
            case R.id.Search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadCastWifi, new IntentFilter(MyWifiBroadCast.ACTION_NAME));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadCastWifi);
    }

    AlertDialog alertDialog;


    class MyWifiBroadCast extends BroadcastReceiver {

        public static final String ACTION_NAME = "android.net.wifi.STATE_CHANGE";

        public MyWifiBroadCast() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View alertDialog_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alertdialog_layout, null);
            builder.setView(alertDialog_view);
            builder.setCancelable(false);
            alertDialog = builder.create();

        }

        @Override
        public void onReceive(Context context, Intent intent) {

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Service.WIFI_SERVICE);

            if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
                alertDialog.show();
            } else {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }

            }
        }


    }
}













