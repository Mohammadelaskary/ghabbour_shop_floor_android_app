// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentManufacturingAddDefectsBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView adddefectTxt;

  @NonNull
  public final TextInputLayout basketCode;

  @NonNull
  public final LinearLayout constraint;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final TextView defectedDetailsTxt;

  @NonNull
  public final TextInputLayout defectedQtyEdt;

  @NonNull
  public final TextView defectnameTxt;

  @NonNull
  public final RecyclerView defectsList;

  @NonNull
  public final TextView hint;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final ImageView plusIcon;

  @NonNull
  public final TextInputLayout sampleQtyEdt;

  @NonNull
  public final TextView sampleqtyTxt;

  private FragmentManufacturingAddDefectsBinding(@NonNull ScrollView rootView,
      @NonNull TextView adddefectTxt, @NonNull TextInputLayout basketCode,
      @NonNull LinearLayout constraint, @NonNull ConstraintLayout dataLayout,
      @NonNull TextView defectedDetailsTxt, @NonNull TextInputLayout defectedQtyEdt,
      @NonNull TextView defectnameTxt, @NonNull RecyclerView defectsList, @NonNull TextView hint,
      @NonNull JobOrderDataBinding jobOrderData, @NonNull TextView operation,
      @NonNull TextView parentDesc, @NonNull ImageView plusIcon,
      @NonNull TextInputLayout sampleQtyEdt, @NonNull TextView sampleqtyTxt) {
    this.rootView = rootView;
    this.adddefectTxt = adddefectTxt;
    this.basketCode = basketCode;
    this.constraint = constraint;
    this.dataLayout = dataLayout;
    this.defectedDetailsTxt = defectedDetailsTxt;
    this.defectedQtyEdt = defectedQtyEdt;
    this.defectnameTxt = defectnameTxt;
    this.defectsList = defectsList;
    this.hint = hint;
    this.jobOrderData = jobOrderData;
    this.operation = operation;
    this.parentDesc = parentDesc;
    this.plusIcon = plusIcon;
    this.sampleQtyEdt = sampleQtyEdt;
    this.sampleqtyTxt = sampleqtyTxt;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentManufacturingAddDefectsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentManufacturingAddDefectsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_manufacturing_add_defects, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentManufacturingAddDefectsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adddefect_txt;
      TextView adddefectTxt = ViewBindings.findChildViewById(rootView, id);
      if (adddefectTxt == null) {
        break missingId;
      }

      id = R.id.basket_code;
      TextInputLayout basketCode = ViewBindings.findChildViewById(rootView, id);
      if (basketCode == null) {
        break missingId;
      }

      id = R.id.constraint;
      LinearLayout constraint = ViewBindings.findChildViewById(rootView, id);
      if (constraint == null) {
        break missingId;
      }

      id = R.id.data_layout;
      ConstraintLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.defected_details_txt;
      TextView defectedDetailsTxt = ViewBindings.findChildViewById(rootView, id);
      if (defectedDetailsTxt == null) {
        break missingId;
      }

      id = R.id.defected_qty_edt;
      TextInputLayout defectedQtyEdt = ViewBindings.findChildViewById(rootView, id);
      if (defectedQtyEdt == null) {
        break missingId;
      }

      id = R.id.defectname_txt;
      TextView defectnameTxt = ViewBindings.findChildViewById(rootView, id);
      if (defectnameTxt == null) {
        break missingId;
      }

      id = R.id.defects_list;
      RecyclerView defectsList = ViewBindings.findChildViewById(rootView, id);
      if (defectsList == null) {
        break missingId;
      }

      id = R.id.hint;
      TextView hint = ViewBindings.findChildViewById(rootView, id);
      if (hint == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.operation;
      TextView operation = ViewBindings.findChildViewById(rootView, id);
      if (operation == null) {
        break missingId;
      }

      id = R.id.parent_desc;
      TextView parentDesc = ViewBindings.findChildViewById(rootView, id);
      if (parentDesc == null) {
        break missingId;
      }

      id = R.id.plus_icon;
      ImageView plusIcon = ViewBindings.findChildViewById(rootView, id);
      if (plusIcon == null) {
        break missingId;
      }

      id = R.id.sample_qty_edt;
      TextInputLayout sampleQtyEdt = ViewBindings.findChildViewById(rootView, id);
      if (sampleQtyEdt == null) {
        break missingId;
      }

      id = R.id.sampleqty_txt;
      TextView sampleqtyTxt = ViewBindings.findChildViewById(rootView, id);
      if (sampleqtyTxt == null) {
        break missingId;
      }

      return new FragmentManufacturingAddDefectsBinding((ScrollView) rootView, adddefectTxt,
          basketCode, constraint, dataLayout, defectedDetailsTxt, defectedQtyEdt, defectnameTxt,
          defectsList, hint, binding_jobOrderData, operation, parentDesc, plusIcon, sampleQtyEdt,
          sampleqtyTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}