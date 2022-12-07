// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class StoppageItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView stoppageLocation;

  @NonNull
  public final TextView stoppageName;

  @NonNull
  public final TextView stoppageStartDateTime;

  private StoppageItemBinding(@NonNull CardView rootView, @NonNull TextView stoppageLocation,
      @NonNull TextView stoppageName, @NonNull TextView stoppageStartDateTime) {
    this.rootView = rootView;
    this.stoppageLocation = stoppageLocation;
    this.stoppageName = stoppageName;
    this.stoppageStartDateTime = stoppageStartDateTime;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static StoppageItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StoppageItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.stoppage_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StoppageItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.stoppage_location;
      TextView stoppageLocation = ViewBindings.findChildViewById(rootView, id);
      if (stoppageLocation == null) {
        break missingId;
      }

      id = R.id.stoppage_name;
      TextView stoppageName = ViewBindings.findChildViewById(rootView, id);
      if (stoppageName == null) {
        break missingId;
      }

      id = R.id.stoppage_start_date_time;
      TextView stoppageStartDateTime = ViewBindings.findChildViewById(rootView, id);
      if (stoppageStartDateTime == null) {
        break missingId;
      }

      return new StoppageItemBinding((CardView) rootView, stoppageLocation, stoppageName,
          stoppageStartDateTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
