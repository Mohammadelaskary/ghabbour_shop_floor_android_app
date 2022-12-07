// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPaintQualityOperationPprListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView noPprList;

  @NonNull
  public final RecyclerView pprList;

  @NonNull
  public final TextView signOffTitle;

  private FragmentPaintQualityOperationPprListBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView noPprList, @NonNull RecyclerView pprList, @NonNull TextView signOffTitle) {
    this.rootView = rootView;
    this.noPprList = noPprList;
    this.pprList = pprList;
    this.signOffTitle = signOffTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintQualityOperationPprListBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintQualityOperationPprListBinding inflate(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paint_quality_operation_ppr_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintQualityOperationPprListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.no_ppr_list;
      TextView noPprList = ViewBindings.findChildViewById(rootView, id);
      if (noPprList == null) {
        break missingId;
      }

      id = R.id.ppr_list;
      RecyclerView pprList = ViewBindings.findChildViewById(rootView, id);
      if (pprList == null) {
        break missingId;
      }

      id = R.id.sign_off_title;
      TextView signOffTitle = ViewBindings.findChildViewById(rootView, id);
      if (signOffTitle == null) {
        break missingId;
      }

      return new FragmentPaintQualityOperationPprListBinding((ConstraintLayout) rootView, noPprList,
          pprList, signOffTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
