package com.example.jorge.gasprices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PriceActivity extends AppCompatActivity implements IViewPrices {

    //El constructor del presenter llamará al modelo para recuperar las estaciones de servicio pertinentes (asíncrono)
    //Tendremos que poner visible e invisible la progress bar y el list view
    //Guardar en objetos stationprice lo que recuperemos del JSON

    //Primer error: nos da que el FUEL es null (ahora mismo funciona porque le pasamos directamente el tipo Gasolina95)
    //Segundo error: se queda cargando y parece que nunca entra en el ShowPrices (nunca responde nada el onResponse?)

    //PRIMERO: hacer el layout del diálogo --> CHECK
    //primero punto 5: meter flechita back --> CHECK
    //SEGUNDO: introducir en la clase StationPriceDialogFragment los valores correspondientes de los 4 text view --> CHECK
    //TERCERO: Hacer el maps --> CHECK
    //CUARTO: Meter toasts --> CHECK


    //PRIMERO: LABEL DEL MAPS
    //SEGUNDO: Layouts girados
    //TERCERO: Memoria

    private ListView listView;

    private List<StationPrice> lista = new ArrayList<>();

    private ProgressBar progressBar;

    private GasType fuel;
    private Town town;

    private ActionBar actionBar;

    private PresenterPrices presenterPrices;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        actionBar = getSupportActionBar();

        listView = findViewById(R.id.prices);
        progressBar = findViewById(R.id.progressBar);

        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        town = intent.getParcelableExtra("town");
        fuel = (GasType)intent.getSerializableExtra("fuel");


        actionBar.setTitle(town.toString());
        actionBar.setSubtitle(fuel.toString());
        actionBar.setDisplayHomeAsUpEnabled(true);


        presenterPrices = new PresenterPrices(ModelPrices.getInstance(getApplicationContext()), (IViewPrices) this, town, fuel);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StationPrice stationPrice = (StationPrice) listView.getItemAtPosition(position);
                StationPriceDialogFragment stationPriceDialogFragment = new StationPriceDialogFragment();
                Bundle args = new Bundle();
                args.putParcelable("StationPrice", stationPrice);
                stationPriceDialogFragment.setArguments(args);
                stationPriceDialogFragment.show(getSupportFragmentManager(), "SP");
            }
        });


    }

    @Override
    public void showPrices(List<StationPrice> response) {

        for (int i = 0; i < response.size(); i++){
            lista.add(response.get(i));
        }

        GasPriceAdapter adapter = new GasPriceAdapter(PriceActivity.this, lista);
        listView.setAdapter(adapter);

        if (lista.size()== 0){
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }

        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),"NETWORK ERROR", Toast.LENGTH_LONG).show();
        finish();


    }


}
