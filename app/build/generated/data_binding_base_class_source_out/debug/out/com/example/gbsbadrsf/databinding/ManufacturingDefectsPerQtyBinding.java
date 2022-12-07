// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ManufacturingDefectsPerQtyBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView background;

  @NonNull
  public final TextView defectedQty;

  @NonNull
  public final TextView defects;

  @NonNull
  public final View line;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final MaterialButton remove;

  private ManufacturingDefectsPerQtyBinding(@NonNull CardView rootView,
      @NonNull CardView background, @NonNull TextView defectedQty, @NonNull TextView defects,
      @NonNull View line, @NonNull LinearLayout linearLayout2, @NonNull MaterialButton remove) {
    this.rootView = rootView;
    this.background = background;
    this.defectedQty = defectedQty;
    this.defects = defects;
    this.line = line;
    this.linearLayout2 = linearLayout2;
    this.remove = remove;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ManufacturingDefectsPerQtyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ManufacturingDefectsPerQtyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.manufacturing_defects_per_qty, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ManufacturingDefectsPerQtyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView background = (CardView) rootView;

      id = R.id.defected_qty;
      TextView defectedQty = ViewBindings.findChildViewById(rootView, id);
      if (defectedQty == null) {
        break missingId;
      }

      id = R.id.defects;
      TextView defects = ViewBindings.findChildViewById(rootView, id);
      if (defects == null) {
        break missingId;
      }

      id = R.id.line;
      View line = ViewBindings.findChildViewById(rootView, id);
      if (line == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.remove;
      MaterialButton remove = ViewBindings.findChildViewById(rootView, id);
      if (remove == null) {
        break missingId;
      }

      return new ManufacturingDefectsPerQtyBinding((CardView) rootView, background, defectedQty,
          defects, line, linearLayout2, remove);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}