// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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

public final class DeclineRejectionRequestDecisionFragmentBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final DeclineRejectionBasketCodeBottomSheetBinding approvedRejectedBasketCodeBottomSheet;

  @NonNull
  public final TextInputLayout approvedRejectedQty;

  @NonNull
  public final ImageView barcodeIcon;

  @NonNull
  public final TextView basketCode;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final View disableColor;

  @NonNull
  public final MaterialButton editOkBasket;

  @NonNull
  public final JobOrderDataBinding jobOrderData;

  @NonNull
  public final ConstraintLayout okBasketLayout;

  @NonNull
  public final TextInputLayout okQty;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final TextView productionscrapTxt;

  @NonNull
  public final TextInputLayout reason;

  @NonNull
  public final AutoCompleteTextView reasonSpinner;

  @NonNull
  public final RejectedLayoutDataBinding rejectedQtyData;

  @NonNull
  public final TextInputLayout responsibleDepSpin;

  @NonNull
  public final AutoCompleteTextView responsibleSpinner;

  @NonNull
  public final MaterialButton save;

  private DeclineRejectionRequestDecisionFragmentBinding(@NonNull CoordinatorLayout rootView,
      @NonNull DeclineRejectionBasketCodeBottomSheetBinding approvedRejectedBasketCodeBottomSheet,
      @NonNull TextInputLayout approvedRejectedQty, @NonNull ImageView barcodeIcon,
      @NonNull TextView basketCode, @NonNull ConstraintLayout dataLayout,
      @NonNull View disableColor, @NonNull MaterialButton editOkBasket,
      @NonNull JobOrderDataBinding jobOrderData, @NonNull ConstraintLayout okBasketLayout,
      @NonNull TextInputLayout okQty, @NonNull TextView parentDesc,
      @NonNull TextView productionscrapTxt, @NonNull TextInputLayout reason,
      @NonNull AutoCompleteTextView reasonSpinner,
      @NonNull RejectedLayoutDataBinding rejectedQtyData,
      @NonNull TextInputLayout responsibleDepSpin, @NonNull AutoCompleteTextView responsibleSpinner,
      @NonNull MaterialButton save) {
    this.rootView = rootView;
    this.approvedRejectedBasketCodeBottomSheet = approvedRejectedBasketCodeBottomSheet;
    this.approvedRejectedQty = approvedRejectedQty;
    this.barcodeIcon = barcodeIcon;
    this.basketCode = basketCode;
    this.dataLayout = dataLayout;
    this.disableColor = disableColor;
    this.editOkBasket = editOkBasket;
    this.jobOrderData = jobOrderData;
    this.okBasketLayout = okBasketLayout;
    this.okQty = okQty;
    this.parentDesc = parentDesc;
    this.productionscrapTxt = productionscrapTxt;
    this.reason = reason;
    this.reasonSpinner = reasonSpinner;
    this.rejectedQtyData = rejectedQtyData;
    this.responsibleDepSpin = responsibleDepSpin;
    this.responsibleSpinner = responsibleSpinner;
    this.save = save;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DeclineRejectionRequestDecisionFragmentBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DeclineRejectionRequestDecisionFragmentBinding inflate(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.decline_rejection_request_decision_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DeclineRejectionRequestDecisionFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.approved_rejected_basket_code_bottom_sheet;
      View approvedRejectedBasketCodeBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (approvedRejectedBasketCodeBottomSheet == null) {
        break missingId;
      }
      DeclineRejectionBasketCodeBottomSheetBinding binding_approvedRejectedBasketCodeBottomSheet = DeclineRejectionBasketCodeBottomSheetBinding.bind(approvedRejectedBasketCodeBottomSheet);

      id = R.id.approved_rejected_qty;
      TextInputLayout approvedRejectedQty = ViewBindings.findChildViewById(rootView, id);
      if (approvedRejectedQty == null) {
        break missingId;
      }

      id = R.id.barcode_icon;
      ImageView barcodeIcon = ViewBindings.findChildViewById(rootView, id);
      if (barcodeIcon == null) {
        break missingId;
      }

      id = R.id.basket_code;
      TextView basketCode = ViewBindings.findChildViewById(rootView, id);
      if (basketCode == null) {
        break missingId;
      }

      id = R.id.data_layout;
      ConstraintLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.disable_color;
      View disableColor = ViewBindings.findChildViewById(rootView, id);
      if (disableColor == null) {
        break missingId;
      }

      id = R.id.edit_ok_basket;
      MaterialButton editOkBasket = ViewBindings.findChildViewById(rootView, id);
      if (editOkBasket == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      View jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }
      JobOrderDataBinding binding_jobOrderData = JobOrderDataBinding.bind(jobOrderData);

      id = R.id.ok_basket_layout;
      ConstraintLayout okBasketLayout = ViewBindings.findChildViewById(rootView, id);
      if (okBasketLayout == null) {
        break missingId;
      }

      id = R.id.ok_qty;
      TextInputLayout okQty = ViewBindings.findChildViewById(rootView, id);
      if (okQty == null) {
        break missingId;
      }

      id = R.id.parent_desc;
      TextView parentDesc = ViewBindings.findChildViewById(rootView, id);
      if (parentDesc == null) {
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

      id = R.id.reason_spinner;
      AutoCompleteTextView reasonSpinner = ViewBindings.findChildViewById(rootView, id);
      if (reasonSpinner == null) {
        break missingId;
      }

      id = R.id.rejected_qty_data;
      View rejectedQtyData = ViewBindings.findChildViewById(rootView, id);
      if (rejectedQtyData == null) {
        break missingId;
      }
      RejectedLayoutDataBinding binding_rejectedQtyData = RejectedLayoutDataBinding.bind(rejectedQtyData);

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

      id = R.id.save;
      MaterialButton save = ViewBindings.findChildViewById(rootView, id);
      if (save == null) {
        break missingId;
      }

      return new DeclineRejectionRequestDecisionFragmentBinding((CoordinatorLayout) rootView,
          binding_approvedRejectedBasketCodeBottomSheet, approvedRejectedQty, barcodeIcon,
          basketCode, dataLayout, disableColor, editOkBasket, binding_jobOrderData, okBasketLayout,
          okQty, parentDesc, productionscrapTxt, reason, reasonSpinner, binding_rejectedQtyData,
          responsibleDepSpin, responsibleSpinner, save);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
