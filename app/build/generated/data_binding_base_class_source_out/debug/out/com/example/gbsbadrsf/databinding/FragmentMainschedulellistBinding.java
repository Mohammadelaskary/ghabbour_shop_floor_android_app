// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMainschedulellistBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView importRv;

  @NonNull
  public final AppBarLayout mainschedulelstAppbar;

  private FragmentMainschedulellistBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView importRv, @NonNull AppBarLayout mainschedulelstAppbar) {
    this.rootView = rootView;
    this.importRv = importRv;
    this.mainschedulelstAppbar = mainschedulelstAppbar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMainschedulellistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMainschedulellistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_mainschedulellist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMainschedulellistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.import_rv;
      RecyclerView importRv = ViewBindings.findChildViewById(rootView, id);
      if (importRv == null) {
        break missingId;
      }

      id = R.id.mainschedulelst_appbar;
      AppBarLayout mainschedulelstAppbar = ViewBindings.findChildViewById(rootView, id);
      if (mainschedulelstAppbar == null) {
        break missingId;
      }

      return new FragmentMainschedulellistBinding((ConstraintLayout) rootView, importRv,
          mainschedulelstAppbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
