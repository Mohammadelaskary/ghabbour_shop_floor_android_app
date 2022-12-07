// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPaintQualityRepairBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextInputLayout basketCode;

  @NonNull
  public final TextView defectQtn;

  @NonNull
  public final TextView defectQtnTxt;

  @NonNull
  public final RecyclerView defectsDetailsList;

  @NonNull
  public final TextView defectsList;

  @NonNull
  public final TextView defectsQty;

  @NonNull
  public final TextView holdQty;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView operationTxt;

  @NonNull
  public final TextView parentCode;

  @NonNull
  public final TextView parentDesc;

  @NonNull
  public final TextView parentTxt;

  @NonNull
  public final TextView qualityRepairTxt;

  private FragmentPaintQualityRepairBinding(@NonNull ScrollView rootView,
      @NonNull TextInputLayout basketCode, @NonNull TextView defectQtn,
      @NonNull TextView defectQtnTxt, @NonNull RecyclerView defectsDetailsList,
      @NonNull TextView defectsList, @NonNull TextView defectsQty, @NonNull TextView holdQty,
      @NonNull LinearLayout linearLayout, @NonNull TextView operation,
      @NonNull TextView operationTxt, @NonNull TextView parentCode, @NonNull TextView parentDesc,
      @NonNull TextView parentTxt, @NonNull TextView qualityRepairTxt) {
    this.rootView = rootView;
    this.basketCode = basketCode;
    this.defectQtn = defectQtn;
    this.defectQtnTxt = defectQtnTxt;
    this.defectsDetailsList = defectsDetailsList;
    this.defectsList = defectsList;
    this.defectsQty = defectsQty;
    this.holdQty = holdQty;
    this.linearLayout = linearLayout;
    this.operation = operation;
    this.operationTxt = operationTxt;
    this.parentCode = parentCode;
    this.parentDesc = parentDesc;
    this.parentTxt = parentTxt;
    this.qualityRepairTxt = qualityRepairTxt;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPaintQualityRepairBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPaintQualityRepairBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_paint_quality_repair, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPaintQualityRepairBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basket_code;
      TextInputLayout basketCode = ViewBindings.findChildViewById(rootView, id);
      if (basketCode == null) {
        break missingId;
      }

      id = R.id.defect_qtn;
      TextView defectQtn = ViewBindings.findChildViewById(rootView, id);
      if (defectQtn == null) {
        break missingId;
      }

      id = R.id.defect_qtn_txt;
      TextView defectQtnTxt = ViewBindings.findChildViewById(rootView, id);
      if (defectQtnTxt == null) {
        break missingId;
      }

      id = R.id.defects_details_list;
      RecyclerView defectsDetailsList = ViewBindings.findChildViewById(rootView, id);
      if (defectsDetailsList == null) {
        break missingId;
      }

      id = R.id.defects_list;
      TextView defectsList = ViewBindings.findChildViewById(rootView, id);
      if (defectsList == null) {
        break missingId;
      }

      id = R.id.defects_qty;
      TextView defectsQty = ViewBindings.findChildViewById(rootView, id);
      if (defectsQty == null) {
        break missingId;
      }

      id = R.id.hold_qty;
      TextView holdQty = ViewBindings.findChildViewById(rootView, id);
      if (holdQty == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.operation;
      TextView operation = ViewBindings.findChildViewById(rootView, id);
      if (operation == null) {
        break missingId;
      }

      id = R.id.operation_txt;
      TextView operationTxt = ViewBindings.findChildViewById(rootView, id);
      if (operationTxt == null) {
        break missingId;
      }

      id = R.id.parent_code;
      TextView parentCode = ViewBindings.findChildViewById(rootView, id);
      if (parentCode == null) {
        break missingId;
      }

      id = R.id.parent_desc;
      TextView parentDesc = ViewBindings.findChildViewById(rootView, id);
      if (parentDesc == null) {
        break missingId;
      }

      id = R.id.parent_txt;
      TextView parentTxt = ViewBindings.findChildViewById(rootView, id);
      if (parentTxt == null) {
        break missingId;
      }

      id = R.id.quality_repair_txt;
      TextView qualityRepairTxt = ViewBindings.findChildViewById(rootView, id);
      if (qualityRepairTxt == null) {
        break missingId;
      }

      return new FragmentPaintQualityRepairBinding((ScrollView) rootView, basketCode, defectQtn,
          defectQtnTxt, defectsDetailsList, defectsList, defectsQty, holdQty, linearLayout,
          operation, operationTxt, parentCode, parentDesc, parentTxt, qualityRepairTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
