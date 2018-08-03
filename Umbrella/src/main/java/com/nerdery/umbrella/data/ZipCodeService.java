package com.nerdery.umbrella.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nerdery.umbrella.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZipCodeService {

    public interface ZipLocationListener {
        void onLocationFound(ZipLocation location);
        void onLocationNotFound();
    }

    public class ZipLocation {
        @SerializedName("z")
        long zipCode;
        @SerializedName("c")
        String city;
        @SerializedName("s")
        String state;
        @SerializedName("la")
        double latitude;
        @SerializedName("lo")
        double longitude;

        public long getZipCode() {
            return zipCode;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public static void getLatLongByZip(Context context, String zipCode, ZipLocationListener listener) {
        long zipLong = 0;
        try {
            zipLong = Long.valueOf(zipCode);
        } catch (NumberFormatException e) {
            listener.onLocationNotFound();
        }

        InputStream stream = context.getResources().openRawResource(R.raw.zipcodes);
        String jsonString = readJsonFile(stream);
        Gson gson = new Gson();
        ZipLocation[] locations = gson.fromJson(jsonString, ZipLocation[].class);
        for (ZipLocation location : locations) {
            if (location.zipCode == zipLong) listener.onLocationFound(location);
        }
        listener.onLocationNotFound();
    }

    private static String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toString();
    }

}
