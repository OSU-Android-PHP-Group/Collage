package com.unitedware.collage;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
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

		imageView.setImageBitmap(mThumbIds[position]);
		return imageView;
	}

	/*
	 * Get all the files in the Collage directroy and and cast them into
	 * integers
	 */
	private Bitmap[] getAllFilesInPhotoGallery() {
		String filePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Collage";
		File dir = new File(filePath);
		Bitmap[] mThumbIds = {};

		/*
		 * If there are photos in the directory it will take all of them,
		 * convert them to Bitmap and load them into and array to be returnd to
		 * the ImageAdapter
		 */
		if (dir.isDirectory()) {
			File photos[] = dir.listFiles();
			Log.d("ImageAdapter", "We have found " + photos.length + " files!");
			if (photos.length > 0) {
				Log.d("ImageAdapter", "There are photos in the photo array");
				for (int i = 0; i < photos.length; i++) {
					String tempPhotoPath = photos[i].getAbsolutePath();
					Log.d("ImageAdapter", tempPhotoPath);
					Bitmap tempPhoto = BitmapFactory.decodeFile(tempPhotoPath);
					Log.d("ImageAdapter", "Temp Photo" + tempPhoto.toString());
					mThumbIds[i] = tempPhoto;
					Log.d("ImageAdapter",
							"Inside the Array" + mThumbIds[i].toString());
				}
			}
		}

		return mThumbIds;
	}

	// references to our images
	private Bitmap[] mThumbIds = getAllFilesInPhotoGallery();
}