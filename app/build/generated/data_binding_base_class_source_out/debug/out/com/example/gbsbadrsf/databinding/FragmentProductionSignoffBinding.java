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
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProductionSignoffBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final TextView Joborderqtn;

  @NonNull
  public final SignoffcustomdialogBinding basketsBottomSheet;

  @NonNull
  public final TextView childesc;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final View disableColor;

  @NonNull
  public final ConstraintLayout jobOrderData;

  @NonNull
  public final TextView jobordernum;

  @NonNull
  public final TextView jobordernumTxt;

  @NonNull
  public final TextView loadingQty;

  @NonNull
  public final TextInputLayout machinecodeEdt;

  @NonNull
  public final TextInputEditText machinecodeNewedttxt;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView productionsignoffTxt;

  @NonNull
  public final MaterialButton saveBtn;

  @NonNull
  public final ConstraintLayout signOffData;

  @NonNull
  public final TextInputLayout signOffQty;

  @NonNull
  public final TextView signOffQtyTxt;

  @NonNull
  public final TextView signedOffQty;

  @NonNull
  public final MaterialButton signoffitemsBtn;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView textView20;

  private FragmentProductionSignoffBinding(@NonNull CoordinatorLayout rootView,
      @NonNull TextView Joborderqtn, @NonNull SignoffcustomdialogBinding basketsBottomSheet,
      @NonNull TextView childesc, @NonNull ConstraintLayout dataLayout, @NonNull View disableColor,
      @NonNull ConstraintLayout jobOrderData, @NonNull TextView jobordernum,
      @NonNull TextView jobordernumTxt, @NonNull TextView loadingQty,
      @NonNull TextInputLayout machinecodeEdt, @NonNull TextInputEditText machinecodeNewedttxt,
      @NonNull TextView operation, @NonNull TextView productionsignoffTxt,
      @NonNull MaterialButton saveBtn, @NonNull ConstraintLayout signOffData,
      @NonNull TextInputLayout signOffQty, @NonNull TextView signOffQtyTxt,
      @NonNull TextView signedOffQty, @NonNull MaterialButton signoffitemsBtn,
      @NonNull TextView textView1, @NonNull TextView textView20) {
    this.rootView = rootView;
    this.Joborderqtn = Joborderqtn;
    this.basketsBottomSheet = basketsBottomSheet;
    this.childesc = childesc;
    this.dataLayout = dataLayout;
    this.disableColor = disableColor;
    this.jobOrderData = jobOrderData;
    this.jobordernum = jobordernum;
    this.jobordernumTxt = jobordernumTxt;
    this.loadingQty = loadingQty;
    this.machinecodeEdt = machinecodeEdt;
    this.machinecodeNewedttxt = machinecodeNewedttxt;
    this.operation = operation;
    this.productionsignoffTxt = productionsignoffTxt;
    this.saveBtn = saveBtn;
    this.signOffData = signOffData;
    this.signOffQty = signOffQty;
    this.signOffQtyTxt = signOffQtyTxt;
    this.signedOffQty = signedOffQty;
    this.signoffitemsBtn = signoffitemsBtn;
    this.textView1 = textView1;
    this.textView20 = textView20;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProductionSignoffBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProductionSignoffBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_production_signoff, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProductionSignoffBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Joborderqtn;
      TextView Joborderqtn = ViewBindings.findChildViewById(rootView, id);
      if (Joborderqtn == null) {
        break missingId;
      }

      id = R.id.baskets_bottom_sheet;
      View basketsBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (basketsBottomSheet == null) {
        break missingId;
      }
      SignoffcustomdialogBinding binding_basketsBottomSheet = SignoffcustomdialogBinding.bind(basketsBottomSheet);

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

      id = R.id.disable_color;
      View disableColor = ViewBindings.findChildViewById(rootView, id);
      if (disableColor == null) {
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

      id = R.id.machinecode_edt;
      TextInputLayout machinecodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (machinecodeEdt == null) {
        break missingId;
      }

      id = R.id.machinecode_newedttxt;
      TextInputEditText machinecodeNewedttxt = ViewBindings.findChildViewById(rootView, id);
      if (machinecodeNewedttxt == null) {
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

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      id = R.id.sign_off_data;
      ConstraintLayout signOffData = ViewBindings.findChildViewById(rootView, id);
      if (signOffData == null) {
        break missingId;
      }

      id = R.id.sign_off_qty;
      TextInputLayout signOffQty = ViewBindings.findChildViewById(rootView, id);
      if (signOffQty == null) {
        break missingId;
      }

      id = R.id.sign_off_qty_txt;
      TextView signOffQtyTxt = ViewBindings.findChildViewById(rootView, id);
      if (signOffQtyTxt == null) {
        break missingId;
      }

      id = R.id.signed_off_qty;
      TextView signedOffQty = ViewBindings.findChildViewById(rootView, id);
      if (signedOffQty == null) {
        break missingId;
      }

      id = R.id.signoffitems_btn;
      MaterialButton signoffitemsBtn = ViewBindings.findChildViewById(rootView, id);
      if (signoffitemsBtn == null) {
        break missingId;
      }

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      id = R.id.textView20;
      TextView textView20 = ViewBindings.findChildViewById(rootView, id);
      if (textView20 == null) {
        break missingId;
      }

      return new FragmentProductionSignoffBinding((CoordinatorLayout) rootView, Joborderqtn,
          binding_basketsBottomSheet, childesc, dataLayout, disableColor, jobOrderData, jobordernum,
          jobordernumTxt, loadingQty, machinecodeEdt, machinecodeNewedttxt, operation,
          productionsignoffTxt, saveBtn, signOffData, signOffQty, signOffQtyTxt, signedOffQty,
          signoffitemsBtn, textView1, textView20);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
