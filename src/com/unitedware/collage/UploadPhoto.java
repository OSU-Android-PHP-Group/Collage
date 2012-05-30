package com.unitedware.collage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadPhoto extends Activity implements OnClickListener {

    private Uri mImageCaptureUri, outputFileUri;
    private Cursor cursor;
    private int columnIndex;
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
        GridView gridview = (GridView) findViewById(R.id.sdcard);

        initialize();

        if (startUp == 0) {
            // Run dialog code on startup
            isViewHidden = true;
            choosePhotoOption();
            startUp++;
            gridview.setAdapter(new ImageAdapter(this));
        }
    }

    public void initialize() {
        selectAnother = (Button) findViewById(R.id.bChoosePhoto);
        selectAnother.setOnClickListener(this);
        // selectAnother.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.tvUploadTitle);
        // title.setVisibility(View.GONE);

        /*
         * Create the directory if needed for the gridView This is setup to also
         * test to make sure if it was created, I needed something visual right
         * now, this won't go in final development
         */

        File collageDirectory = new File(
                Environment.getExternalStorageDirectory() + "/Collage");
        boolean success = false;
        if (!collageDirectory.exists()) {
            success = collageDirectory.mkdir();
        }
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
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    .getPath());
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

    public void CopyandMoveFile(File sourceFile, String destination)
            throws IOException {
        FileChannel in = null;
        FileChannel out = null;

        try {
            in = new FileInputStream(sourceFile).getChannel();
            File outFile = new File(destination, sourceFile.getName());
            out = new FileOutputStream(outFile).getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public String getRealPathFromUri(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent) This onActivity result needs to changed from just
     * setting one picture, to multiple pictures, in order to do this you need
     * to save these to a file directory, I have already set up the permissions.
     * This will allow me to use the ImageAdapter class grab all the photos from
     * the directory and display them into the gridview
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
            case PICK_FROM_CAMERA:

                // Get the photo and store it to the sd card along with making a
                // thumbnail
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                MediaStore.Images.Media.insertImage(getContentResolver(), bm,
                        null, null);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                /*
                 * The user should be able to save the photo to their own
                 * personal gallery and then it should also be located in the
                 * collage directory
                 */

                // Grab the newest photo from the Camera directory aka what pic
                // the user took
                File[] images = new File(
                        Environment.getExternalStorageDirectory()
                                + File.separator + "DCIM/Camera").listFiles();
                File latestSavedImage = images[0];
                for (int i = 1; i < images.length; ++i) {
                    if (images[i].lastModified() > latestSavedImage
                            .lastModified()) {
                        latestSavedImage = images[i];
                    }
                }

                // Then move it over to the Collage Dir
                String collageDirCamera = (Environment
                        .getExternalStorageDirectory() + "/Collage/");
                try {
                    CopyandMoveFile(latestSavedImage, collageDirCamera);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                showView(isViewHidden);
                break;

            /* This has officially been proven to work */
            case PICK_FROM_FILE:

                Uri targetUri = data.getData();
                String photoDir = getRealPathFromUri(targetUri);
                String collageDir = (Environment.getExternalStorageDirectory() + "/Collage/");
                String parsedPhotoPath = photoDir.substring(11,
                        photoDir.length());
                File galleryPhoto = new File(
                        Environment.getExternalStorageDirectory()
                                + parsedPhotoPath);
                try {
                    CopyandMoveFile(galleryPhoto, collageDir);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
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
