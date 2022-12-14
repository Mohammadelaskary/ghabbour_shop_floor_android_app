// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProductionRejectionBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final BasketQtyDataBinding basketQtyLayout;

  @NonNull
  public final TextView childdesc;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final DefectsListBottomSheetBinding defectsListBottomSheet;

  @NonNull
  public final View disableColor;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final ConstraintLayout layout;

  @NonNull
  public final TextInputLayout newBasketCode;

  @NonNull
  public final TextInputLayout oldBasketCode;

  @NonNull
  public final TextView operationName;

  @NonNull
  public final TextView productionscrapTxt;

  @NonNull
  public final TextInputLayout reason;

  @NonNull
  public final MaterialButton reasonDefBtn;

  @NonNull
  public final AutoCompleteTextView reasonSpinner;

  @NonNull
  public final TextInputLayout rejectedQtyEdt;

  @NonNull
  public final TextInputLayout responsibleDepSpin;

  @NonNull
  public final AutoCompleteTextView responsibleSpinner;

  @NonNull
  public final MaterialButton saveBtn;

  private FragmentProductionRejectionBinding(@NonNull CoordinatorLayout rootView,
      @NonNull BasketQtyDataBinding basketQtyLayout, @NonNull TextView childdesc,
      @NonNull ConstraintLayout dataLayout,
      @NonNull DefectsListBottomSheetBinding defectsListBottomSheet, @NonNull View disableColor,
      @NonNull JobOrderDataBinding jobOrderData, @NonNull ConstraintLayout layout,
      @NonNull TextInputLayout newBasketCode, @NonNull TextInputLayout oldBasketCode,
      @NonNull TextView operationName, @NonNull TextView productionscrapTxt,
      @NonNull TextInputLayout reason, @NonNull MaterialButton reasonDefBtn,
      @NonNull AutoCompleteTextView reasonSpinner, @NonNull TextInputLayout rejectedQtyEdt,
      @NonNull TextInputLayout responsibleDepSpin, @NonNull AutoCompleteTextView responsibleSpinner,
      @NonNull MaterialButton saveBtn) {
    this.rootView = rootView;
    this.basketQtyLayout = basketQtyLayout;
    this.childdesc = childdesc;
    this.dataLayout = dataLayout;
    this.defectsListBottomSheet = defectsListBottomSheet;
    this.disableColor = disableColor;
    this.jobOrderData = jobOrderData;
    this.layout = layout;
    this.newBasketCode = newBasketCode;
    this.oldBasketCode = oldBasketCode;
    this.operationName = operationName;
    this.productionscrapTxt = productionscrapTxt;
    this.reason = reason;
    this.reasonDefBtn = reasonDefBtn;
    this.reasonSpinner = reasonSpinner;
    this.rejectedQtyEdt = rejectedQtyEdt;
    this.responsibleDepSpin = responsibleDepSpin;
    this.responsibleSpinner = responsibleSpinner;
    this.saveBtn = saveBtn;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProductionRejectionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProductionRejectionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_production_rejection, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProductionRejectionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basket_qty_layout;
      View basketQtyLayout = ViewBindings.findChildViewById(rootView, id);
      if (basketQtyLayout == null) {
        break missingId;
      }
      BasketQtyDataBinding binding_basketQtyLayout = BasketQtyDataBinding.bind(basketQtyLayout);

      id = R.id.childdesc;
      TextView childdesc = ViewBindings.findChildViewById(rootView, id);
      if (childdesc == null) {
        break missingId;
      }

      id = R.id.data_layout;
      ConstraintLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.defects_list_bottom_sheet;
      View defectsListBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (defectsListBottomSheet == null) {
        break missingId;
      }
      DefectsListBottomSheetBinding binding_defectsListBottomSheet = DefectsListBottomSheetBinding.bind(defectsListBottomSheet);

      id = R.id.disable_color;
      View disableColor = ViewBindings.findChildViewById(rootView, id);
      if (disableColor == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.layout;
      ConstraintLayout layout = ViewBindings.findChildViewById(rootView, id);
      if (layout == null) {
        break missingId;
      }

      id = R.id.new_basket_code;
      TextInputLayout newBasketCode = ViewBindings.findChildViewById(rootView, id);
      if (newBasketCode == null) {
        break missingId;
      }

      id = R.id.old_basket_code;
      TextInputLayout oldBasketCode = ViewBindings.findChildViewById(rootView, id);
      if (oldBasketCode == null) {
        break missingId;
      }

      id = R.id.operation_name;
      TextView operationName = ViewBindings.findChildViewById(rootView, id);
      if (operationName == null) {
        break missingId;
      }

      id = R.id.productionscrap_txt;
      TextView productionscrapTxt = ViewBindings.findChildViewById(rootView, id);
      if (productionscrapTxt == null) {
        break missingId;
      }

      id = R.id.reason;
      TextInputLayout reason = ViewBindings.findChildViewById(rootView, id);
      if (reason == null) {
        break missingId;
      }

      id = R.id.reason_def_btn;
      MaterialButton reasonDefBtn = ViewBindings.findChildViewById(rootView, id);
      if (reasonDefBtn == null) {
        break missingId;
      }

      id = R.id.reason_spinner;
      AutoCompleteTextView reasonSpinner = ViewBindings.findChildViewById(rootView, id);
      if (reasonSpinner == null) {
        break missingId;
      }

      id = R.id.rejected_qty_edt;
      TextInputLayout rejectedQtyEdt = ViewBindings.findChildViewById(rootView, id);
      if (rejectedQtyEdt == null) {
        break missingId;
      }

      id = R.id.responsible_dep_spin;
      TextInputLayout responsibleDepSpin = ViewBindings.findChildViewById(rootView, id);
      if (responsibleDepSpin == null) {
        break missingId;
      }

      id = R.id.responsible_spinner;
      AutoCompleteTextView responsibleSpinner = ViewBindings.findChildViewById(rootView, id);
      if (responsibleSpinner == null) {
        break missingId;
      }

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      return new FragmentProductionRejectionBinding((CoordinatorLayout) rootView,
          binding_basketQtyLayout, childdesc, dataLayout, binding_defectsListBottomSheet,
          disableColor, binding_jobOrderData, layout, newBasketCode, oldBasketCode, operationName,
          productionscrapTxt, reason, reasonDefBtn, reasonSpinner, rejectedQtyEdt,
          responsibleDepSpin, responsibleSpinner, saveBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
