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

	private static String collageDirectory;
	private String[] allFiles;

	static {
		ImageAdapter.collageDirectory = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Collage/";
	}

	private Context mContext;
	int mGalleryItemBackground;

	public ImageAdapter(Context c) {
		mContext = c;
		this.allFiles = new File(collageDirectory).list();
	}

	public int getCount() {
		return allFiles.length;
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

		Bitmap bitmapImage = BitmapFactory.decodeFile(
				new File(collageDirectory, allFiles[position]).getPath());

		BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);
		myImageView.setImageDrawable(drawableImage);

		return myImageView;
	}

	public static String getCollageDirectory() {
		return ImageAdapter.collageDirectory;
	}

	@Override
	public Object getItem(int position) {
		if (position < this.allFiles.length) {
			return new File(collageDirectory, this.allFiles[position]);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
