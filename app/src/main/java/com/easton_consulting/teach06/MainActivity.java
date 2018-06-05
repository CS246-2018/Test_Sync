package com.easton_consulting.teach06;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Integer> numList = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;
    private ListView list;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         list = findViewById(R.id.numListView);
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numList );
        list.setAdapter(adapter);
        progressBar = findViewById(R.id.progressBar2);
    }



    public void onCreateClick(View view) throws FileNotFoundException {
        NumberFileWriter nfw = new NumberFileWriter();
        nfw.execute();

    }



    public void onLoadClick(View view) throws FileNotFoundException {
       NumberFileReader nfr = new NumberFileReader();
       nfr.execute();
    }

    public void onClearClick(View view) {
        progressBar.setProgress(0);
        adapter.clear();

    }

   private class NumberFileWriter extends AsyncTask<String, Integer, Boolean> {

       protected void onPreExecute() {
           progressBar.setProgress(0);
       }

       protected void onPostExecute(Boolean result) {
           progressBar.setProgress(0);
       }
        protected Boolean doInBackground(String... params) {
            try {
                FileOutputStream fos = openFileOutput(getString(R.string.com_easton_consulting_FILENAME), Context.MODE_PRIVATE);
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos));
                for (int i = 1; i <= 10; i++) {
                    writer.println(i);
                    Thread.sleep(250);
                    publishProgress (i);
                }
                writer.close();
            } catch (Exception e) {
                Log.d("FileOpenError", "Can't Save to file: " + getString(R.string.com_easton_consulting_FILENAME));
            }
            return true;
        }
       protected void onProgressUpdate(Integer... value) {
           adapter.notifyDataSetChanged();
           progressBar.setProgress(value[0]);
       }
    }

    private class NumberFileReader extends AsyncTask<String, Integer, Boolean> {

        protected void onPreExecute() {
            progressBar.setProgress(0);
        }

        protected void onPostExecute(Boolean result) {
            progressBar.setProgress(0);
        }

        protected Boolean doInBackground(String... params) {
            try {
                FileInputStream fis = openFileInput(getString(R.string.com_easton_consulting_FILENAME, Context.MODE_PRIVATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String lineVal;
                while ((lineVal = reader.readLine()) != null) {
                    numList.add(Integer.parseInt(lineVal));
                    Thread.sleep(250);
                    publishProgress(1);
                }
            } catch (Exception e) {
                Log.d("FileOpenError", e.getMessage());
            }
            return true;
        }


        protected void onProgressUpdate(Integer... value) {
            adapter.notifyDataSetChanged();
            progressBar.incrementProgressBy(value[0]);
        }
    }
}

