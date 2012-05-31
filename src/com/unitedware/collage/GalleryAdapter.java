package com.unitedware.collage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GalleryAdapter extends BaseAdapter {

    // Member Variables
    Context mContext;
    private Bitmap[] mImageArray;

    public GalleryAdapter(Context context, Bitmap[] imageArrBitmaps) {
        mContext = context;
        mImageArray = imageArrBitmaps;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mImageArray.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(mImageArray[position]);

        // put black borders around the image
        final RelativeLayout borderImg = new RelativeLayout(mContext);
        borderImg.setPadding(20, 20, 20, 20);
        borderImg.setBackgroundColor(0xff000000);// black
        borderImg.addView(imageView);
        return borderImg;
    }

}
