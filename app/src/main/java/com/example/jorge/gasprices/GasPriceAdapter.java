package com.example.jorge.gasprices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class GasPriceAdapter extends ArrayAdapter<StationPrice> {

    private TextView gasPrice;
    private TextView stationDirection;

    public GasPriceAdapter(@NonNull Context context, @NonNull List<StationPrice> objects) {
        super(context, R.layout.layout_gasprice, objects);
    }




    public View getView (int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_gasprice, parent, false);
        }

        else {
            view = convertView;
        }

        gasPrice = view.findViewById(R.id.gasPrice);
        stationDirection = view.findViewById(R.id.stationDirection);

        StationPrice stationPrice = getItem(position);
        gasPrice.setText(stationPrice.precioProducto);
        stationDirection.setText(stationPrice.direccion);

        return view;


    }
}
