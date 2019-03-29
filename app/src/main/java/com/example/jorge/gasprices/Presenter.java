package com.example.jorge.gasprices;

import android.widget.Toast;

import com.android.volley.Response;

public class Presenter {

    Model model;
    IView view;
    Town[] townsArray;
    Town selectedTown;
    GasType fuel;

    public Presenter (Model model, IView view){
        this.model = model;
        this.view = view;
        model.getCommunities(new Response.Listener<Community[]>(){
            @Override
            public void onResponse(Community[] response) {
                onCommunityisAvailable(response);

            }
        });
        model.getFuel(new Response.Listener<GasType[]>(){

            @Override
            public void onResponse(GasType[] response) {
                onFuelisAvailable(response);
            }
        });


    }

    private void onFuelisAvailable(GasType[] fuels) {
        view.showFuels(fuels);
    }


    private void onCommunityisAvailable(Community[] communities) {
        view.showCommunities(communities);

    }

    private void onProvinceisAvailable(Province[] provinces) {
        view.showProvinces(provinces);
    }

    private void onTownisAvaillable(Town[] towns) {
        view.showTowns(towns);
        townsArray = towns;
        view.deleteDespres(townsArray);

    }

    public void onCommunitySelected(Community community) {
        model.getProvinces(new Response.Listener<Province[]>(){

            @Override
            public void onResponse(Province[] response) {

                onProvinceisAvailable(response);

            }
        }, community);
    }

    public void onProvinceSelected(Province province) {
        model.getTowns(new Response.Listener<Town[]>(){
            @Override
            public void onResponse(Town[] response) {
                onTownisAvaillable(response);

            }
        }, province);

    }


    public void checkTown(String s) {
        if (townsArray == null){
            return;
        }
        boolean enableButton = model.compareTownsBool (s, townsArray);
        if (enableButton){
            int index = model.compareTownsInt(s, townsArray);
            selectedTown = townsArray[index];
        }

        view.enableSearch(enableButton);
    }

    public void request() {
        view.switchToPrices(selectedTown, fuel);
    }

    public void onFuelSelected(GasType gasType) {
        fuel = gasType;
    }
}
