package com.example.frank.busmap;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by frank on 21/02/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.BusViewHolder>{

    private ArrayList<List<BusArrivalResponse>> list;

    public DataAdapter(ArrayList<List<BusArrivalResponse>> list) {
        Log.d(TAG, "LISTTTTTTT");
        this.list = list;
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "CREATRE");

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, viewGroup,false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusViewHolder busViewHolder, int i) {
        Log.d(TAG, "ON BIND 0 + " + i);
        busViewHolder.busName.setText(list.get(i).get(i).getStationName());
        busViewHolder.busId.setText(list.get(i).get(i).getId());
        // busViewHolder.iv.setImageResource(list.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BusViewHolder extends RecyclerView.ViewHolder {

        //CardView cv;
        TextView busName, busId;
        ImageView iv ;

        BusViewHolder(View view) {
            super(view);
            Log.d(TAG, "BUSVIEW");
            //cv = (CardView)view.findViewById(R.id.busCardView);
            busName =(TextView)view.findViewById(R.id.busName);
            busId =(TextView)view.findViewById(R.id.busId);
            //iv =(ImageView) view.findViewById(R.id.busImageView);

        }
    }
}

