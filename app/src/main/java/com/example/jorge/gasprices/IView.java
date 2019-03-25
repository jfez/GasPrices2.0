package com.example.jorge.gasprices;

public interface IView {

    void showCommunities(Community[] communities);

    void showProvinces(Province[] provinces);

    void showTowns(Town[] towns);

    void showFuels(GasType[] fuels);

    void enableSearch(boolean enableButton);

    void switchToPrices(Town selectedTown, GasType fuel);

    void deleteDespres(Town[] townsArray);
}
