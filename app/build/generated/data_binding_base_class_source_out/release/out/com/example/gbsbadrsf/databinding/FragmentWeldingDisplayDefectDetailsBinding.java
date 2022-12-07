// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentWeldingDisplayDefectDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextInputLayout defectedQty;

  @NonNull
  public final LinearLayout defectsListLayout;

  @NonNull
  public final RecyclerView defectsSelectList;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final ImageView listDownArrow;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final TextInputLayout sampleQty;

  @NonNull
  public final TextView title;

  private FragmentWeldingDisplayDefectDetailsBinding(@NonNull LinearLayout rootView,
      @NonNull TextInputLayout defectedQty, @NonNull LinearLayout defectsListLayout,
      @NonNull RecyclerView defectsSelectList, @NonNull JobOrderDataBinding jobOrderData,
      @NonNull ImageView listDownArrow, @NonNull TextView operation, @NonNull TextView parentDesc,
      @NonNull TextInputLayout sampleQty, @NonNull TextView title) {
    this.rootView = rootView;
    this.defectedQty = defectedQty;
    this.defectsListLayout = defectsListLayout;
    this.defectsSelectList = defectsSelectList;
    this.jobOrderData = jobOrderData;
    this.listDownArrow = listDownArrow;
    this.operation = operation;
    this.parentDesc = parentDesc;
    this.sampleQty = sampleQty;
    this.title = title;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentWeldingDisplayDefectDetailsBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentWeldingDisplayDefectDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_welding_display_defect_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentWeldingDisplayDefectDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.defected_qty;
      TextInputLayout defectedQty = ViewBindings.findChildViewById(rootView, id);
      if (defectedQty == null) {
        break missingId;
      }

      id = R.id.defects_list_layout;
      LinearLayout defectsListLayout = ViewBindings.findChildViewById(rootView, id);
      if (defectsListLayout == null) {
        break missingId;
      }

      id = R.id.defects_select_list;
      RecyclerView defectsSelectList = ViewBindings.findChildViewById(rootView, id);
      if (defectsSelectList == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.list_down_arrow;
      ImageView listDownArrow = ViewBindings.findChildViewById(rootView, id);
      if (listDownArrow == null) {
        break missingId;
      }

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

      id = R.id.sample_qty;
      TextInputLayout sampleQty = ViewBindings.findChildViewById(rootView, id);
      if (sampleQty == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      return new FragmentWeldingDisplayDefectDetailsBinding((LinearLayout) rootView, defectedQty,
          defectsListLayout, defectsSelectList, binding_jobOrderData, listDownArrow, operation,
          parentDesc, sampleQty, title);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
