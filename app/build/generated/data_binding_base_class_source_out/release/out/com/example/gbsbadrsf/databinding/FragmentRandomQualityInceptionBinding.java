// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRandomQualityInceptionBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final MaterialButton addDefects;

  @NonNull
  public final TextView childesc;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final OnlineInspectionAddBasketsLayoutBinding defectedRejectedBaskets;

  @NonNull
  public final RecyclerView defectsPerQty;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final LoadingQtyDataBinding loadingQtyData;

  @NonNull
  public final TextInputLayout machineDieCode;

  @NonNull
  public final DefectsPerQtyTitleBinding manufacturingDefectsPerQtyTitle;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView randomQualityInspectionTxt;

  @NonNull
  public final TextInputLayout sampleQty;

  @NonNull
  public final MaterialButton save;

  private FragmentRandomQualityInceptionBinding(@NonNull CoordinatorLayout rootView,
      @NonNull MaterialButton addDefects, @NonNull TextView childesc,
      @NonNull ConstraintLayout dataLayout,
      @NonNull OnlineInspectionAddBasketsLayoutBinding defectedRejectedBaskets,
      @NonNull RecyclerView defectsPerQty, @NonNull JobOrderDataBinding jobOrderData,
      @NonNull LoadingQtyDataBinding loadingQtyData, @NonNull TextInputLayout machineDieCode,
      @NonNull DefectsPerQtyTitleBinding manufacturingDefectsPerQtyTitle,
      @NonNull TextView operation, @NonNull TextView randomQualityInspectionTxt,
      @NonNull TextInputLayout sampleQty, @NonNull MaterialButton save) {
    this.rootView = rootView;
    this.addDefects = addDefects;
    this.childesc = childesc;
    this.dataLayout = dataLayout;
    this.defectedRejectedBaskets = defectedRejectedBaskets;
    this.defectsPerQty = defectsPerQty;
    this.jobOrderData = jobOrderData;
    this.loadingQtyData = loadingQtyData;
    this.machineDieCode = machineDieCode;
    this.manufacturingDefectsPerQtyTitle = manufacturingDefectsPerQtyTitle;
    this.operation = operation;
    this.randomQualityInspectionTxt = randomQualityInspectionTxt;
    this.sampleQty = sampleQty;
    this.save = save;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRandomQualityInceptionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRandomQualityInceptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_random_quality_inception, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRandomQualityInceptionBinding bind(@NonNull View rootView) {
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

      id = R.id.data_layout;
      ConstraintLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.defected_rejected_baskets;
      View defectedRejectedBaskets = ViewBindings.findChildViewById(rootView, id);
      if (defectedRejectedBaskets == null) {
        break missingId;
      }
      OnlineInspectionAddBasketsLayoutBinding binding_defectedRejectedBaskets = OnlineInspectionAddBasketsLayoutBinding.bind(defectedRejectedBaskets);

      id = R.id.defects_per_qty;
      RecyclerView defectsPerQty = ViewBindings.findChildViewById(rootView, id);
      if (defectsPerQty == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.loading_qty_data;
      View loadingQtyData = ViewBindings.findChildViewById(rootView, id);
      if (loadingQtyData == null) {
        break missingId;
      }
      LoadingQtyDataBinding binding_loadingQtyData = LoadingQtyDataBinding.bind(loadingQtyData);

      id = R.id.machine_die_code;
      TextInputLayout machineDieCode = ViewBindings.findChildViewById(rootView, id);
      if (machineDieCode == null) {
        break missingId;
      }

      id = R.id.manufacturing_defects_per_qty_title;
      View manufacturingDefectsPerQtyTitle = ViewBindings.findChildViewById(rootView, id);
      if (manufacturingDefectsPerQtyTitle == null) {
        break missingId;
      }
      DefectsPerQtyTitleBinding binding_manufacturingDefectsPerQtyTitle = DefectsPerQtyTitleBinding.bind(manufacturingDefectsPerQtyTitle);

      id = R.id.operation;
      TextView operation = ViewBindings.findChildViewById(rootView, id);
      if (operation == null) {
        break missingId;
      }

      id = R.id.random_quality_inspection_txt;
      TextView randomQualityInspectionTxt = ViewBindings.findChildViewById(rootView, id);
      if (randomQualityInspectionTxt == null) {
        break missingId;
      }

      id = R.id.sample_qty;
      TextInputLayout sampleQty = ViewBindings.findChildViewById(rootView, id);
      if (sampleQty == null) {
        break missingId;
      }

      id = R.id.save;
      MaterialButton save = ViewBindings.findChildViewById(rootView, id);
      if (save == null) {
        break missingId;
      }

      return new FragmentRandomQualityInceptionBinding((CoordinatorLayout) rootView, addDefects,
          childesc, dataLayout, binding_defectedRejectedBaskets, defectsPerQty,
          binding_jobOrderData, binding_loadingQtyData, machineDieCode,
          binding_manufacturingDefectsPerQtyTitle, operation, randomQualityInspectionTxt, sampleQty,
          save);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
