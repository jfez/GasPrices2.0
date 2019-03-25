package com.example.jorge.gasprices;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class StationPriceDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle arguments = getArguments();
        final StationPrice stationPrice = (StationPrice) arguments.get("StationPrice");

        View view = requireActivity().getLayoutInflater().inflate(R.layout.layout_dialog, null);
        TextView rotulo = view.findViewById(R.id.rotulo);
        rotulo.setText(stationPrice.rotulo+"");
        TextView addName = view.findViewById(R.id.addName);
        addName.setText(stationPrice.direccion+"");
        TextView priceCant = view.findViewById(R.id.priceCant);
        priceCant.setText(stationPrice.precioProducto+"");

               //Hay que hacer otro layout y pasarlo arriba
                builder.setView(view)
                .setPositiveButton("OK", null)
                .setNeutralButton("MAP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String label = URLEncoder.encode(stationPrice.rotulo);
                        Double latitud = stationPrice.latitud;
                        Double longitud = stationPrice.longitud;

                        //Uri gmmIntentUri = Uri.parse("geo:0,0?q=-33.8666,151.1957(Google+Sydney)");
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+latitud+","+longitud+"("+label+")");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                        else {
                            Toast.makeText(getContext(),"MAPS APPLICATION NOT FOUND", Toast.LENGTH_LONG).show();
                        }


                    }
                });

        return builder.create();
    }
}
