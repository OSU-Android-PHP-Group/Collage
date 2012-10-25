package com.unitedware.collage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class SelectCollagePhotos extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photogallery);

		GridView photoGrid = (GridView) findViewById(R.id.photo_grid);
		photoGrid.setAdapter(new ImageAdapter(this));

		// photoGrid.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View v,
		// int position, long id) {
		// Toast.makeText(SelectCollagePhotos.this, "" + position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });
	}
}