package com.example.jorge.gasprices;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.android.volley.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import androidx.room.Room;

public class Model  {

    static DAO dao;

    private static Model instance;

    private final Resources resources;


    private Model (Context ctx){
        DataBase db = Room.databaseBuilder(ctx,
                DataBase.class, "database-name").build();
        dao = db.GetDAO();
        resources = ctx.getResources();
    }

    public static Model getInstance(Context applicationContext){
        if (instance == null){
            instance = new Model(applicationContext);

        }
        return instance;
    }

    public void addDataBase() {

        InputStream stream = resources.openRawResource(R.raw.communities);
        Scanner scanner = new Scanner(stream);
        List<Community> communities = new ArrayList<>();

        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] arrayS = s.split("#");
            Community community = new Community();
            community.id = Integer.parseInt(arrayS[0]);
            community.name = arrayS[1];
            communities.add(community);
        }

        scanner.close();

        dao.insertCommunities(communities);

        stream = resources.openRawResource(R.raw.provinces);
        scanner = new Scanner(stream);
        List<Province> provinces = new ArrayList<>();

        while (scanner.hasNextLine()){
            String sProv = scanner.nextLine();
            String[] arraySProv = sProv.split("#");
            Province province = new Province();
            province.id = Integer.parseInt(arraySProv[0]);
            province.name = arraySProv[1];
            province.communityID = Integer.parseInt(arraySProv[2]);
            provinces.add(province);
        }

        scanner.close();

        dao.insertProvinces(provinces);

        stream = resources.openRawResource(R.raw.towns);
        scanner = new Scanner(stream);
        List<Town> towns = new ArrayList<>();

        while (scanner.hasNextLine()){
            String sTown = scanner.nextLine();
            String[] arraySTown = sTown.split("#");
            Town town = new Town();
            town.id = Integer.parseInt(arraySTown[0]);
            town.name = arraySTown[1];
            town.provinceID = Integer.parseInt(arraySTown[2]);
            towns.add(town);
        }

        scanner.close();

        dao.insertTowns(towns);



    }


    public void getCommunities(final Response.Listener<Community[]> listener) {
        new AsyncTask<Void, Void, Community[]>(){
            @Override
            protected Community[] doInBackground(Void... voids) {
                Community[] communities = dao.GetCommunities();


                if (communities.length == 0){
                    addDataBase();
                    communities = dao.GetCommunities();

                }

                return communities;
            }

            @Override
            protected void onPostExecute(Community[] communities) {
                listener.onResponse(communities);
            }
        }.execute();
    }


    public void getProvinces(final Response.Listener<Province[]> listener, final Community community) {
        new AsyncTask<Void, Void, Province[]>() {
            @Override
            protected Province[] doInBackground(Void... voids) {
                Province[] provinces = dao.GetProvinces(community.id);
                //Province[] provinces = dao.GetAllProvinces();
                //no funciona ninguna de las 2 porque por lo que parece, el provinces.length siempre es 0, es decir, no se carga la bbdd del fichero

                /*Province province1 = new Province(1,"Muro d'Alcoi", 9);
                Province province2 = new Province(2,"Murcia", 10);

                Province[] provinces = new Province[2];
                provinces[0] = province1;
                provinces[1] = province2;*/

                return provinces;


            }

            @Override
            protected void onPostExecute(Province[] provinces) {
                listener.onResponse(provinces);
            }
        }.execute();
    }

    public void getTowns(final Response.Listener<Town[]> listener, final Province province) {
        new AsyncTask<Void, Void, Town[]>(){

            @Override
            protected Town[] doInBackground(Void... voids) {
                Town [] towns = dao.GetTowns(province.id);

                return towns;
            }

            @Override
            protected void onPostExecute(Town[] towns) {
                listener.onResponse(towns);
            }
        }.execute();
    }

    public void getFuel(final Response.Listener<GasType[]> listener) {
        new AsyncTask<Void, Void, GasType[]>(){

            @Override
            protected GasType[] doInBackground(Void... voids) {

                GasType [] fuels = GasType.values();

                return fuels;
            }

            @Override
            protected void onPostExecute(GasType[] fuels) {
                listener.onResponse(fuels);
            }
        }.execute();
    }



    Comparator<Town> c = new Comparator<Town>() {
        @Override
        public int compare(Town town, Town input) {
            return town.name.compareTo(input.name);
        }
    };



    public boolean compareTownsBool(String s, Town[] townsArray) {
        Town input = new Town(-1,s,-1);
        int found = Arrays.binarySearch(townsArray, input, c);
        return found >= 0;
    }

    public int compareTownsInt(String s, Town[] townsArray) {
        Town input = new Town(-1,s,-1);
        int found = Arrays.binarySearch(townsArray, input, c);
        return found;
    }


}
