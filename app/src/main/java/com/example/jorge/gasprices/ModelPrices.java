package com.example.jorge.gasprices;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.DialogFragment;


@SuppressLint("ValidFragment")
public class ModelPrices extends DialogFragment {

    private static final Locale spanish = new Locale("es", "ES");
    private static final NumberFormat doubleFormat = NumberFormat.getInstance(spanish);
    private static double parseDouble(String s) {
        try {
            return doubleFormat.parse(s).doubleValue();
        } catch (ParseException e) {
            return 0;
        }
    }


    private String url = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipioProducto/";
    private RequestQueue queue;
    private static ModelPrices instance;
    private List<StationPrice> priceList;

    private  ModelPrices(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public static ModelPrices getInstance(Context applicationContext) {     //Para hacerlo Singleton
        if (instance == null){
            instance = new ModelPrices(applicationContext);

        }
        return instance;
    }


    public void getPrices(Town town, GasType fuel, final Response.Listener<List<StationPrice>> listener, final Response.ErrorListener errorListener) {
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, url + town.id + "/" + fuel.getCode(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                priceList = parseJSON(response);

                if (priceList != null){
                    listener.onResponse(priceList);
                }

                else {
                    errorListener.onErrorResponse(new VolleyError());


                }



            }
        }, errorListener);
        queue.add(request);



    }

    private List<StationPrice> parseJSON(JSONObject response) {

        List<StationPrice> stationPriceList = new ArrayList<>();

        try {
            JSONArray jsonArray = response.getJSONArray("ListaEESSPrecio");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject price = jsonArray.getJSONObject(i);

                String rotulo = price.getString("Rótulo");
                String direccion = price.getString("Dirección");
                String precioProducto = price.getString("PrecioProducto");
                String latitud = price.getString("Latitud");
                String longitud = price.getString("Longitud (WGS84)");

                Double numLatitud = parseDouble(latitud);
                Double numLongitud = parseDouble(longitud);

                StationPrice stationPrice = new StationPrice(rotulo, direccion, precioProducto, numLatitud, numLongitud);

                stationPriceList.add(stationPrice);

            }
            return stationPriceList;
        }
        catch (JSONException e) {
            return null;
        }
    }


}
