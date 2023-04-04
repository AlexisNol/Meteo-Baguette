package n.rnu.isetr.weatherwidgetapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;


public class ParamActivity extends AppCompatActivity implements LocationListener,AdapterView.OnItemSelectedListener {

    /* Indice lié à la langue sélectionnée */
    public int indice_langue = 0;

    /* Objet de la classe Intent que l'on transmettra lors du changement d'activité */
    private Intent intent,intent_recep, refresh;

    /* Liste déroulante des différentes langues en paramètres */
    private Spinner langues;

    /* Tableau de String qui contient les labels des langues proposées en traduction */
    private String[] val_langues = {"Français","Anglais(UK)","Espagnol","Russe","Chinois(Simplifié)"};

    /* Adaptater qui est utilisé pour la correspondance entre la liste déroulante et les labels associés */
    private ArrayAdapter adapter;

    /* Variable qui contient la langue sélectionné par l'utilisateur */
    private String selection;

    /* Déclaration d'un bouton qui confirme le changement de langue */
    private Button change_langue;

    /* Déclaration d'un bouton pour un retour au menu principal */
    private Button retour;

    /* Déclaration d'un Switch pour le changement d'unité de température */
    private Switch unite;

    /* Déclaration d'un Switch pour choisir ou non l'utilisation de la géolocalisation */
    private Switch geoloc;

    /* Déclaration d'un Switch pour la sélection du thème clair ou sombre */
    private Switch mode_affich;

    /* Clé de l'API Open Weather */
    private static final String API_KEY = "df07195ad38e7d3cbb2f448d3aec3285";

    /* Latitude de la position de l'utilisateur */
    private static String lat;

    /* Longitude de la position de l'utilisateur */
    private static String lon;
    android.location.LocationManager locationManager;

    /* Objet qui fera la correspondance avec le layout afin de pouvoir modifier certains attributs */
    private LinearLayout layout_l;

    /* Booléen qui vaut vrai lorsque l'unité de température choisit est en degré celsius */
    public boolean celsius = true;

    /* Booléen qui vaut vrai lorsque l'utilisateur active le mode sombre de l'application */
    public boolean theme_sombre = false;

