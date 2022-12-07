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
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class BasketQtyDataBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView loadingQtyTitle;

  @NonNull
  public final TextView qty;

  @NonNull
  public final ConstraintLayout qtyData;

  private BasketQtyDataBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView loadingQtyTitle, @NonNull TextView qty, @NonNull ConstraintLayout qtyData) {
    this.rootView = rootView;
    this.loadingQtyTitle = loadingQtyTitle;
    this.qty = qty;
    this.qtyData = qtyData;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BasketQtyDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BasketQtyDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.basket_qty_data, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BasketQtyDataBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.loading_qty_title;
      TextView loadingQtyTitle = ViewBindings.findChildViewById(rootView, id);
      if (loadingQtyTitle == null) {
        break missingId;
      }

      id = R.id.qty;
      TextView qty = ViewBindings.findChildViewById(rootView, id);
      if (qty == null) {
        break missingId;
      }

      id = R.id.qty_data;
      ConstraintLayout qtyData = ViewBindings.findChildViewById(rootView, id);
      if (qtyData == null) {
        break missingId;
      }

      return new BasketQtyDataBinding((ConstraintLayout) rootView, loadingQtyTitle, qty, qtyData);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
