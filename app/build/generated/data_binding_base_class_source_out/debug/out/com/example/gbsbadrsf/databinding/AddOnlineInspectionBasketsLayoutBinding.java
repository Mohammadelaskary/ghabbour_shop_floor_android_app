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

public final class AddOnlineInspectionBasketsLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton addDefecedBasket;

  @NonNull
  public final MaterialButton addRejectedBasket;

  @NonNull
  public final TextView defectedQty;

  @NonNull
  public final TextView defectedText;

  @NonNull
  public final TextView loadingQtyTitle;

  @NonNull
  public final ConstraintLayout qtyData;

  @NonNull
  public final TextView rejectedQty;

  @NonNull
  public final TextView rejectedText;

  private AddOnlineInspectionBasketsLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton addDefecedBasket, @NonNull MaterialButton addRejectedBasket,
      @NonNull TextView defectedQty, @NonNull TextView defectedText,
      @NonNull TextView loadingQtyTitle, @NonNull ConstraintLayout qtyData,
      @NonNull TextView rejectedQty, @NonNull TextView rejectedText) {
    this.rootView = rootView;
    this.addDefecedBasket = addDefecedBasket;
    this.addRejectedBasket = addRejectedBasket;
    this.defectedQty = defectedQty;
    this.defectedText = defectedText;
    this.loadingQtyTitle = loadingQtyTitle;
    this.qtyData = qtyData;
    this.rejectedQty = rejectedQty;
    this.rejectedText = rejectedText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddOnlineInspectionBasketsLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddOnlineInspectionBasketsLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_online_inspection_baskets_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddOnlineInspectionBasketsLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_defeced_basket;
      MaterialButton addDefecedBasket = ViewBindings.findChildViewById(rootView, id);
      if (addDefecedBasket == null) {
        break missingId;
      }

      id = R.id.add_rejected_basket;
      MaterialButton addRejectedBasket = ViewBindings.findChildViewById(rootView, id);
      if (addRejectedBasket == null) {
        break missingId;
      }

      id = R.id.defected_qty;
      TextView defectedQty = ViewBindings.findChildViewById(rootView, id);
      if (defectedQty == null) {
        break missingId;
      }

      id = R.id.defected_text;
      TextView defectedText = ViewBindings.findChildViewById(rootView, id);
      if (defectedText == null) {
        break missingId;
      }

      id = R.id.loading_qty_title;
      TextView loadingQtyTitle = ViewBindings.findChildViewById(rootView, id);
      if (loadingQtyTitle == null) {
        break missingId;
      }

      id = R.id.qty_data;
      ConstraintLayout qtyData = ViewBindings.findChildViewById(rootView, id);
      if (qtyData == null) {
        break missingId;
      }

      id = R.id.rejected_qty;
      TextView rejectedQty = ViewBindings.findChildViewById(rootView, id);
      if (rejectedQty == null) {
        break missingId;
      }

      id = R.id.rejected_text;
      TextView rejectedText = ViewBindings.findChildViewById(rootView, id);
      if (rejectedText == null) {
        break missingId;
      }

      return new AddOnlineInspectionBasketsLayoutBinding((ConstraintLayout) rootView,
          addDefecedBasket, addRejectedBasket, defectedQty, defectedText, loadingQtyTitle, qtyData,
          rejectedQty, rejectedText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
