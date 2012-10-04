package com.unitedware.collage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImportPhotos extends Activity implements OnClickListener {

	boolean isViewHidden = false;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	int startUp = 0;

	Button selectAnother;
	Bundle extras;
	Bitmap userPhoto;
	ImageView mImageView;
	TextView title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoview);

		initialize();

		if (startUp == 0) {
			// Run dialog code on startup
			isViewHidden = true;
			choosePhotoOption();
			startUp++;
		}
	}

	public void initialize() {
		mImageView = (ImageView) findViewById(R.id.ivPhoto);
		selectAnother = (Button) findViewById(R.id.bChoosePhoto);
		selectAnother.setOnClickListener(this);
		selectAnother.setVisibility(View.GONE);
		title = (TextView) findViewById(R.id.tvUploadTitle);
		title.setVisibility(View.GONE);
	}

	public void savePhotoToSD(Bitmap photo) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Collage" + File.separator + timeStamp + ".jpg");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// write the bytes in file
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(bytes.toByteArray());
	}

	public void choosePhotoOption() {

		final String[] items = new String[] { "Take from camera",
				"Select from gallery" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				// Get picture from camera
				if (item == 0) {
					Intent intent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				}

				else { // pick from file
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent,
							"Complete action using"), PICK_FROM_FILE);
				}
			}
		});

		final AlertDialog dialog = builder.create();
		dialog.show();

	}

	public boolean showView(boolean isHidden) {

		// Reveals layout if necessary
		if (isHidden == true) {
			selectAnother.setVisibility(View.VISIBLE);
			title.setVisibility(View.VISIBLE);
			return isHidden = false;
		}
		return isHidden;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case PICK_FROM_CAMERA:
				userPhoto = (Bitmap) data.getExtras().get("data");
				mImageView.setImageBitmap(userPhoto);
				
				// Try and save the picture to the sd card
				try {
					savePhotoToSD(userPhoto);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				showView(isViewHidden);
				break;

			case PICK_FROM_FILE:

				// This gets the path data from the data given from the Uri and
				// then grabs the image and sets it to the Bitmap variable
				Uri targetUri = data.getData();

				try {
					userPhoto = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(targetUri));
					mImageView.setImageBitmap(userPhoto);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				showView(isViewHidden);
				break;
			}
		}
		if (resultCode == RESULT_CANCELED) {
			showView(isViewHidden);
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bChoosePhoto:
			choosePhotoOption();
			break;
		}
	}
}