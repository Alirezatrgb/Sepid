package com.example.TBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.TBook.adapter.ProductsAdapter;
import com.example.TBook.entity.Products;
import com.example.TBook.webservice.ProductsApi;

import android.widget.GridView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//سرچ هر کلمه ای که شامل این حروف باشد

public class SearchActivity extends AppCompatActivity {

    EditText etSearch;
    ProductsAdapter adapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(ProductsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductsApi api= retrofit.create(ProductsApi.class);
        Call<ArrayList<Products>> request= api.getResult();

        request.enqueue(new Callback<ArrayList<Products>>() {
            @Override
            public void onResponse(Call<ArrayList<Products>> call, Response<ArrayList<Products>> response) {
                ArrayList<Products> arrayList=response.body();
                adapter=new ProductsAdapter(SearchActivity.this , arrayList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Products>> call, Throwable t) {

            }
        });

    }


    private void init()
    {
        getSupportActionBar().setTitle("جستجوی کتاب");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView=findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Products products = (Products) adapterView.getItemAtPosition(position);
                String str= "id = " + products.getId() + " name " + products.getName() + "  price " + products.getPrice()+ "  writer " + products.getWriter()+ "  information " + products.getInformation();


                Intent intent=new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("name",products.getName());
                intent.putExtra("price",products.getPrice());
                intent.putExtra("writer",products.getWriter());
                intent.putExtra("information",products.getInformation());
                intent.putExtra("image",products.getImage());

                startActivity(intent);
            }
        });

        etSearch=findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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



    AlertDialog alertDialog;
    class MyWifiBroadCast extends BroadcastReceiver

    {

        public static final String ACTION_NAME = "android.net.wifi.STATE_CHANGE";

        public MyWifiBroadCast() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        View alertDialog_view= LayoutInflater.from(SearchActivity.this).inflate(R.layout.alertdialog_layout,null);
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
