package n.rnu.isetr.weatherwidgetapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import android.view.MotionEvent;


import java.util.Locale;


public class CarteFrance extends AppCompatActivity {

    private static final String API_KEY = "df07195ad38e7d3cbb2f448d3aec3285";

    /*ImageView des ciels*/
    private static ImageView centre;
    private static ImageView grandNord;
    private static ImageView grandOuest;
    private static ImageView grandEst;
    private static ImageView grandSudOuest;
    private static ImageView grandCentre;
    private static ImageView grandSudEst;
    private static ImageView corse;
    private static ImageView outreMer;
    private static int affichageCiel = 0;


    /*TextView des températures*/
    private static TextView tempCentre;
    private static TextView tempGrandNord;
    private static TextView tempGrandOuest;
    private static TextView tempGrandEst;
    private static TextView tempGrandSudOuest;
    private static TextView tempGrandCentre;
    private static TextView tempGrandSudEst;
    private static TextView tempCorse;
    private static TextView tempOutreMer;
    private static int affichageTemperature = 0;

    public Intent intent, intent_recep;

    /*Variables d'affichage*/
    public boolean theme_sombre = false;
    public boolean celsius = true;
    public int indice_langue = 0;
    public boolean accord_geo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_france);

        ImageButton retourButton = findViewById(R.id.retour);
        /*Méthode permettant de retourner au layout principal lors du clic*/
        retourButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                actualiser();
                retourMenu();
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        retourButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.2f);
                    v.setScaleY(1.2f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });

        /*Récupération des id des ImageView et des TextView
        * La carte est rendue vierge lors de la première ouverture du layout*/
        centre = findViewById(R.id.Centre);
        grandNord = findViewById(R.id.GrandNord);
        grandOuest = findViewById(R.id.GrandOuest);
        grandEst = findViewById(R.id.GrandEst);
        grandSudOuest = findViewById(R.id.GrandSudOuest);
        grandCentre = findViewById(R.id.GrandCentre);
        grandSudEst = findViewById(R.id.GrandSudEst);
        corse = findViewById(R.id.Corse);
        outreMer = findViewById(R.id.OutreMer);

        centre.setVisibility(View.INVISIBLE);
        grandNord.setVisibility(View.INVISIBLE);
        grandEst.setVisibility(View.INVISIBLE);
        grandOuest.setVisibility(View.INVISIBLE);
        grandSudOuest.setVisibility(View.INVISIBLE);
        grandCentre.setVisibility(View.INVISIBLE);
        grandSudEst.setVisibility(View.INVISIBLE);
        corse.setVisibility(View.INVISIBLE);
        outreMer.setVisibility(View.INVISIBLE);


        tempCentre = findViewById(R.id.tempCentre);
        tempGrandNord = findViewById(R.id.tempGrandNord);
        tempGrandOuest = findViewById(R.id.tempGrandOuest);
        tempGrandEst = findViewById(R.id.tempGrandEst);
        tempGrandSudOuest = findViewById(R.id.tempGrandSudOuest);
        tempGrandCentre = findViewById(R.id.tempGrandCentre);
        tempGrandSudEst = findViewById(R.id.tempGrandSudEst);
        tempCorse = findViewById(R.id.tempCorse);
        tempOutreMer = findViewById(R.id.tempOutreMer);

        tempCentre.setVisibility(View.INVISIBLE);
        tempGrandNord.setVisibility(View.INVISIBLE);
        tempGrandOuest.setVisibility(View.INVISIBLE);
        tempGrandEst.setVisibility(View.INVISIBLE);
        tempGrandSudOuest.setVisibility(View.INVISIBLE);
        tempGrandCentre.setVisibility(View.INVISIBLE);
        tempGrandSudEst.setVisibility(View.INVISIBLE);
        tempCorse.setVisibility(View.INVISIBLE);
        tempOutreMer.setVisibility(View.INVISIBLE);



        ImageButton actualiserBouton = findViewById(R.id.actualiser);
        /*Méthode permettant l'actualisation de la carte lors du clic*/
        actualiserBouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(affichageCiel == 1){
                    TodaysWeather("Paris", centre);
                    TodaysWeather("Lille", grandNord);
                    TodaysWeather("Strasbourg", grandEst);
                    TodaysWeather("Rennes", grandOuest);
                    TodaysWeather("Bordeaux", grandSudOuest);
                    TodaysWeather("Lyon", grandCentre);
                    TodaysWeather("Marseille", grandSudEst);
                    TodaysWeather("Ajaccio", corse);
                    TodaysWeather("Fort-De-France", outreMer);
                }
                if(affichageTemperature == 1){
                    TodaysTemperature("Paris", tempCentre);
                    TodaysTemperature("Lille", tempGrandNord);
                    TodaysTemperature("Strasbourg", tempGrandOuest);
                    TodaysTemperature("Rennes", tempGrandEst);
                    TodaysTemperature("Bordeaux", tempGrandSudOuest);
                    TodaysTemperature("Lyon", tempGrandCentre);
                    TodaysTemperature("Marseille", tempGrandSudEst);
                    TodaysTemperature("Ajaccio", tempCorse);
                    TodaysTemperature("Fort-De-France", tempOutreMer);
                }
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        actualiserBouton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.2f);
                    v.setScaleY(1.2f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });


        ImageButton cielButton = findViewById(R.id.ciel);
        /*Méthode permettant d'activer ou de désactiver l'affichage des ciels lors du clic*/
        cielButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(affichageCiel == 0){
                    affichageCiel = 1;

                    TodaysWeather("Paris", centre);
                    TodaysWeather("Lille", grandNord);
                    TodaysWeather("Strasbourg", grandEst);
                    TodaysWeather("Rennes", grandOuest);
                    TodaysWeather("Bordeaux", grandSudOuest);
                    TodaysWeather("Lyon", grandCentre);
                    TodaysWeather("Marseille", grandSudEst);
                    TodaysWeather("Ajaccio", corse);
                    TodaysWeather("Fort-De-France", outreMer);

                    centre.setVisibility(View.VISIBLE);
                    grandNord.setVisibility(View.VISIBLE);
                    grandEst.setVisibility(View.VISIBLE);
                    grandOuest.setVisibility(View.VISIBLE);
                    grandSudOuest.setVisibility(View.VISIBLE);
                    grandCentre.setVisibility(View.VISIBLE);
                    grandSudEst.setVisibility(View.VISIBLE);
                    corse.setVisibility(View.VISIBLE);
                    outreMer.setVisibility(View.VISIBLE);
                }
                else{
                    affichageCiel = 0;

                    centre.setVisibility(View.INVISIBLE);
                    grandNord.setVisibility(View.INVISIBLE);
                    grandEst.setVisibility(View.INVISIBLE);
                    grandOuest.setVisibility(View.INVISIBLE);
                    grandSudOuest.setVisibility(View.INVISIBLE);
                    grandCentre.setVisibility(View.INVISIBLE);
                    grandSudEst.setVisibility(View.INVISIBLE);
                    corse.setVisibility(View.INVISIBLE);
                    outreMer.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        cielButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.2f);
                    v.setScaleY(1.2f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });


        ImageButton tempButton = findViewById(R.id.temperature);

        /*Méthode permettant d'activer ou de désactiver l'affichage des températures lors du clic*/
        tempButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(affichageTemperature == 0){
                    affichageTemperature = 1;

                    TodaysTemperature("Paris", tempCentre);
                    TodaysTemperature("Lille", tempGrandNord);
                    TodaysTemperature("Strasbourg", tempGrandOuest);
                    TodaysTemperature("Rennes", tempGrandEst);
                    TodaysTemperature("Bordeaux", tempGrandSudOuest);
                    TodaysTemperature("Lyon", tempGrandCentre);
                    TodaysTemperature("Marseille", tempGrandSudEst);
                    TodaysTemperature("Ajaccio", tempCorse);
                    TodaysTemperature("Fort-De-France", tempOutreMer);

                    tempCentre.setVisibility(View.VISIBLE);
                    tempGrandNord.setVisibility(View.VISIBLE);
                    tempGrandOuest.setVisibility(View.VISIBLE);
                    tempGrandEst.setVisibility(View.VISIBLE);
                    tempGrandSudOuest.setVisibility(View.VISIBLE);
                    tempGrandCentre.setVisibility(View.VISIBLE);
                    tempGrandSudEst.setVisibility(View.VISIBLE);
                    tempCorse.setVisibility(View.VISIBLE);
                    tempOutreMer.setVisibility(View.VISIBLE);
                }
                else{
                    affichageTemperature = 0;

                    tempCentre.setVisibility(View.INVISIBLE);
                    tempGrandNord.setVisibility(View.INVISIBLE);
                    tempGrandOuest.setVisibility(View.INVISIBLE);
                    tempGrandEst.setVisibility(View.INVISIBLE);
                    tempGrandSudOuest.setVisibility(View.INVISIBLE);
                    tempGrandCentre.setVisibility(View.INVISIBLE);
                    tempGrandSudEst.setVisibility(View.INVISIBLE);
                    tempCorse.setVisibility(View.INVISIBLE);
                    tempOutreMer.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        tempButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.2f);
                    v.setScaleY(1.2f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });

        intent_recep = getIntent();
        actualiser();

    }

    /*Méthode de récupération de l'état actuel du ciel pour une ville donnée*/
    private void TodaysWeather(String ville, ImageView image) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + ville + "&appid=" + API_KEY;

        Ion.with(this)
                .load(apiUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if (e != null) {
                            e.printStackTrace();

                        } else {
                            JsonArray weather = result.get("weather").getAsJsonArray();
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon, image);
                        }
                    }
                });
    }

    /*Méthode de récupération de la température actuelle pour une ville donnée*/
    private void TodaysTemperature(String ville, TextView texte) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + ville + "&appid=" + API_KEY;

        Ion.with(this)
                .load(apiUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if (e != null) {
                            e.printStackTrace();

                        } else {
                            JsonObject main = result.get("main").getAsJsonObject();
                            double tempKelvin = main.get("temp").getAsDouble();
                            double tempCelsius = tempKelvin - 273.15;
                            double tempFahrenheit = (tempCelsius * 1.8) + 32;
                            String temp;
                            if(celsius == true) {
                                temp = Integer.toString((int) Math.round(tempCelsius));
                            }else{
                                temp = Integer.toString((int) Math.round(tempFahrenheit));
                            }
                            texte.setText(temp + "°");
                        }
                    }
                });
    }

    /*Méthode de récupération de l'icône du ciel*/
    private void loadIcon(String icon, ImageView image) {
        Ion.with(this)
                .load("http://openweathermap.org/img/w/" + icon + ".png")
                .intoImageView(image);
    }

    /*Méthode exécutant le retour au layout principal*/
    private void retourMenu(){
        /* Changer le nom de l'activité .class */
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("theme", theme_sombre);
        intent.putExtra("degre", celsius);
        intent.putExtra("langue", indice_langue);
        intent.putExtra("accord", accord_geo);
        startActivity(intent);
    }

    /*Méthode d'actualisation des paramètres d'affichage*/
    private void actualiser(){
        if(intent_recep != null){
            this.celsius = intent_recep.getBooleanExtra("degre", true);
            this.theme_sombre = intent_recep.getBooleanExtra("theme", false);
            this.indice_langue = intent_recep.getIntExtra("langue", 0);
            this.accord_geo = intent_recep.getBooleanExtra("accord", false);
        }
        Locale locale = new Locale("fr");
        if(indice_langue==0){
            locale = new Locale("fr");
        }
        if(indice_langue==1){
            locale = new Locale("en");
        }
        if(indice_langue==2){
            locale = new Locale("es");
        }
        if(indice_langue==3){
            locale = new Locale("ru");
        }
        if(indice_langue==4){
            locale = new Locale("zh");
        }
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        if(theme_sombre == true){
            findViewById(R.id.layout_map).setBackgroundColor(Color.parseColor("#283B62"));
        }else{
            findViewById(R.id.layout_map).setBackgroundColor(Color.parseColor("#D0EFF6"));
        }
    }
}