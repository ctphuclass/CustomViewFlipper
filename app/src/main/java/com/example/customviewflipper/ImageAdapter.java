package com.example.customviewflipper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public static String[] imageLinks = new String[]
            {       "https://images.unsplash.com/photo-1530878955558-a6c31b9c97db?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",
                    "https://images.unsplash.com/photo-1503696967350-ad1874122058?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80",
                    "https://images.unsplash.com/photo-1540660235365-083e8894cec4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=606&q=80"};

    public ImageAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return imageLinks.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imgView;
        Bitmap bitmap;
        if(convertView == null){
            imgView = new ImageView(mContext);

            imgView.setLayoutParams(new AbsListView.LayoutParams(200, 200));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setPadding(10, 10, 10, 10);
        }else{
            imgView = (ImageView) convertView;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getbmpfromURL(imageLinks[position]);
                imgView.post(new Runnable() {
                    @Override
                    public void run() {
                        imgView.setImageBitmap(bitmap);
                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return imgView;
    }

    public static Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            String i = e.getMessage();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
