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
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentWeldingSequenceBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton firstloadingBtn;

  @NonNull
  public final TextView noPprList;

  @NonNull
  public final RecyclerView pprList;

  @NonNull
  public final TextView productionseqTxt;

  private FragmentWeldingSequenceBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton firstloadingBtn, @NonNull TextView noPprList,
      @NonNull RecyclerView pprList, @NonNull TextView productionseqTxt) {
    this.rootView = rootView;
    this.firstloadingBtn = firstloadingBtn;
    this.noPprList = noPprList;
    this.pprList = pprList;
    this.productionseqTxt = productionseqTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentWeldingSequenceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentWeldingSequenceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_welding_sequence, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentWeldingSequenceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.firstloading_btn;
      MaterialButton firstloadingBtn = ViewBindings.findChildViewById(rootView, id);
      if (firstloadingBtn == null) {
        break missingId;
      }

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

      id = R.id.productionseq_txt;
      TextView productionseqTxt = ViewBindings.findChildViewById(rootView, id);
      if (productionseqTxt == null) {
        break missingId;
      }

      return new FragmentWeldingSequenceBinding((ConstraintLayout) rootView, firstloadingBtn,
          noPprList, pprList, productionseqTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}