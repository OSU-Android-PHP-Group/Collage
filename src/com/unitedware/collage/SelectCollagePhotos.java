package com.unitedware.collage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.unitedware.collage.exceptions.EmptyCollageException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
        final Button generateButton = (Button) findViewById(R.id.generate);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                File image = (File) gridview.getAdapter().getItem(position);
                collage.addImage(image);
                Log.i(TAG, "Added image to collage (\"" + image.getName()
                        + "\")");
                Toast.makeText(SelectCollagePhotos.this,
                        "" + "File has been chosen", Toast.LENGTH_SHORT).show();
            }
        });

        generateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String feedback = "Generating collage...";
                Log.i(TAG, feedback);
                Toast.makeText(SelectCollagePhotos.this, feedback,
                        Toast.LENGTH_SHORT).show();
                try {
                    File generatedCollage = collage.generate();
                    Log.i(TAG,
                            "Generated collage (\""
                                    + generatedCollage.getPath() + "\").");
                } catch (EmptyCollageException e) {
                    this.handleError(e.getMessage());
                } catch (FileNotFoundException e) {
                    this.handleError("Generated file could not be found.", e);
                } catch (IOException e) {
                    this.handleError("Failed to generate a collage.", e);
                }
            }

            private void handleError(String message) {
                Toast.makeText(SelectCollagePhotos.this, message,
                        Toast.LENGTH_SHORT).show();
            }

            private void handleError(String message, Throwable e) {
                this.handleError(message);
                Log.e(TAG,
                        message + ": " + e.getMessage() + "\n"
                                + Log.getStackTraceString(e));
            }
        });
    }
}
