package com.unitedware.collage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UploadPhoto extends Activity {

	int cameraCounter = 0;
	final int cameraData = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (cameraCounter == 0) {
			final int cameraData = 0;
			Intent cameraStart = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraStart, cameraData);
		} else {
			setContentView(R.layout.photoview);
		}
	}
}