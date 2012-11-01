package com.unitedware.collage;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	int mGalleryItemBackground;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return allFiles.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView myImageView = new ImageView(mContext);

		// if there is already a view don't recreate it
		if (convertView != null) {
			myImageView = (ImageView) convertView;
		} else {
			myImageView = new ImageView(mContext);
			myImageView.setLayoutParams(new GridView.LayoutParams(120, 120));
			myImageView.setAdjustViewBounds(false);
			myImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		}

		Bitmap bitmapImage = BitmapFactory.decodeFile(folder + "/"
				+ allFiles[position]);
		BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);
		myImageView.setImageDrawable(drawableImage);

		return myImageView;
	}

	String collageFolder = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Collage/";

	// Add that folder to the image adapter then add give the array all of the
	// files in folder
	File folder = new File(collageFolder);
	String[] allFiles = folder.list();
}
