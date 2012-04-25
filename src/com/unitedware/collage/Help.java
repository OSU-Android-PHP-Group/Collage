package com.unitedware.collage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

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
                        TextView txtHelpContent = (TextView) findViewById(R.id.txtHelpContent);
                        switch (pos) {
                        case 0:
                            txtHelpContent.setText(R.string.uploadHelp);
                            break;
                        case 1:
                            txtHelpContent.setText(R.string.help2);
                            break;
                        case 2:
                            txtHelpContent.setText(R.string.help3);
                            break;
                        case 3:
                            txtHelpContent.setText(R.string.help4);
                            break;
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

}
