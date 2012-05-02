package com.unitedware.collage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class Help extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.help);
        Spinner topicSelector = (Spinner) findViewById(R.id.spinnerTopicSelect);

        topicSelector
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {
                        
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

}
