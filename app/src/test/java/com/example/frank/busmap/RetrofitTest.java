package com.example.frank.busmap;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Config
@RunWith(MyRobolectricTestRunner.class)
public class RetrofitTest {

//    @Before
//    public void setUp(){
//        MockWebServer mockWebServer = new MockWebServer();
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(mockWebServer.url("").toString())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        mockWebServer.enqueue(new MockResponse().setBody(""));
//
//
//    }

    @Test
    public void a() throws IOException{
        getAssetStream(RuntimeEnvironment.application, "U3BusRouteTest.json");
//        InputStream inputStream = RuntimeEnvironment.application.getAssets().open("U3BusRouteTest.json");
//        Assert.assertNotNull(inputStream);
    }

    private InputStream getAssetStream(Context context, String fileName)
            throws IOException {
        InputStream result;
        try {
            result = context.getResources().getAssets().open(fileName);
        } catch (IOException e) {
            result = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("assets" + File.separator + fileName);
        }
        if (result == null)
            throw new IOException(
                    String.format("Asset file [%s] not found.",
                            fileName)
            );

        return result;
    }
}
