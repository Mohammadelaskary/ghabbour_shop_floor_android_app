// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPaintQualityOperationBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final MaterialButton addDefects;

  @NonNull
  public final TextView adddefectTxt;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final TextInputLayout defecedQty;

  @NonNull
  public final ConstraintLayout defectsListLayout;

  @NonNull
  public final RecyclerView defectsPerQty;

  @NonNull
  public final SwitchMaterial fullInspectionSwitch;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final NestedScrollView layout;

  @NonNull
  public final DefectsPerQtyTitleBinding manufacturingDefectsPerQtyTitle;

  @NonNull
  public final TextView noConnection;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final ContentLoadingProgressBar progressBar;

  @NonNull
  public final MaterialButton qualityOk;

  @NonNull
  public final MaterialButton qualityPass;

  @NonNull
  public final TextInputLayout rejectedQty;

  @NonNull
  public final TextInputLayout sampleQtnEdt;

  @NonNull
  public final MaterialButton signOffBaskets;

  @NonNull
  public final SignOffQtyBinding signOffData;

  private FragmentPaintQualityOperationBinding(@NonNull ScrollView rootView,
      @NonNull MaterialButton addDefects, @NonNull TextView adddefectTxt,
      @NonNull ConstraintLayout dataLayout, @NonNull TextInputLayout defecedQty,
      @NonNull ConstraintLayout defectsListLayout, @NonNull RecyclerView defectsPerQty,
      @NonNull SwitchMaterial fullInspectionSwitch, @NonNull JobOrderDataBinding jobOrderData,
      @NonNull NestedScrollView layout,
      @NonNull DefectsPerQtyTitleBinding manufacturingDefectsPerQtyTitle,
      @NonNull TextView noConnection, @NonNull TextView operation, @NonNull TextView parentDesc,
      @NonNull ContentLoadingProgressBar progressBar, @NonNull MaterialButton qualityOk,
      @NonNull MaterialButton qualityPass, @NonNull TextInputLayout rejectedQty,
      @NonNull TextInputLayout sampleQtnEdt, @NonNull MaterialButton signOffBaskets,
      @NonNull SignOffQtyBinding signOffData) {
    this.rootView = rootView;
    this.addDefects = addDefects;
    this.adddefectTxt = adddefectTxt;
    this.dataLayout = dataLayout;
    this.defecedQty = defecedQty;
    this.defectsListLayout = defectsListLayout;
    this.defectsPerQty = defectsPerQty;
    this.fullInspectionSwitch = fullInspectionSwitch;
    this.jobOrderData = jobOrderData;
    this.layout = layout;
    this.manufacturingDefectsPerQtyTitle = manufacturingDefectsPerQtyTitle;
    this.noConnection = noConnection;
    this.operation = operation;
    this.parentDesc = parentDesc;
    this.progressBar = progressBar;
    this.qualityOk = qualityOk;
    this.qualityPass = qualityPass;
    this.rejectedQty = rejectedQty;
    this.sampleQtnEdt = sampleQtnEdt;
    this.signOffBaskets = signOffBaskets;
    this.signOffData = signOffData;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintQualityOperationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintQualityOperationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paint_quality_operation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintQualityOperationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_defects;
      MaterialButton addDefects = ViewBindings.findChildViewById(rootView, id);
      if (addDefects == null) {
        break missingId;
      }

      id = R.id.adddefect_txt;
      TextView adddefectTxt = ViewBindings.findChildViewById(rootView, id);
      if (adddefectTxt == null) {
        break missingId;
      }

      id = R.id.data_layout;
      ConstraintLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.defeced_qty;
      TextInputLayout defecedQty = ViewBindings.findChildViewById(rootView, id);
      if (defecedQty == null) {
        break missingId;
      }

      id = R.id.defects_list_layout;
      ConstraintLayout defectsListLayout = ViewBindings.findChildViewById(rootView, id);
      if (defectsListLayout == null) {
        break missingId;
      }

      id = R.id.defects_per_qty;
      RecyclerView defectsPerQty = ViewBindings.findChildViewById(rootView, id);
      if (defectsPerQty == null) {
        break missingId;
      }

      id = R.id.full_inspection_switch;
      SwitchMaterial fullInspectionSwitch = ViewBindings.findChildViewById(rootView, id);
      if (fullInspectionSwitch == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.layout;
      NestedScrollView layout = ViewBindings.findChildViewById(rootView, id);
      if (layout == null) {
        break missingId;
      }

      id = R.id.manufacturing_defects_per_qty_title;
      View manufacturingDefectsPerQtyTitle = ViewBindings.findChildViewById(rootView, id);
      if (manufacturingDefectsPerQtyTitle == null) {
        break missingId;
      }
      DefectsPerQtyTitleBinding binding_manufacturingDefectsPerQtyTitle = DefectsPerQtyTitleBinding.bind(manufacturingDefectsPerQtyTitle);

      id = R.id.no_connection;
      TextView noConnection = ViewBindings.findChildViewById(rootView, id);
      if (noConnection == null) {
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

      id = R.id.progress_bar;
      ContentLoadingProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.quality_ok;
      MaterialButton qualityOk = ViewBindings.findChildViewById(rootView, id);
      if (qualityOk == null) {
        break missingId;
      }

      id = R.id.quality_pass;
      MaterialButton qualityPass = ViewBindings.findChildViewById(rootView, id);
      if (qualityPass == null) {
        break missingId;
      }

      id = R.id.rejected_qty;
      TextInputLayout rejectedQty = ViewBindings.findChildViewById(rootView, id);
      if (rejectedQty == null) {
        break missingId;
      }

      id = R.id.sample_qtn_edt;
      TextInputLayout sampleQtnEdt = ViewBindings.findChildViewById(rootView, id);
      if (sampleQtnEdt == null) {
        break missingId;
      }

      id = R.id.sign_off_baskets;
      MaterialButton signOffBaskets = ViewBindings.findChildViewById(rootView, id);
      if (signOffBaskets == null) {
        break missingId;
      }

      id = R.id.sign_off_data;
      View signOffData = ViewBindings.findChildViewById(rootView, id);
      if (signOffData == null) {
        break missingId;
      }
      SignOffQtyBinding binding_signOffData = SignOffQtyBinding.bind(signOffData);

      return new FragmentPaintQualityOperationBinding((ScrollView) rootView, addDefects,
          adddefectTxt, dataLayout, defecedQty, defectsListLayout, defectsPerQty,
          fullInspectionSwitch, binding_jobOrderData, layout,
          binding_manufacturingDefectsPerQtyTitle, noConnection, operation, parentDesc, progressBar,
          qualityOk, qualityPass, rejectedQty, sampleQtnEdt, signOffBaskets, binding_signOffData);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
