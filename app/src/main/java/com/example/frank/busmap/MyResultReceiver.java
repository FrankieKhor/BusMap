package com.example.frank.busmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by frank on 28/03/2018.
 */
//Used to receive data back from intent
public class MyResultReceiver extends ResultReceiver {
    String TAG = MyResultReceiver.class.getName();
    private Receiver mReceiver;

    public MyResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }else {
            Log.d(TAG, "NO problem " + resultCode);
        }
    }
}