    /* Booléen qui vaut vrai lorsque l'utilisateur accepte de transmettre la géolocalisation */
    public boolean accord_geo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.param_activity);

        langues = findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, val_langues);

        langues.setAdapter(adapter);
        langues.setOnItemSelectedListener(this);
        change_langue = findViewById(R.id.id_btn_langue);

        unite = findViewById(R.id.switch_unite);
        geoloc = findViewById(R.id.switch_geoloc);
        mode_affich = findViewById(R.id.switch_mode_affich);
        layout_l = findViewById(R.id.layout_param);

        retour = findViewById(R.id.id_btn_menu);

        // Demande d'autorisation pour la géolocalisation
        if (ContextCompat.checkSelfPermission(ParamActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ParamActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getLocation();

        this.change_langue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                refresh = new Intent(getApplicationContext(), ParamActivity.class);
                refresh.putExtra("theme", theme_sombre);
                refresh.putExtra("degre", celsius);
                refresh.putExtra("langue", indice_langue);
                refresh.putExtra("accord", accord_geo);
                startActivity(refresh);

                //recreate();
                //actualiser();
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        this.change_langue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.1f);
                    v.setScaleY(1.1f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // rétrécir l'image
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }
                return false;
            }
        });

        // Affectation d'un comportement ou Switch lié à la géolocalisation
        // Si le bouton est activé on demande la permission
        // Si le bouton est désactivé on suspend la géolocalisation
        this.geoloc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(geoloc.isChecked()){
                    accord_geo = true;
                    goGeoloc();
                }else{
                    accord_geo = false;
                    noGeoloc();
                }
            }
        });

        // Affectation d'un comportement ou Switch lié au thème de l'application
        // Si le bouton est activé on applique des couleurs plutôt claires sur l'affichage
        // Si le bouton est désactivé on applique des couleurs plutôt sombres sur l'affichage
        this.mode_affich.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mode_affich.isChecked()){
                    theme_sombre = true;
                    goSombre(true, mode_affich);
                }else{
                    theme_sombre = false;
                    goClaire(false, mode_affich);
                }
            }
        });

        // Affectation d'un comportement ou Switch lié à l'unité de température
        // Si le bouton est activé on utilise le degré °F
        // Si le bouton est désactivé on utilise le degré °C
        this.unite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(unite.isChecked()){
                    celsius = false;
                    changeColor(true,unite);
                }else{
                    celsius = true;
                    changeColor(false,unite);
                }
            }
        });

        this.retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                actualiser();
                retourMenu();
            }
        });

        /*Méthode de gestion de l'animation de clic*/
        this.retour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // agrandir l'image
                    v.setScaleX(1.1f);
                    v.setScaleY(1.1f);
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

    // Méthode associée à la liste déroulante des langues
    // Sauvegarde la langue choisit par l'utilisateur et affiche un message
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selection = val_langues[i];
        Toast.makeText(this.getApplicationContext(),"Vous avez choisis le paramètre : "+selection, Toast.LENGTH_LONG).show();
        indice_langue = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Méthode qui récupère la localisation de l'utilisateur
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (android.location.LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,5000,5,ParamActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            lat = String.valueOf(location.getLatitude());
            lon= String.valueOf(location.getLongitude());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getLat() {
        return lat;
    }
    public static String getLon() {
        return lon;
    }

    // Méthode qui permet de changer la couleur des boutons de l'écran
    private void changeColor(boolean isChecked, Switch un_switch) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int thumbColor;
            int trackColor;

            if(isChecked) {
                thumbColor = Color.parseColor("#EFD635");
                trackColor = Color.parseColor("#EFD635");
            } else {
                thumbColor = Color.parseColor("#C1BFAE");
                trackColor = Color.parseColor("#283B62");
            }

            try {
                un_switch.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
                un_switch.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void goSombre(boolean isChecked, Switch un_switch){
        theme_sombre = true;
        layout_l.setBackgroundColor(Color.parseColor("#283B62"));
        change_langue.setTextColor(Color.parseColor("#FFFFFF"));
        change_langue.setBackgroundColor(Color.parseColor("#1880D1"));
        retour.setTextColor(Color.parseColor("#FFFFFF"));
        retour.setBackgroundColor(Color.parseColor("#1880D1"));
        findViewById(R.id.id_l_lang).setBackgroundColor(Color.parseColor("#6C9CFA"));
        findViewById(R.id.id_l_unite).setBackgroundColor(Color.parseColor("#6C9CFA"));
        findViewById(R.id.id_l_geo).setBackgroundColor(Color.parseColor("#6C9CFA"));
        findViewById(R.id.id_l_theme).setBackgroundColor(Color.parseColor("#6C9CFA"));
        findViewById(R.id.id_param).setBackgroundColor(Color.parseColor("#113E95"));
        findViewById(R.id.id_langue).setBackgroundColor(Color.parseColor("#113E95"));
        findViewById(R.id.id_unite).setBackgroundColor(Color.parseColor("#113E95"));
        findViewById(R.id.id_accord).setBackgroundColor(Color.parseColor("#113E95"));
        findViewById(R.id.id_mode_sombre).setBackgroundColor(Color.parseColor("#113E95"));
        changeColor(isChecked,un_switch);
    }

    public void goClaire(boolean isChecked, Switch un_switch){
        theme_sombre = false;
        layout_l.setBackgroundColor(Color.parseColor("#FFFFFF"));
        change_langue.setTextColor(Color.parseColor("#000000"));
        change_langue.setBackgroundColor(Color.parseColor("#A3E3F3"));
        retour.setTextColor(Color.parseColor("#000000"));
        retour.setBackgroundColor(Color.parseColor("#A3E3F3"));
        findViewById(R.id.id_l_lang).setBackgroundColor(Color.parseColor("#D0EFF6"));
        findViewById(R.id.id_l_unite).setBackgroundColor(Color.parseColor("#D0EFF6"));
        findViewById(R.id.id_l_geo).setBackgroundColor(Color.parseColor("#D0EFF6"));
        findViewById(R.id.id_l_theme).setBackgroundColor(Color.parseColor("#D0EFF6"));
        findViewById(R.id.id_param).setBackgroundColor(Color.parseColor("#7F8181"));
        findViewById(R.id.id_langue).setBackgroundColor(Color.parseColor("#7F8181"));
        findViewById(R.id.id_unite).setBackgroundColor(Color.parseColor("#7F8181"));
        findViewById(R.id.id_accord).setBackgroundColor(Color.parseColor("#7F8181"));
        findViewById(R.id.id_mode_sombre).setBackgroundColor(Color.parseColor("#7F8181"));
        changeColor(isChecked,un_switch);
    }

    public void goGeoloc(){
        ActivityCompat.requestPermissions(ParamActivity.this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        },100);
        changeColor(true,geoloc);
        Toast.makeText(getApplicationContext(),"Géolocalisation activée", Toast.LENGTH_LONG).show();
    }

    public void noGeoloc(){
        ActivityCompat.requestPermissions(ParamActivity.this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        },0);
        changeColor(false,geoloc);
        Toast.makeText(getApplicationContext(),"Géolocalisation désactivée", Toast.LENGTH_LONG).show();
    }

    /* Méthode qui permet le passer d'une Activité à une autre*/
    private void retourMenu(){
        /* Changer le nom de l'activité .class */
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("theme", theme_sombre);
        intent.putExtra("degre", celsius);
        intent.putExtra("langue", indice_langue);
        intent.putExtra("accord", accord_geo);
        startActivity(intent);
    }

    public void actualiser(){
        if(intent_recep != null){
            this.celsius = intent_recep.getBooleanExtra("degre", true);
            this.theme_sombre = intent_recep.getBooleanExtra("theme", false);
            this.indice_langue = intent_recep.getIntExtra("langue", 0);
            this.accord_geo = intent_recep.getBooleanExtra("accord",false);
        }
        if(this.theme_sombre == true){
            goSombre(true,mode_affich);
            mode_affich.toggle();
        }
        if(this.celsius == false){
            changeColor(true,unite);
            unite.toggle();
        }
        if(this.accord_geo == true){
            changeColor(true,geoloc);
            geoloc.toggle();
        }
    }

}