package com.example.frank.busmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
public class RealTimeGTFS extends AppCompatActivity {
    private static final String TAG = "RealTimeGTFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_gtfs);
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("file:///android_asset/gtfs/trips");
        FeedMessage feed = FeedMessage.parseFrom(url.openStream());
        for (FeedEntity entity : feed.getEntityList()) {
            if (entity.hasTripUpdate()) {
                Log.d(TAG, "Apple "+entity.getTripUpdate().toString());
            }
        }
    }

}
