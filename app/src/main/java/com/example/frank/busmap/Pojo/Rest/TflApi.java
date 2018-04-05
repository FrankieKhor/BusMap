package com.example.frank.busmap.Pojo.Rest;


import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getStopPointArrival.StopPointArrival;
import com.example.frank.busmap.Pojo.getTicketPrice.TicketPrice;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

//All POJO are created via http://pojo.sodhanalibrary.com/ and tweaked for some getter/setter emthods
public interface TflApi {

    //Getting list of all stops for bus number and the direction(inbound/outbound)
    @GET("Line/{id}/Route/Sequence/{direction}")
    Call<BusStopResponse> getAllBusStops (@Path("id") String id, @Path("direction") String direction,
                                          @Query("app_id") String AppId, @Query("app_key") String ApiKey);
    //Observable<BusStopResponse> getAllBusStops (@Path("id") String id, @Path("direction") String direction, @Query("app_id") String AppId, @Query("app_key") String ApiKey);
    //This will be used to display the actual buses, will look at the first or last stop from the request above(PROBABLY WONT BE USING)
    @GET("Line/{id}/Arrivals")
    Call<List<BusArrivalResponse>> getBusArrival (@Path("id") String id, @Query("app_id") String AppId, @Query("app_key") String ApiKey);

    @GET("StopPoint/{id}/Arrivals")
    Call<List<StopPointArrival>> getStopArrival (@Path("id") String id, @Query("app_id") String AppId, @Query("app_key") String ApiKey);

    @GET("/Journey/JourneyResults/{from}/to/{to}")
    Call<JourneyFromToResponse> getJourney (@Path("from") String from, @Path("to") String to, @Query("app_id") String AppId, @Query("app_key") String ApiKey);

    @GET("StopPoint/{StopPointFrom}/FareTo/{StopPointTo}")
    Call<List<TicketPrice>> getTicketPrices(@Path("StopPointFrom") String from, @Path("StopPointTo") String to, @Query("app_id") String AppId, @Query("app_key") String ApiKey );

    // @GET("/Journey/JourneyResults/{from}/to/{to}")
    //Call<List<BusArrivalResponse>> getBusArrival (@Path("id") String id, @Query("app_id") String AppId, @Query("app_key") String ApiKey);

}
