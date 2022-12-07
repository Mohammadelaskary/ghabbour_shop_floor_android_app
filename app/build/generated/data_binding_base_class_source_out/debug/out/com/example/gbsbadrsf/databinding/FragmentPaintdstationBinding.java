// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPaintdstationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText barcodeEdt;

  @NonNull
  public final TextInputLayout jobOrderName;

  @NonNull
  public final NestedScrollView nested;

  @NonNull
  public final TextView noPprList;

  @NonNull
  public final RecyclerView pprList;

  @NonNull
  public final MaterialButton qtnokBtn;

  @NonNull
  public final TextView weldingseqTxt;

  private FragmentPaintdstationBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText barcodeEdt, @NonNull TextInputLayout jobOrderName,
      @NonNull NestedScrollView nested, @NonNull TextView noPprList, @NonNull RecyclerView pprList,
      @NonNull MaterialButton qtnokBtn, @NonNull TextView weldingseqTxt) {
    this.rootView = rootView;
    this.barcodeEdt = barcodeEdt;
    this.jobOrderName = jobOrderName;
    this.nested = nested;
    this.noPprList = noPprList;
    this.pprList = pprList;
    this.qtnokBtn = qtnokBtn;
    this.weldingseqTxt = weldingseqTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintdstationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintdstationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paintdstation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintdstationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.barcode_edt;
      EditText barcodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (barcodeEdt == null) {
        break missingId;
      }

      id = R.id.job_order_name;
      TextInputLayout jobOrderName = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderName == null) {
        break missingId;
      }

      id = R.id.nested;
      NestedScrollView nested = ViewBindings.findChildViewById(rootView, id);
      if (nested == null) {
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

      id = R.id.qtnok_btn;
      MaterialButton qtnokBtn = ViewBindings.findChildViewById(rootView, id);
      if (qtnokBtn == null) {
        break missingId;
      }

      id = R.id.weldingseq_txt;
      TextView weldingseqTxt = ViewBindings.findChildViewById(rootView, id);
      if (weldingseqTxt == null) {
        break missingId;
      }

      return new FragmentPaintdstationBinding((ConstraintLayout) rootView, barcodeEdt, jobOrderName,
          nested, noPprList, pprList, qtnokBtn, weldingseqTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
