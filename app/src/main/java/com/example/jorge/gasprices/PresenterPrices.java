package com.example.jorge.gasprices;

import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

public class PresenterPrices {

    IViewPrices viewPrices;
    ModelPrices modelPrices;

    public PresenterPrices(ModelPrices modelPrices, IViewPrices viewPrices, Town town, GasType fuel) {
        this.modelPrices = modelPrices;
        this.viewPrices = viewPrices;

        modelPrices.getPrices(town, fuel, new Response.Listener<List<StationPrice>> (){

            @Override
            public void onResponse(List<StationPrice> response) {
                onPriceisAvailable(response);

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                PresenterPrices.this.viewPrices.showError(error.getMessage());
            }
        });


    }

    private void onPriceisAvailable(List<StationPrice> response) {

        viewPrices.showPrices(response);

    }


}
