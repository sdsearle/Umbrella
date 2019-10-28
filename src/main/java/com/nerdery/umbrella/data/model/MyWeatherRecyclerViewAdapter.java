package com.nerdery.umbrella.data.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.data.api.IconApi;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyWeatherRecyclerViewAdapter extends RecyclerView.Adapter<MyWeatherRecyclerViewAdapter.ViewHolder> {

    private List<ForecastCondition> mValues;
    int max = 0;
    int min = 0;
    //private final OnListFragmentInteractionListener mListener;

    public MyWeatherRecyclerViewAdapter(List<ForecastCondition> items){//, OnListFragmentInteractionListener listener) {
        mValues = items;
    }

    private void findMaxMin() {
        for (int i = 0; i < mValues.size(); i++) {
            if(mValues.get(max).getTemp() < mValues.get(i).getTemp()){
                max = i;
            }
            if(mValues.get(min).getTemp() > mValues.get(i).getTemp()){
                min = i;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String temp = (int) (mValues.get(position).getTemp()) + "°";
        holder.mTempertureView.setText(temp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time = simpleDateFormat.format(mValues.get(position).getTime());
        holder.mTimeview.setText(time);

        IconApi iconApi = new IconApi();
        Picasso.get().load(iconApi.getUrlForIcon(mValues.get(position).getIcon(), position == max || position == min)).into(holder.mImage);
        if(position == max) {
            int warm = holder.mView.getResources().getColor(R.color.weather_warm);
            holder.mImage.setColorFilter(warm);
            holder.mTempertureView.setTextColor(warm);
            holder.mTimeview.setTextColor(warm);
        } else if(position == min) {
            int warm = holder.mView.getResources().getColor(R.color.weather_cool);
            holder.mImage.setColorFilter(warm);
            holder.mTempertureView.setTextColor(warm);
            holder.mTimeview.setTextColor(warm);
        }else{
            int primary = holder.mView.getResources().getColor(R.color.text_color_primary);
            holder.mImage.setColorFilter(primary);
            holder.mTempertureView.setTextColor(primary);
            holder.mTimeview.setTextColor(primary);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateData(List<ForecastCondition> list) {
        mValues.clear();
        mValues.addAll(list);
        findMaxMin();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTempertureView;
        public final TextView mTimeview;
        public final ImageView mImage;
        public ForecastCondition mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTempertureView = view.findViewById(R.id.tvTemp);
            mTimeview = view.findViewById(R.id.tvTime);
            mImage = view.findViewById(R.id.ivImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimeview.getText() + "'";
        }
    }
}
