package n.rnu.isetr.weatherwidgetapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrInterface;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Intent intent, intent_recep;
    private Button btn_param, btn_carte;
    private static final String API_KEY = "df07195ad38e7d3cbb2f448d3aec3285";
    ImageView iconWeather;
    TextView text_tmp, text_loc,text_desc,text_vent,text_humi;
    ListView previsions;
    ProgressBar pb;

    private static String lat;
    private static String lon;

    private static String ville = "Paris";
    android.location.LocationManager locationManager;

    /*Variables d'affichage*/
    public boolean theme_sombre = false;
    public boolean celsius = true;
    public int indice_langue = 0;
    public boolean accord_geo = false;

    private SlidrInterface slidr;
    private SlidrConfig slidrConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidrConfig config = new SlidrConfig.Builder()
                .sensitivity(1f)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                .listener(new SlidrListener(){
                    @Override
                    public void onSlideStateChanged(int state){
                    }

                    @Override
                    public void onSlideChange(float percent){
                        Intent intent_carte = new Intent(getApplicationContext(), CarteFrance.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onSlideOpened(){
                    }

                    @Override
                    public boolean onSlideClosed(){
                        return false;
                    }
                })
                .build();


        slidr = Slidr.attach(this, config);
        Slidr.attach(this);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        /*Récupération des id*/
        iconWeather = findViewById(R.id.iconWeather);
        text_tmp = findViewById(R.id.text_tmp);
        text_loc = findViewById(R.id.text_loc);
        text_desc=findViewById(R.id.text_desc);
        text_vent = findViewById(R.id.windspeed);
        text_humi=findViewById(R.id.text_humi);
        previsions = findViewById(R.id.previsions);
        pb=findViewById(R.id.pb);
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                ville = query;
                TodaysWeather("24","32");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                // Called when the user changes the search query text
                return false;
            }
        });

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getLocation();

        btn_param = findViewById(R.id.animated_button);
        /*Méthode permettant d'accéder au layout des paramètres lors du clic*/
        this.btn_param.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goParametres();
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        this.btn_param.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.1f);
                    v.setScaleY(1.1f);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });

        btn_carte = findViewById(R.id.map_button);
        /*Méthode permettant d'accéder au layout de la carte lors du clic*/
        this.btn_carte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goCarte();
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        this.btn_carte.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.1f);
                    v.setScaleY(1.1f);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
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

    @SuppressLint("MissingPermission")
    /*Méthode de récupération de localisation du téléphone*/
    private void getLocation(){
        try {
            locationManager = (android.location.LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,5000,5,MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    /*Méthode d'actualisation de la localisation en cas de changement*/
    public void onLocationChanged(Location location){
         try {
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());
            TodaysWeather(lat,lon);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*Méthode de récupération de la météo actuelle pour une ville donnée*/
    private void TodaysWeather(String lati , String longi){
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + ville + "&appid=" + API_KEY;

        Ion.with(this)
                .load(apiUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>(){
                    @Override
                    public void onCompleted(Exception e, JsonObject result){
                        // do stuff with the result or error
                        if (e != null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        } else {
                            pb.setVisibility(View.INVISIBLE);
                            // Conversion du résultat en JSON vers Java
                            JsonObject main = result.get("main").getAsJsonObject();
                            JsonObject wind = result.get("wind").getAsJsonObject();
                            String temp;
                            double tempKelvin = main.get("temp").getAsDouble();
                            double tempCelsius = tempKelvin - 273.15;
                            double tempFahrenheit = (tempCelsius * 1.8) + 32;

                            if(celsius == true){
                                temp = Integer.toString((int) Math.round(tempCelsius));
                            }else{
                                temp = Integer.toString((int) Math.round(tempFahrenheit));
                            }

                            text_tmp.setText(temp + "°");
                            String hum = Integer.toString((int) Math.round(main.get("humidity").getAsDouble()));
                            String windspeed = Integer.toString((int) Math.round(wind.get("speed").getAsDouble()));
                            text_humi.setText(hum+"%");
                            text_vent.setText(windspeed+" M/S");

                            JsonObject sys = result.get("sys").getAsJsonObject();

                            String city = result.get("name").getAsString();
                            String country = sys.get("country").getAsString();
                            text_loc.setText(city + ", " + country);
                            text_loc.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location, 0);

                            JsonArray weather = result.get("weather").getAsJsonArray();

                            String description = weather.get(0).getAsJsonObject().get("description").getAsString();
                            text_desc.setText(description);
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon);
                            RestOfTheWeekWeather(lati, longi);
                        }
                    }
                });
    }



    /*Méthode de récupération de la météo de la semaine pour une ville donnée*/
    private void RestOfTheWeekWeather(String lat,String lon){
        String apiUrl = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly,minutely,current&units=metric&appid=" + API_KEY;
        //String apiUrl = "https://api.openweathermap.org/data/2.5/onecall?appid=" + API_KEY + "&exclude=hourly,minutely,current&units=metric&q=" + ville;
        Ion.with(this)
                .load(apiUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>(){
                    @Override
                    public void onCompleted(Exception e, JsonObject result){
                        // On contrôle qu'il n'y ai pas d'erreurs
                        if (e != null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        } else {
                            List<Weather> weatherList = new ArrayList<>();
                            String timeZone = result.get("timezone").getAsString();
                            JsonArray daily = result.get("daily").getAsJsonArray();
                            for(int i = 1; i < daily.size(); i++){
                                Long date = daily.get(i).getAsJsonObject().get("dt").getAsLong();
                                //double tempFahrenheit = (tempCelsius * 1.8) + 32;
                                int t = (int) Math.round(daily.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("day").getAsDouble());
                                String temp;
                                if(celsius == false){
                                    t = (int)(t * 1.8 + 32);
                                }
                                temp = Integer.toString(t);
                                String icon = daily.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                                weatherList.add(new Weather(date, timeZone, temp, icon));
                            }

                            // On attache l'adapter à la liste des prévisions
                            DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(MainActivity.this, weatherList);
                            previsions.setAdapter(dailyWeatherAdapter);
                        }
                    }
                });
    }


    /*Méthode de récupération de l'icône du ciel*/
    private void loadIcon(String icon){
        Ion.with(this)
                .load("http://openweathermap.org/img/w/" + icon + ".png")
                .intoImageView(iconWeather);
    }



    /*Méthode de récupération de la latitude*/
    public static String getLat(){
        return lat;
    }

    /*Méthode de récupération de la longitude*/
    public static String getLon(){
        return lon;
    }

    /*Méthode exécutant l'accès au layout des paramètres*/
    private void goParametres(){
        actualiser();
        intent = new Intent(getApplicationContext(), ParamActivity.class);
        intent.putExtra("degre", this.celsius);
        intent.putExtra("langue", this.indice_langue);
        intent.putExtra("theme", this.theme_sombre);
        intent.putExtra("accord", this.accord_geo);
        startActivity(intent);
    }

    /*Méthode exécutant l'accès au layout de la carte*/
    private void goCarte(){
        actualiser();
        intent = new Intent(getApplicationContext(), CarteFrance.class);
        intent.putExtra("degre", this.celsius);
        intent.putExtra("langue", this.indice_langue);
        intent.putExtra("theme", this.theme_sombre);
        intent.putExtra("accord", this.accord_geo);
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
            btn_param.setTextColor(Color.parseColor("#FFFFFF"));
            btn_param.setBackgroundColor(Color.parseColor("#1880D1"));
            btn_carte.setTextColor(Color.parseColor("#FFFFFF"));
            btn_carte.setBackgroundColor(Color.parseColor("#1880D1"));
            findViewById(R.id.main_l).setBackgroundColor(Color.parseColor("#283B62"));
        }else{
            btn_param.setTextColor(Color.parseColor("#000000"));
            btn_param.setBackgroundColor(Color.parseColor("#A3E3F3"));
            btn_carte.setTextColor(Color.parseColor("#000000"));
            btn_carte.setBackgroundColor(Color.parseColor("#A3E3F3"));
            findViewById(R.id.main_l).setBackgroundColor(Color.parseColor("#D0EFF6"));
        }
    }

}