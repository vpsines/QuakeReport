package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Activity activity, ArrayList<Earthquake> earthquake)
    {
        super(activity,0,earthquake);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private static final String seperator=" of ";
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magValue){
        int magnitudeFloor=(int) Math.floor(magValue);
        int magId=0;
        switch (magnitudeFloor){
            case 0:
            case 1: magId= ContextCompat.getColor(getContext(), R.color.magnitude1);
                    break;
            case 2: magId= ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3: magId= ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4: magId= ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5: magId = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6: magId= ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7: magId= ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8: magId= ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9: magId= ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default: magId= ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }
        return magId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitude=(TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat formatter=new DecimalFormat("0.0");
        String mag=formatter.format(currentEarthquake.getMagnitude());
        magnitude.setText(mag);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String place=currentEarthquake.getPlace();
        String offset;
        String pLocation;
        if(place.contains(seperator)){
            String[] location=place.split(seperator);
            offset=location[0]+seperator;
            pLocation=location[1];
        }else{
            offset= getContext().getString(R.string.near_the);
            pLocation=place;
        }

        TextView offsetView=(TextView) listItemView.findViewById(R.id.location_offset);
        offsetView.setText(offset);
        TextView pLocationView=(TextView) listItemView.findViewById(R.id.primary_location);
        pLocationView.setText(pLocation);

        Date dateObject=new Date(currentEarthquake.getTimeInms());
        TextView date=(TextView) listItemView.findViewById(R.id.date);
        String formatedDate=formatDate(dateObject);
        date.setText(formatedDate);

        TextView time=(TextView) listItemView.findViewById(R.id.time);
        String formatedTime=formatTime(dateObject);
        time.setText(formatedTime);

        return listItemView;

    }

}
