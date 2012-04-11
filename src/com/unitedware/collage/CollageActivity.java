package com.unitedware.collage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class CollageActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	AlertDialog uploadChoice;
	Button choosePictures, uploadPictures;
	Intent aboutUs, photoSelection, startPhotoUpload;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Starts pretty much everything...
		initialize();
	}

	// Puts together the main XML items and the CollageActivity and other items
	// needed
	public void initialize() {

		// Connects the Button declared above to the button created in xml then
		// makes sure that it can do something when clicked
		choosePictures = (Button) findViewById(R.id.bselectPhotos);
		choosePictures.setOnClickListener(this);

		uploadPictures = (Button) findViewById(R.id.buploadPictures);
		uploadPictures.setOnClickListener(this);

		aboutUs = new Intent("com.unitedware.collage.ABOUT");
		photoSelection = new Intent("com.unitedware.collage.CHOOSEPHOTO");
		startPhotoUpload = new Intent("com.unitedware.collage.UPLOADPHOTO");

		// On a side note this needs to be changed to a broadcast intent, this
		// will allow us to grab not only from the camera but also the photo
		// gallery.

		// Create the AlertDialog for the "Choose a Picture(s) button
		uploadChoice = new AlertDialog.Builder(this).create();
		uploadChoice.setTitle("Choose a Picture(s)");
		uploadChoice.setMessage("From which source?");

		uploadChoice.setButton("From Camera",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						startActivity(startPhotoUpload);
					}

				});

		uploadChoice.setButton2("From Gallery",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.bselectPhotos:
			break;

		case R.id.buploadPictures:
			// Starts the AlertDialog created in initialize
			uploadChoice.show();
			break;
		}
	}

	// Creates a sub-menu inflation
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// This contains the sub-menu items and what they do.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.About:
			startActivity(aboutUs);
			break;
		case R.id.Exit:
			finish();
			break;
		}
		return true;
	}
}