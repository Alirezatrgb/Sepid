package com.example.TBook.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.TBook.R;
import com.example.TBook.entity.Products;

import java.util.ArrayList;

public class ProductsAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Products> arrayList;
    private ArrayList<Products> temp;

    public ProductsAdapter(Context context, ArrayList<Products> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.temp= arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.gridview_items , null);
        }

        Products Products=this.arrayList.get(position);

        TextView textViewPrice=convertView.findViewById(R.id.tvPrice);
        TextView textViewName=convertView.findViewById(R.id.tvName);
        ImageView imageView=convertView.findViewById(R.id.img);
        Picasso.get().load(Products.getImage()).into(imageView);
        textViewPrice.setText(String.valueOf(Products.getPrice()));
        textViewName.setText(Products.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new MyFilter();
    }

    private class MyFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults=new FilterResults();

            ArrayList<Products> findSearch=new ArrayList<>();
            for (Products item: temp
            ) {
                if (item.getName().contains(charSequence))
                {
                    findSearch.add(item);
                }
            }


            filterResults.values = findSearch;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            ArrayList<Products> result= (ArrayList<Products>) filterResults.values;

                arrayList = result;

            }
        }
    }

