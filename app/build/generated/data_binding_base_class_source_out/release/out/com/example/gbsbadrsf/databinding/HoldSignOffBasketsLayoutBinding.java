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

public final class HoldSignOffBasketsLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton addHoldBaskets;

  @NonNull
  public final MaterialButton addSignOffBaskets;

  @NonNull
  public final TextView holdQty;

  @NonNull
  public final TextView holdText;

  @NonNull
  public final TextView loadingQtyTitle;

  @NonNull
  public final ConstraintLayout qtyData;

  @NonNull
  public final TextView signOffQty;

  @NonNull
  public final TextView signOffText;

  private HoldSignOffBasketsLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton addHoldBaskets, @NonNull MaterialButton addSignOffBaskets,
      @NonNull TextView holdQty, @NonNull TextView holdText, @NonNull TextView loadingQtyTitle,
      @NonNull ConstraintLayout qtyData, @NonNull TextView signOffQty,
      @NonNull TextView signOffText) {
    this.rootView = rootView;
    this.addHoldBaskets = addHoldBaskets;
    this.addSignOffBaskets = addSignOffBaskets;
    this.holdQty = holdQty;
    this.holdText = holdText;
    this.loadingQtyTitle = loadingQtyTitle;
    this.qtyData = qtyData;
    this.signOffQty = signOffQty;
    this.signOffText = signOffText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static HoldSignOffBasketsLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HoldSignOffBasketsLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.hold_sign_off_baskets_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HoldSignOffBasketsLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_hold_baskets;
      MaterialButton addHoldBaskets = ViewBindings.findChildViewById(rootView, id);
      if (addHoldBaskets == null) {
        break missingId;
      }

      id = R.id.add_sign_off_baskets;
      MaterialButton addSignOffBaskets = ViewBindings.findChildViewById(rootView, id);
      if (addSignOffBaskets == null) {
        break missingId;
      }

      id = R.id.hold_qty;
      TextView holdQty = ViewBindings.findChildViewById(rootView, id);
      if (holdQty == null) {
        break missingId;
      }

      id = R.id.hold_text;
      TextView holdText = ViewBindings.findChildViewById(rootView, id);
      if (holdText == null) {
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

      id = R.id.sign_off_qty;
      TextView signOffQty = ViewBindings.findChildViewById(rootView, id);
      if (signOffQty == null) {
        break missingId;
      }

      id = R.id.sign_off_text;
      TextView signOffText = ViewBindings.findChildViewById(rootView, id);
      if (signOffText == null) {
        break missingId;
      }

      return new HoldSignOffBasketsLayoutBinding((ConstraintLayout) rootView, addHoldBaskets,
          addSignOffBaskets, holdQty, holdText, loadingQtyTitle, qtyData, signOffQty, signOffText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
