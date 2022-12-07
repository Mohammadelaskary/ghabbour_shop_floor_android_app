// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public final class FragmentPaintQualityDefectRepairBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final TextInputLayout approvedQty;

  @NonNull
  public final LinearLayout dataLayout;

  @NonNull
  public final DefectedQtyDataBinding defectedQtnEdt;

  @NonNull
  public final RecyclerView defectsDetailsList;

  @NonNull
  public final View disable;

  @NonNull
  public final MaterialButton moveToBasket;

  @NonNull
  public final MoveToBasketBottomSheetBinding moveToBasketBottomSheet;

  @NonNull
  public final TextView moveToBasketQty;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final TextView qualityRepairTxt;

  @NonNull
  public final MaterialButton reopenDefectBtn;

  @NonNull
  public final MaterialButton saveBtn;

  private FragmentPaintQualityDefectRepairBinding(@NonNull CoordinatorLayout rootView,
      @NonNull TextInputLayout approvedQty, @NonNull LinearLayout dataLayout,
      @NonNull DefectedQtyDataBinding defectedQtnEdt, @NonNull RecyclerView defectsDetailsList,
      @NonNull View disable, @NonNull MaterialButton moveToBasket,
      @NonNull MoveToBasketBottomSheetBinding moveToBasketBottomSheet,
      @NonNull TextView moveToBasketQty, @NonNull TextView operation, @NonNull TextView parentDesc,
      @NonNull TextView qualityRepairTxt, @NonNull MaterialButton reopenDefectBtn,
      @NonNull MaterialButton saveBtn) {
    this.rootView = rootView;
    this.approvedQty = approvedQty;
    this.dataLayout = dataLayout;
    this.defectedQtnEdt = defectedQtnEdt;
    this.defectsDetailsList = defectsDetailsList;
    this.disable = disable;
    this.moveToBasket = moveToBasket;
    this.moveToBasketBottomSheet = moveToBasketBottomSheet;
    this.moveToBasketQty = moveToBasketQty;
    this.operation = operation;
    this.parentDesc = parentDesc;
    this.qualityRepairTxt = qualityRepairTxt;
    this.reopenDefectBtn = reopenDefectBtn;
    this.saveBtn = saveBtn;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintQualityDefectRepairBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintQualityDefectRepairBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paint_quality_defect_repair, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintQualityDefectRepairBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.approved_qty;
      TextInputLayout approvedQty = ViewBindings.findChildViewById(rootView, id);
      if (approvedQty == null) {
        break missingId;
      }

      id = R.id.data_layout;
      LinearLayout dataLayout = ViewBindings.findChildViewById(rootView, id);
      if (dataLayout == null) {
        break missingId;
      }

      id = R.id.defected_qtn_edt;
      View defectedQtnEdt = ViewBindings.findChildViewById(rootView, id);
      if (defectedQtnEdt == null) {
        break missingId;
      }
      DefectedQtyDataBinding binding_defectedQtnEdt = DefectedQtyDataBinding.bind(defectedQtnEdt);

      id = R.id.defects_details_list;
      RecyclerView defectsDetailsList = ViewBindings.findChildViewById(rootView, id);
      if (defectsDetailsList == null) {
        break missingId;
      }

      id = R.id.disable;
      View disable = ViewBindings.findChildViewById(rootView, id);
      if (disable == null) {
        break missingId;
      }

      id = R.id.move_to_basket;
      MaterialButton moveToBasket = ViewBindings.findChildViewById(rootView, id);
      if (moveToBasket == null) {
        break missingId;
      }

      id = R.id.move_to_basket_bottom_sheet;
      View moveToBasketBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (moveToBasketBottomSheet == null) {
        break missingId;
      }
      MoveToBasketBottomSheetBinding binding_moveToBasketBottomSheet = MoveToBasketBottomSheetBinding.bind(moveToBasketBottomSheet);

      id = R.id.move_to_basket_qty;
      TextView moveToBasketQty = ViewBindings.findChildViewById(rootView, id);
      if (moveToBasketQty == null) {
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

      id = R.id.quality_repair_txt;
      TextView qualityRepairTxt = ViewBindings.findChildViewById(rootView, id);
      if (qualityRepairTxt == null) {
        break missingId;
      }

      id = R.id.reopen_defect_btn;
      MaterialButton reopenDefectBtn = ViewBindings.findChildViewById(rootView, id);
      if (reopenDefectBtn == null) {
        break missingId;
      }

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      return new FragmentPaintQualityDefectRepairBinding((CoordinatorLayout) rootView, approvedQty,
          dataLayout, binding_defectedQtnEdt, defectsDetailsList, disable, moveToBasket,
          binding_moveToBasketBottomSheet, moveToBasketQty, operation, parentDesc, qualityRepairTxt,
          reopenDefectBtn, saveBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
