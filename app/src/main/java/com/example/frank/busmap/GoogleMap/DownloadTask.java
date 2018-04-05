package com.example.frank.busmap.GoogleMap;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String>
{
    private String TAG = DownloadTask.class.getName();
    private boolean geoCode;

    public DownloadTask(Boolean geoCode)
    {
        this.geoCode = geoCode;
    }

    @Override
    protected String doInBackground(String... url)
    {
        String data = "";
        try
        {
            data = downloadUrl(url[0]);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }
        return data;
    }

    private String downloadUrl(String strUrl) throws IOException
    {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line ;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }
        finally
        {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        if(geoCode)
        {
            ParserTaskGeo parserTaskGeo = new ParserTaskGeo();
            parserTaskGeo.execute(result);
        }
        else
        {
            ParserTaskReverseGeo parserTaskReverse = new ParserTaskReverseGeo();
            parserTaskReverse.execute(result);
        }
    }

}





