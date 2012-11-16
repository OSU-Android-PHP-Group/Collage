package com.unitedware.collage;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class SelectCollagePhotos extends Activity {
	public static final String TAG = "SelectCollagePhotos";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		final GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		final Collage collage = new Collage();

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				File image = (File) gridview.getAdapter().getItem(position);
				collage.addImage(image);
				Log.i(TAG, "Added image to collage (\"" + image.getName() + "\")");
				Toast.makeText(SelectCollagePhotos.this, "" + "File has been chosen",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
