package com.unitedware.collage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;

public class Help extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.help);
        
        final WebView browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        
        Spinner topicSelector = (Spinner) findViewById(R.id.spinnerTopicSelect);

        topicSelector
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {
                        switch (pos) {
                        case 0:
                            browser.loadUrl(getString(R.string.uploadHelp));
                            break;
                        case 1:
                            browser.loadUrl(getString(R.string.help2));
                            break;
                        case 2:
                            browser.loadUrl(getString(R.string.help3));
                            break;
                        case 3:
                            browser.loadUrl(getString(R.string.help4));
                            break;
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

}
