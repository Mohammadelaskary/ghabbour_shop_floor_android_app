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

public final class RepairorderLstBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView basketcode;

  @NonNull
  public final TextView basketcodeTxt;

  @NonNull
  public final TextView child;

  @NonNull
  public final TextView childTxt;

  @NonNull
  public final TextView defectedqtnTxt;

  @NonNull
  public final TextView jobordername;

  @NonNull
  public final TextView jobordernameTxt;

  @NonNull
  public final TextView jobordreqtn;

  @NonNull
  public final CardView mainimportcard;

  @NonNull
  public final TextView numberofdefects;

  @NonNull
  public final TextView numberofdefectsTxt;

  @NonNull
  public final TextView operation;

  @NonNull
  public final TextView operationTxt;

  private RepairorderLstBinding(@NonNull CardView rootView, @NonNull TextView basketcode,
      @NonNull TextView basketcodeTxt, @NonNull TextView child, @NonNull TextView childTxt,
      @NonNull TextView defectedqtnTxt, @NonNull TextView jobordername,
      @NonNull TextView jobordernameTxt, @NonNull TextView jobordreqtn,
      @NonNull CardView mainimportcard, @NonNull TextView numberofdefects,
      @NonNull TextView numberofdefectsTxt, @NonNull TextView operation,
      @NonNull TextView operationTxt) {
    this.rootView = rootView;
    this.basketcode = basketcode;
    this.basketcodeTxt = basketcodeTxt;
    this.child = child;
    this.childTxt = childTxt;
    this.defectedqtnTxt = defectedqtnTxt;
    this.jobordername = jobordername;
    this.jobordernameTxt = jobordernameTxt;
    this.jobordreqtn = jobordreqtn;
    this.mainimportcard = mainimportcard;
    this.numberofdefects = numberofdefects;
    this.numberofdefectsTxt = numberofdefectsTxt;
    this.operation = operation;
    this.operationTxt = operationTxt;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static RepairorderLstBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RepairorderLstBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.repairorder_lst, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RepairorderLstBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basketcode;
      TextView basketcode = ViewBindings.findChildViewById(rootView, id);
      if (basketcode == null) {
        break missingId;
      }

      id = R.id.basketcode_txt;
      TextView basketcodeTxt = ViewBindings.findChildViewById(rootView, id);
      if (basketcodeTxt == null) {
        break missingId;
      }

      id = R.id.child;
      TextView child = ViewBindings.findChildViewById(rootView, id);
      if (child == null) {
        break missingId;
      }

      id = R.id.child_txt;
      TextView childTxt = ViewBindings.findChildViewById(rootView, id);
      if (childTxt == null) {
        break missingId;
      }

      id = R.id.defectedqtn_txt;
      TextView defectedqtnTxt = ViewBindings.findChildViewById(rootView, id);
      if (defectedqtnTxt == null) {
        break missingId;
      }

      id = R.id.jobordername;
      TextView jobordername = ViewBindings.findChildViewById(rootView, id);
      if (jobordername == null) {
        break missingId;
      }

      id = R.id.jobordername_txt;
      TextView jobordernameTxt = ViewBindings.findChildViewById(rootView, id);
      if (jobordernameTxt == null) {
        break missingId;
      }

      id = R.id.jobordreqtn;
      TextView jobordreqtn = ViewBindings.findChildViewById(rootView, id);
      if (jobordreqtn == null) {
        break missingId;
      }

      CardView mainimportcard = (CardView) rootView;

      id = R.id.numberofdefects;
      TextView numberofdefects = ViewBindings.findChildViewById(rootView, id);
      if (numberofdefects == null) {
        break missingId;
      }

      id = R.id.numberofdefects_txt;
      TextView numberofdefectsTxt = ViewBindings.findChildViewById(rootView, id);
      if (numberofdefectsTxt == null) {
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

      return new RepairorderLstBinding((CardView) rootView, basketcode, basketcodeTxt, child,
          childTxt, defectedqtnTxt, jobordername, jobordernameTxt, jobordreqtn, mainimportcard,
          numberofdefects, numberofdefectsTxt, operation, operationTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
