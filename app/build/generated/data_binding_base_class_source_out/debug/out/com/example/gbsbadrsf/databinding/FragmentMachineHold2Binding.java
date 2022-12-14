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

public final class FragmentMachineHold2Binding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final TextView Joborderqtn;

  @NonNull
  public final TextView childesc;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final ConstraintLayout jobOrderData;

  @NonNull
  public final TextView jobordernum;

  @NonNull
  public final TextView jobordernumTxt;

  @NonNull
  public final TextView loadingQty;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView productionsignoffTxt;

  @NonNull
  public final ConstraintLayout remainingQtyData;

  @NonNull
  public final MaterialButton saveBtn;

  @NonNull
  public final HoldSignOffBasketsLayoutBinding signOffBaskets;

  @NonNull
  public final TextView signOffQtyTitle;

  @NonNull
  public final TextInputLayout stopReason;

  @NonNull
  public final AutoCompleteTextView stopReasonSpinner;

  private FragmentMachineHold2Binding(@NonNull CoordinatorLayout rootView,
      @NonNull TextView Joborderqtn, @NonNull TextView childesc,
      @NonNull ConstraintLayout dataLayout, @NonNull ConstraintLayout jobOrderData,
      @NonNull TextView jobordernum, @NonNull TextView jobordernumTxt, @NonNull TextView loadingQty,
      @NonNull TextView operation, @NonNull TextView productionsignoffTxt,
      @NonNull ConstraintLayout remainingQtyData, @NonNull MaterialButton saveBtn,
      @NonNull HoldSignOffBasketsLayoutBinding signOffBaskets, @NonNull TextView signOffQtyTitle,
      @NonNull TextInputLayout stopReason, @NonNull AutoCompleteTextView stopReasonSpinner) {
    this.rootView = rootView;
    this.Joborderqtn = Joborderqtn;
    this.childesc = childesc;
    this.dataLayout = dataLayout;
    this.jobOrderData = jobOrderData;
    this.jobordernum = jobordernum;
    this.jobordernumTxt = jobordernumTxt;
    this.loadingQty = loadingQty;
    this.operation = operation;
    this.productionsignoffTxt = productionsignoffTxt;
    this.remainingQtyData = remainingQtyData;
    this.saveBtn = saveBtn;
    this.signOffBaskets = signOffBaskets;
    this.signOffQtyTitle = signOffQtyTitle;
    this.stopReason = stopReason;
    this.stopReasonSpinner = stopReasonSpinner;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMachineHold2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMachineHold2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_machine_hold2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMachineHold2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Joborderqtn;
      TextView Joborderqtn = ViewBindings.findChildViewById(rootView, id);
      if (Joborderqtn == null) {
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

      id = R.id.job_order_data;
      ConstraintLayout jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
        break missingId;
      }

      id = R.id.jobordernum;
      TextView jobordernum = ViewBindings.findChildViewById(rootView, id);
      if (jobordernum == null) {
        break missingId;
      }

      id = R.id.jobordernum_txt;
      TextView jobordernumTxt = ViewBindings.findChildViewById(rootView, id);
      if (jobordernumTxt == null) {
        break missingId;
      }

      id = R.id.loading_qty;
      TextView loadingQty = ViewBindings.findChildViewById(rootView, id);
      if (loadingQty == null) {
        break missingId;
      }

      id = R.id.operation;
      TextView operation = ViewBindings.findChildViewById(rootView, id);
      if (operation == null) {
        break missingId;
      }

      id = R.id.productionsignoff_txt;
      TextView productionsignoffTxt = ViewBindings.findChildViewById(rootView, id);
      if (productionsignoffTxt == null) {
        break missingId;
      }

      id = R.id.remaining_qty_data;
      ConstraintLayout remainingQtyData = ViewBindings.findChildViewById(rootView, id);
      if (remainingQtyData == null) {
        break missingId;
      }

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      id = R.id.sign_off_baskets;
      View signOffBaskets = ViewBindings.findChildViewById(rootView, id);
      if (signOffBaskets == null) {
        break missingId;
      }
      HoldSignOffBasketsLayoutBinding binding_signOffBaskets = HoldSignOffBasketsLayoutBinding.bind(signOffBaskets);

      id = R.id.sign_off_qty_title;
      TextView signOffQtyTitle = ViewBindings.findChildViewById(rootView, id);
      if (signOffQtyTitle == null) {
        break missingId;
      }

      id = R.id.stop_reason;
      TextInputLayout stopReason = ViewBindings.findChildViewById(rootView, id);
      if (stopReason == null) {
        break missingId;
      }

      id = R.id.stop_reason_spinner;
      AutoCompleteTextView stopReasonSpinner = ViewBindings.findChildViewById(rootView, id);
      if (stopReasonSpinner == null) {
        break missingId;
      }

      return new FragmentMachineHold2Binding((CoordinatorLayout) rootView, Joborderqtn, childesc,
          dataLayout, jobOrderData, jobordernum, jobordernumTxt, loadingQty, operation,
          productionsignoffTxt, remainingQtyData, saveBtn, binding_signOffBaskets, signOffQtyTitle,
          stopReason, stopReasonSpinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
