// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DisplayChildItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView childDesc;

  @NonNull
  public final TextView issuedQty;

  private DisplayChildItemBinding(@NonNull CardView rootView, @NonNull TextView childDesc,
      @NonNull TextView issuedQty) {
    this.rootView = rootView;
    this.childDesc = childDesc;
    this.issuedQty = issuedQty;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DisplayChildItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DisplayChildItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.display_child_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DisplayChildItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.child_desc;
      TextView childDesc = ViewBindings.findChildViewById(rootView, id);
      if (childDesc == null) {
        break missingId;
      }

      id = R.id.issued_qty;
      TextView issuedQty = ViewBindings.findChildViewById(rootView, id);
      if (issuedQty == null) {
        break missingId;
      }

      return new DisplayChildItemBinding((CardView) rootView, childDesc, issuedQty);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
