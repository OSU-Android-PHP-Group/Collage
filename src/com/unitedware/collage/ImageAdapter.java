package com.unitedware.collage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    // Member Variables
    private Context mContext;
    private Bitmap[] finalGridImages;

    public ImageAdapter(Context c, Bitmap[] gridImages) {
        mContext = c;
        finalGridImages = gridImages;
    }

    public int getCount() {
        return finalGridImages.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, initialize some
                                   // attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // Sets imageView from Bitmap array if there are actually photos there
        if (finalGridImages[0] == null) {
            imageView.setImageResource(mThumbIdsPlaceHolder[position]);
        } else {
            imageView.setImageBitmap(finalGridImages[position]);
        }
        return imageView;
    }

    String dir = Environment.getExternalStorageDirectory() + "/Collage/";

    // A place holder for right now
    private Integer[] mThumbIdsPlaceHolder = { R.drawable.ic_launcher };
}
