// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class BasketInfoFragmentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextInputLayout basketCode;

  @NonNull
  public final TextView childParentDesc;

  @NonNull
  public final ConstraintLayout dataLayout;

  @NonNull
  public final ConstraintLayout jobOrderData;

  @NonNull
  public final TextView jobOrderName;

  @NonNull
  public final TextView jobOrderQty;

  @NonNull
  public final TextView jobordernumTxt;

  @NonNull
  public final RecyclerView machineStationList;

  @NonNull
  public final TextView signoffqtnTxt;

  private BasketInfoFragmentBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextInputLayout basketCode, @NonNull TextView childParentDesc,
      @NonNull ConstraintLayout dataLayout, @NonNull ConstraintLayout jobOrderData,
      @NonNull TextView jobOrderName, @NonNull TextView jobOrderQty,
      @NonNull TextView jobordernumTxt, @NonNull RecyclerView machineStationList,
      @NonNull TextView signoffqtnTxt) {
    this.rootView = rootView;
    this.basketCode = basketCode;
    this.childParentDesc = childParentDesc;
    this.dataLayout = dataLayout;
    this.jobOrderData = jobOrderData;
    this.jobOrderName = jobOrderName;
    this.jobOrderQty = jobOrderQty;
    this.jobordernumTxt = jobordernumTxt;
    this.machineStationList = machineStationList;
    this.signoffqtnTxt = signoffqtnTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BasketInfoFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BasketInfoFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.basket_info_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BasketInfoFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basket_code;
      TextInputLayout basketCode = ViewBindings.findChildViewById(rootView, id);
      if (basketCode == null) {
        break missingId;
      }

      id = R.id.child_parent_desc;
      TextView childParentDesc = ViewBindings.findChildViewById(rootView, id);
      if (childParentDesc == null) {
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

      id = R.id.job_order_name;
      TextView jobOrderName = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderName == null) {
        break missingId;
      }

      id = R.id.job_order_qty;
      TextView jobOrderQty = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderQty == null) {
        break missingId;
      }

      id = R.id.jobordernum_txt;
      TextView jobordernumTxt = ViewBindings.findChildViewById(rootView, id);
      if (jobordernumTxt == null) {
        break missingId;
      }

      id = R.id.machine_station_list;
      RecyclerView machineStationList = ViewBindings.findChildViewById(rootView, id);
      if (machineStationList == null) {
        break missingId;
      }

      id = R.id.signoffqtn_txt;
      TextView signoffqtnTxt = ViewBindings.findChildViewById(rootView, id);
      if (signoffqtnTxt == null) {
        break missingId;
      }

      return new BasketInfoFragmentBinding((ConstraintLayout) rootView, basketCode, childParentDesc,
          dataLayout, jobOrderData, jobOrderName, jobOrderQty, jobordernumTxt, machineStationList,
          signoffqtnTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
