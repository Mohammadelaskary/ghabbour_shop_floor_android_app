// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentStoppagesMainMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton addStoppage;

  @NonNull
  public final MaterialButton machineStop;

  @NonNull
  public final MaterialButton viewStoppages;

  private FragmentStoppagesMainMenuBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton addStoppage, @NonNull MaterialButton machineStop,
      @NonNull MaterialButton viewStoppages) {
    this.rootView = rootView;
    this.addStoppage = addStoppage;
    this.machineStop = machineStop;
    this.viewStoppages = viewStoppages;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentStoppagesMainMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentStoppagesMainMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_stoppages_main_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentStoppagesMainMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_stoppage;
      MaterialButton addStoppage = ViewBindings.findChildViewById(rootView, id);
      if (addStoppage == null) {
        break missingId;
      }

      id = R.id.machine_stop;
      MaterialButton machineStop = ViewBindings.findChildViewById(rootView, id);
      if (machineStop == null) {
        break missingId;
      }

      id = R.id.view_stoppages;
      MaterialButton viewStoppages = ViewBindings.findChildViewById(rootView, id);
      if (viewStoppages == null) {
        break missingId;
      }

      return new FragmentStoppagesMainMenuBinding((ConstraintLayout) rootView, addStoppage,
          machineStop, viewStoppages);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
