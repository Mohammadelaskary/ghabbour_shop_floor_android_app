// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddDefectsOnlineInspectionPaintingBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final MaterialButton addDefects;

  @NonNull
  public final TextView childesc;

  @NonNull
  public final TextInputLayout defectedQtyEdt;

  @NonNull
  public final LinearLayout defectsListLayout;

  @NonNull
  public final RecyclerView defectsSelectList;

  @NonNull
  public final CheckBox isRejected;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final ImageView listDownArrow;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextInputLayout sampleQtyEdt;

  @NonNull
  public final TextView title;

  private FragmentAddDefectsOnlineInspectionPaintingBinding(@NonNull ScrollView rootView,
      @NonNull MaterialButton addDefects, @NonNull TextView childesc,
      @NonNull TextInputLayout defectedQtyEdt, @NonNull LinearLayout defectsListLayout,
      @NonNull RecyclerView defectsSelectList, @NonNull CheckBox isRejected,
      @NonNull JobOrderDataBinding jobOrderData, @NonNull ImageView listDownArrow,
      @NonNull TextView operation, @NonNull TextInputLayout sampleQtyEdt, @NonNull TextView title) {
    this.rootView = rootView;
    this.addDefects = addDefects;
    this.childesc = childesc;
    this.defectedQtyEdt = defectedQtyEdt;
    this.defectsListLayout = defectsListLayout;
    this.defectsSelectList = defectsSelectList;
    this.isRejected = isRejected;
    this.jobOrderData = jobOrderData;
    this.listDownArrow = listDownArrow;
    this.operation = operation;
    this.sampleQtyEdt = sampleQtyEdt;
    this.title = title;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddDefectsOnlineInspectionPaintingBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddDefectsOnlineInspectionPaintingBinding inflate(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_add_defects_online_inspection_painting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddDefectsOnlineInspectionPaintingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_defects;
      MaterialButton addDefects = ViewBindings.findChildViewById(rootView, id);
      if (addDefects == null) {
        break missingId;
      }

      id = R.id.childesc;
      TextView childesc = ViewBindings.findChildViewById(rootView, id);
      if (childesc == null) {
        break missingId;
      }

      id = R.id.defected_qty_edt;
      TextInputLayout defectedQtyEdt = ViewBindings.findChildViewById(rootView, id);
      if (defectedQtyEdt == null) {
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

      id = R.id.is_rejected;
      CheckBox isRejected = ViewBindings.findChildViewById(rootView, id);
      if (isRejected == null) {
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

      id = R.id.sample_qty_edt;
      TextInputLayout sampleQtyEdt = ViewBindings.findChildViewById(rootView, id);
      if (sampleQtyEdt == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      return new FragmentAddDefectsOnlineInspectionPaintingBinding((ScrollView) rootView,
          addDefects, childesc, defectedQtyEdt, defectsListLayout, defectsSelectList, isRejected,
          binding_jobOrderData, listDownArrow, operation, sampleQtyEdt, title);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
