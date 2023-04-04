// Generated by view binder compiler. Do not edit!
package n.rnu.isetr.weatherwidgetapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import n.rnu.isetr.weatherwidgetapp.R;

public final class ActivityCarteFranceBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView Centre;

  @NonNull
  public final ImageView Corse;

  @NonNull
  public final ImageView GrandCentre;

  @NonNull
  public final ImageView GrandEst;

  @NonNull
  public final ImageView GrandNord;

  @NonNull
  public final ImageView GrandOuest;

  @NonNull
  public final ImageView GrandSudEst;

  @NonNull
  public final ImageView GrandSudOuest;

  @NonNull
  public final ImageView OutreMer;

  @NonNull
  public final ImageButton actualiser;

  @NonNull
  public final ImageView carteRegions;

  @NonNull
  public final ImageButton ciel;

  @NonNull
  public final RelativeLayout layoutMap;

  @NonNull
  public final ImageButton retour;

  @NonNull
  public final TextView tempCentre;

  @NonNull
  public final TextView tempCorse;

  @NonNull
  public final TextView tempGrandCentre;

  @NonNull
  public final TextView tempGrandEst;

  @NonNull
  public final TextView tempGrandNord;

  @NonNull
  public final TextView tempGrandOuest;

  @NonNull
  public final TextView tempGrandSudEst;

  @NonNull
  public final TextView tempGrandSudOuest;

  @NonNull
  public final TextView tempOutreMer;

  @NonNull
  public final ImageButton temperature;

  private ActivityCarteFranceBinding(@NonNull RelativeLayout rootView, @NonNull ImageView Centre,
      @NonNull ImageView Corse, @NonNull ImageView GrandCentre, @NonNull ImageView GrandEst,
      @NonNull ImageView GrandNord, @NonNull ImageView GrandOuest, @NonNull ImageView GrandSudEst,
      @NonNull ImageView GrandSudOuest, @NonNull ImageView OutreMer,
      @NonNull ImageButton actualiser, @NonNull ImageView carteRegions, @NonNull ImageButton ciel,
      @NonNull RelativeLayout layoutMap, @NonNull ImageButton retour, @NonNull TextView tempCentre,
      @NonNull TextView tempCorse, @NonNull TextView tempGrandCentre,
      @NonNull TextView tempGrandEst, @NonNull TextView tempGrandNord,
      @NonNull TextView tempGrandOuest, @NonNull TextView tempGrandSudEst,
      @NonNull TextView tempGrandSudOuest, @NonNull TextView tempOutreMer,
      @NonNull ImageButton temperature) {
    this.rootView = rootView;
    this.Centre = Centre;
    this.Corse = Corse;
    this.GrandCentre = GrandCentre;
    this.GrandEst = GrandEst;
    this.GrandNord = GrandNord;
    this.GrandOuest = GrandOuest;
    this.GrandSudEst = GrandSudEst;
    this.GrandSudOuest = GrandSudOuest;
    this.OutreMer = OutreMer;
    this.actualiser = actualiser;
    this.carteRegions = carteRegions;
    this.ciel = ciel;
    this.layoutMap = layoutMap;
    this.retour = retour;
    this.tempCentre = tempCentre;
    this.tempCorse = tempCorse;
    this.tempGrandCentre = tempGrandCentre;
    this.tempGrandEst = tempGrandEst;
    this.tempGrandNord = tempGrandNord;
    this.tempGrandOuest = tempGrandOuest;
    this.tempGrandSudEst = tempGrandSudEst;
    this.tempGrandSudOuest = tempGrandSudOuest;
    this.tempOutreMer = tempOutreMer;
    this.temperature = temperature;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCarteFranceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCarteFranceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_carte_france, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCarteFranceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Centre;
      ImageView Centre = ViewBindings.findChildViewById(rootView, id);
      if (Centre == null) {
        break missingId;
      }

      id = R.id.Corse;
      ImageView Corse = ViewBindings.findChildViewById(rootView, id);
      if (Corse == null) {
        break missingId;
      }

      id = R.id.GrandCentre;
      ImageView GrandCentre = ViewBindings.findChildViewById(rootView, id);
      if (GrandCentre == null) {
        break missingId;
      }

      id = R.id.GrandEst;
      ImageView GrandEst = ViewBindings.findChildViewById(rootView, id);
      if (GrandEst == null) {
        break missingId;
      }

      id = R.id.GrandNord;
      ImageView GrandNord = ViewBindings.findChildViewById(rootView, id);
      if (GrandNord == null) {
        break missingId;
      }

      id = R.id.GrandOuest;
      ImageView GrandOuest = ViewBindings.findChildViewById(rootView, id);
      if (GrandOuest == null) {
        break missingId;
      }

      id = R.id.GrandSudEst;
      ImageView GrandSudEst = ViewBindings.findChildViewById(rootView, id);
      if (GrandSudEst == null) {
        break missingId;
      }

      id = R.id.GrandSudOuest;
      ImageView GrandSudOuest = ViewBindings.findChildViewById(rootView, id);
      if (GrandSudOuest == null) {
        break missingId;
      }

      id = R.id.OutreMer;
      ImageView OutreMer = ViewBindings.findChildViewById(rootView, id);
      if (OutreMer == null) {
        break missingId;
      }

      id = R.id.actualiser;
      ImageButton actualiser = ViewBindings.findChildViewById(rootView, id);
      if (actualiser == null) {
        break missingId;
      }

      id = R.id.carte_regions;
      ImageView carteRegions = ViewBindings.findChildViewById(rootView, id);
      if (carteRegions == null) {
        break missingId;
      }

      id = R.id.ciel;
      ImageButton ciel = ViewBindings.findChildViewById(rootView, id);
      if (ciel == null) {
        break missingId;
      }

      RelativeLayout layoutMap = (RelativeLayout) rootView;

      id = R.id.retour;
      ImageButton retour = ViewBindings.findChildViewById(rootView, id);
      if (retour == null) {
        break missingId;
      }

      id = R.id.tempCentre;
      TextView tempCentre = ViewBindings.findChildViewById(rootView, id);
      if (tempCentre == null) {
        break missingId;
      }

      id = R.id.tempCorse;
      TextView tempCorse = ViewBindings.findChildViewById(rootView, id);
      if (tempCorse == null) {
        break missingId;
      }

      id = R.id.tempGrandCentre;
      TextView tempGrandCentre = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandCentre == null) {
        break missingId;
      }

      id = R.id.tempGrandEst;
      TextView tempGrandEst = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandEst == null) {
        break missingId;
      }

      id = R.id.tempGrandNord;
      TextView tempGrandNord = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandNord == null) {
        break missingId;
      }

      id = R.id.tempGrandOuest;
      TextView tempGrandOuest = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandOuest == null) {
        break missingId;
      }

      id = R.id.tempGrandSudEst;
      TextView tempGrandSudEst = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandSudEst == null) {
        break missingId;
      }

      id = R.id.tempGrandSudOuest;
      TextView tempGrandSudOuest = ViewBindings.findChildViewById(rootView, id);
      if (tempGrandSudOuest == null) {
        break missingId;
      }

      id = R.id.tempOutreMer;
      TextView tempOutreMer = ViewBindings.findChildViewById(rootView, id);
      if (tempOutreMer == null) {
        break missingId;
      }

      id = R.id.temperature;
      ImageButton temperature = ViewBindings.findChildViewById(rootView, id);
      if (temperature == null) {
        break missingId;
      }

      return new ActivityCarteFranceBinding((RelativeLayout) rootView, Centre, Corse, GrandCentre,
          GrandEst, GrandNord, GrandOuest, GrandSudEst, GrandSudOuest, OutreMer, actualiser,
          carteRegions, ciel, layoutMap, retour, tempCentre, tempCorse, tempGrandCentre,
          tempGrandEst, tempGrandNord, tempGrandOuest, tempGrandSudEst, tempGrandSudOuest,
          tempOutreMer, temperature);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
