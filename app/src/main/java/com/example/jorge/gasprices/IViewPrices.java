package com.example.jorge.gasprices;

import android.os.Bundle;

import java.util.List;

import androidx.appcompat.app.AlertDialog;

public interface IViewPrices {
    

    void showPrices(List<StationPrice> response);

    void showError(String message);


}
