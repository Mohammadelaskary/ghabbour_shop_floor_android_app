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
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProductionSequenceBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView defectqtnRv;

  @NonNull
  public final MaterialButton firstloadingBtn;

  @NonNull
  public final TextInputLayout jobOrderName;

  @NonNull
  public final TextView productionseqTxt;

  private FragmentProductionSequenceBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView defectqtnRv, @NonNull MaterialButton firstloadingBtn,
      @NonNull TextInputLayout jobOrderName, @NonNull TextView productionseqTxt) {
    this.rootView = rootView;
    this.defectqtnRv = defectqtnRv;
    this.firstloadingBtn = firstloadingBtn;
    this.jobOrderName = jobOrderName;
    this.productionseqTxt = productionseqTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProductionSequenceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProductionSequenceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_production_sequence, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProductionSequenceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.defectqtn_rv;
      RecyclerView defectqtnRv = ViewBindings.findChildViewById(rootView, id);
      if (defectqtnRv == null) {
        break missingId;
      }

      id = R.id.firstloading_btn;
      MaterialButton firstloadingBtn = ViewBindings.findChildViewById(rootView, id);
      if (firstloadingBtn == null) {
        break missingId;
      }

      id = R.id.job_order_name;
      TextInputLayout jobOrderName = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderName == null) {
        break missingId;
      }

      id = R.id.productionseq_txt;
      TextView productionseqTxt = ViewBindings.findChildViewById(rootView, id);
      if (productionseqTxt == null) {
        break missingId;
      }

      return new FragmentProductionSequenceBinding((ConstraintLayout) rootView, defectqtnRv,
          firstloadingBtn, jobOrderName, productionseqTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
