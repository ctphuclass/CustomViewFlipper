package com.example.customviewflipper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class CustomViewFlipper extends Activity implements View.OnTouchListener {

    ViewFlipper viewFlipper;
    float downX = 0;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewflipper_layout);
        onInit();

        Intent intent = getIntent();


        for( int i = 0; i < ImageAdapter.imageLinks.length; i++){
            final ImageView imgView = new ImageView(this);
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = ImageAdapter.getbmpfromURL(ImageAdapter.imageLinks[finalI]);
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
            viewFlipper.addView(imgView);
        }

        viewFlipper.setDisplayedChild(intent.getExtras().getInt("Position",0));
        viewFlipper.setOnTouchListener(this);
    }



    private void onInit(){
        viewFlipper = findViewById(R.id.viewFlipper);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                if(upX < downX){
                    viewFlipper.showNext();
                }else{
                    viewFlipper.showPrevious();
                }
                break;
        }
        return true;
    }
}
