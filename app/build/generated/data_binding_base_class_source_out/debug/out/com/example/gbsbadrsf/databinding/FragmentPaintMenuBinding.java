// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPaintMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton addWorkers;

  @NonNull
  public final MaterialButton approvalRejectionRequest;

  @NonNull
  public final MaterialButton colorverificationBtn;

  @NonNull
  public final MaterialButton machineloadingBtn;

  @NonNull
  public final MaterialButton paintSignOff;

  @NonNull
  public final TextView paintmenuTxt;

  @NonNull
  public final MaterialButton paintwipBtn;

  @NonNull
  public final MaterialButton productionrepairBtn;

  @NonNull
  public final MaterialButton rejectionRequestBtn;

  private FragmentPaintMenuBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton addWorkers, @NonNull MaterialButton approvalRejectionRequest,
      @NonNull MaterialButton colorverificationBtn, @NonNull MaterialButton machineloadingBtn,
      @NonNull MaterialButton paintSignOff, @NonNull TextView paintmenuTxt,
      @NonNull MaterialButton paintwipBtn, @NonNull MaterialButton productionrepairBtn,
      @NonNull MaterialButton rejectionRequestBtn) {
    this.rootView = rootView;
    this.addWorkers = addWorkers;
    this.approvalRejectionRequest = approvalRejectionRequest;
    this.colorverificationBtn = colorverificationBtn;
    this.machineloadingBtn = machineloadingBtn;
    this.paintSignOff = paintSignOff;
    this.paintmenuTxt = paintmenuTxt;
    this.paintwipBtn = paintwipBtn;
    this.productionrepairBtn = productionrepairBtn;
    this.rejectionRequestBtn = rejectionRequestBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paint_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_workers;
      MaterialButton addWorkers = ViewBindings.findChildViewById(rootView, id);
      if (addWorkers == null) {
        break missingId;
      }

      id = R.id.approval_rejection_request;
      MaterialButton approvalRejectionRequest = ViewBindings.findChildViewById(rootView, id);
      if (approvalRejectionRequest == null) {
        break missingId;
      }

      id = R.id.colorverification_btn;
      MaterialButton colorverificationBtn = ViewBindings.findChildViewById(rootView, id);
      if (colorverificationBtn == null) {
        break missingId;
      }

      id = R.id.machineloading_btn;
      MaterialButton machineloadingBtn = ViewBindings.findChildViewById(rootView, id);
      if (machineloadingBtn == null) {
        break missingId;
      }

      id = R.id.paint_sign_off;
      MaterialButton paintSignOff = ViewBindings.findChildViewById(rootView, id);
      if (paintSignOff == null) {
        break missingId;
      }

      id = R.id.paintmenu_txt;
      TextView paintmenuTxt = ViewBindings.findChildViewById(rootView, id);
      if (paintmenuTxt == null) {
        break missingId;
      }

      id = R.id.paintwip_btn;
      MaterialButton paintwipBtn = ViewBindings.findChildViewById(rootView, id);
      if (paintwipBtn == null) {
        break missingId;
      }

      id = R.id.productionrepair_btn;
      MaterialButton productionrepairBtn = ViewBindings.findChildViewById(rootView, id);
      if (productionrepairBtn == null) {
        break missingId;
      }

      id = R.id.rejection_request_btn;
      MaterialButton rejectionRequestBtn = ViewBindings.findChildViewById(rootView, id);
      if (rejectionRequestBtn == null) {
        break missingId;
      }

      return new FragmentPaintMenuBinding((ConstraintLayout) rootView, addWorkers,
          approvalRejectionRequest, colorverificationBtn, machineloadingBtn, paintSignOff,
          paintmenuTxt, paintwipBtn, productionrepairBtn, rejectionRequestBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
