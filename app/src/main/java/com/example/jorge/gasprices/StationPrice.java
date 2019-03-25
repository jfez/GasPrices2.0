package com.example.jorge.gasprices;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;


public class StationPrice implements Parcelable {

    public String rotulo;
    public String direccion;
    public String precioProducto;
    public Double latitud;
    public Double longitud;

    public StationPrice(String rotulo, String direccion, String precioProducto, Double numLatitud, Double numLongitud) {
        this.rotulo = rotulo;
        this.direccion = direccion;
        this.precioProducto = precioProducto;
        this.latitud = numLatitud;
        this.longitud = numLongitud;
    }



    protected StationPrice(Parcel in) {
        rotulo = in.readString();
        direccion = in.readString();
        precioProducto = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    public static final Creator<StationPrice> CREATOR = new Creator<StationPrice>() {
        @Override
        public StationPrice createFromParcel(Parcel in) {
            return new StationPrice(in);
        }

        @Override
        public StationPrice[] newArray(int size) {
            return new StationPrice[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return precioProducto + " " + direccion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rotulo);
        dest.writeString(direccion);
        dest.writeString(precioProducto);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }
}
