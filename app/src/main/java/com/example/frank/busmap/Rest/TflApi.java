package com.example.frank.busmap.Rest;

import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by frank on 08/02/2018.
 */

public interface TflApi {

    //Getting list of all stops for bus number and the direction(inbound/outbound)
    @GET("/Line/{id}/Route/Sequence/{direction}")
    Call<BusStopResponse> getAllBusStops (@Path("id") String id, @Path("direction") String direction, @Query("app_id") String AppId, @Query("app_key") String ApiKey);
    //This will be used to display the actual buses, will look at the first or last stop from the request above(PROBABLY WONT BE USING)
    @GET("/Line/{id}/Arrivals")
    Call<List<BusArrivalResponse>> getBusArrival (@Path("id") String id, @Query("app_id") String AppId, @Query("app_key") String ApiKey);
}
