package com.example.jorge.gasprices;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IView{

    //Guardarnos el array de towns que se recibe en el método showTowns y cada vez que se modifique el autocomplete, comprobar
    //También comprobar cuando se cambie la provincia o la comunidad porque el autocomplete se queda escrito con lo anterior --> CLEAR
    //Llamar a método del presenter pasándole el array de towns y el string y desde ahí llamar al modelo para que compare y devuelva un bool que es el que se le pasa a la vista (enableButton)
    //Se desactiva el botón cuando la ciudad no está en el array de ciudades (las que coinciden con la provincia que está puesta)
    //¿Qué pasa cuando se introduce una ciudad que no tiene el tipo de combustible seleccionado? --> SE BUSCA EN INTERNET Y SI NO SE ENCUENTRA, SE DICE

    private Spinner comSpinner;
    private Spinner provSpinner;
    private AutoCompleteTextView townAuto;
    private Button showPrices;
    private Spinner fuelSpinner;
    private ArrayAdapter<Community> communityArrayAdapter;
    private ArrayAdapter<Province> provinceArrayAdapter;
    private  ArrayAdapter<Town> townArrayAdapter;
    private ArrayAdapter<GasType> gasTypeArrayAdapter;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comSpinner = findViewById(R.id.com);
        provSpinner = findViewById(R.id.prov);
        townAuto = findViewById(R.id.town);
        fuelSpinner = findViewById(R.id.fuel);
        showPrices = findViewById(R.id.show);

        showPrices.setEnabled(false);

        comSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Community community = (Community) comSpinner.getItemAtPosition(position);
                presenter.onCommunitySelected(community);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Province province = (Province) provSpinner.getItemAtPosition(position);
                presenter.onProvinceSelected(province);
                townAuto.getText().clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        townAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.checkTown(s.toString());


            }
        });

        fuelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GasType gasType = (GasType) fuelSpinner.getItemAtPosition(position);
                presenter.onFuelSelected(gasType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.request();
            }
        });





        presenter = new Presenter(Model.getInstance(getApplicationContext()), this);

    }


    @Override
    public void showCommunities(Community[] communities) {

        communityArrayAdapter = new ArrayAdapter<Community>(this, android.R.layout.simple_spinner_item, communities);
        communityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comSpinner.setAdapter(communityArrayAdapter);


    }

    @Override
    public void showProvinces(Province[] provinces) {

        provinceArrayAdapter = new ArrayAdapter<Province>(this, android.R.layout.simple_spinner_item, provinces);
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provSpinner.setAdapter(provinceArrayAdapter);

    }

    @Override
    public void showTowns(Town[] towns) {


        townArrayAdapter = new ArrayAdapter<Town>(this, android.R.layout.simple_list_item_1, towns);

        townAuto.setAdapter(townArrayAdapter);

    }

    @Override
    public void showFuels(GasType[] fuels) {

        gasTypeArrayAdapter = new ArrayAdapter<GasType>(this, android.R.layout.simple_spinner_dropdown_item, fuels);
        gasTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(gasTypeArrayAdapter);

    }

    @Override
    public void enableSearch(boolean enableButton) {
        showPrices.setEnabled(enableButton);
    }

    @Override
    public void switchToPrices(Town selectedTown, GasType gasType) {
        Intent intent = new Intent(this, PriceActivity.class);

        intent.putExtra("town", selectedTown);

        intent.putExtra("fuel", gasType);



        startActivity(intent);

    }

    @Override
    public void deleteDespres(Town[] townsArray) {
        //Toast.makeText(getApplicationContext(),"hola: " + townsArray.length, Toast.LENGTH_LONG).show();
    }


}
