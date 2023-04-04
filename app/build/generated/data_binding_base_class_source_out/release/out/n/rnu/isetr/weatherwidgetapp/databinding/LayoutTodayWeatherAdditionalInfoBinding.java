// Generated by view binder compiler. Do not edit!
package n.rnu.isetr.weatherwidgetapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import n.rnu.isetr.weatherwidgetapp.R;

public final class LayoutTodayWeatherAdditionalInfoBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Guideline guidelineStart;

  @NonNull
  public final TextView textHumi;

  @NonNull
  public final TextView textViewHumidityLabel;

  @NonNull
  public final TextView textViewWindLabel;

  @NonNull
  public final TextView windspeed;

  private LayoutTodayWeatherAdditionalInfoBinding(@NonNull CardView rootView,
      @NonNull Guideline guidelineStart, @NonNull TextView textHumi,
      @NonNull TextView textViewHumidityLabel, @NonNull TextView textViewWindLabel,
      @NonNull TextView windspeed) {
    this.rootView = rootView;
    this.guidelineStart = guidelineStart;
    this.textHumi = textHumi;
    this.textViewHumidityLabel = textViewHumidityLabel;
    this.textViewWindLabel = textViewWindLabel;
    this.windspeed = windspeed;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutTodayWeatherAdditionalInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutTodayWeatherAdditionalInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_today_weather_additional_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutTodayWeatherAdditionalInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.guidelineStart;
      Guideline guidelineStart = ViewBindings.findChildViewById(rootView, id);
      if (guidelineStart == null) {
        break missingId;
      }

      id = R.id.text_humi;
      TextView textHumi = ViewBindings.findChildViewById(rootView, id);
      if (textHumi == null) {
        break missingId;
      }

      id = R.id.textViewHumidityLabel;
      TextView textViewHumidityLabel = ViewBindings.findChildViewById(rootView, id);
      if (textViewHumidityLabel == null) {
        break missingId;
      }

      id = R.id.textViewWindLabel;
      TextView textViewWindLabel = ViewBindings.findChildViewById(rootView, id);
      if (textViewWindLabel == null) {
        break missingId;
      }

      id = R.id.windspeed;
      TextView windspeed = ViewBindings.findChildViewById(rootView, id);
      if (windspeed == null) {
        break missingId;
      }

      return new LayoutTodayWeatherAdditionalInfoBinding((CardView) rootView, guidelineStart,
          textHumi, textViewHumidityLabel, textViewWindLabel, windspeed);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}