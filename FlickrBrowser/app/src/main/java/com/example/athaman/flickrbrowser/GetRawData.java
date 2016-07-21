package com.example.athaman.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by keone on 7/20/2016.
 */

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}
public class GetRawData {

    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mRawUrl){
        this.mRawUrl = mRawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void execute(){
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }
    public void reset(){
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }


    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public class DownloadRawData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if(strings == null){
                return null;
            }

            try{
                URL url = new URL(strings[0]);
                Log.e(LOG_TAG, "Trying to connect to " + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                if(is == null){
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(is));

                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                return buffer.toString();


            }catch (IOException e){
                Log.e(LOG_TAG, "Error " + e.getMessage());
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                   try {
                       reader.close();
                   }catch(final IOException e){
                       Log.e(LOG_TAG, "Error Closing stream", e);
                   }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String webData) {
            mData = webData;
            Log.v(LOG_TAG, "Data returned was: " + mData);

            if(mData == null){
                if(mRawUrl == null){
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                }else{
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else{
                mDownloadStatus = DownloadStatus.OK;
            }
        }
    }
}
