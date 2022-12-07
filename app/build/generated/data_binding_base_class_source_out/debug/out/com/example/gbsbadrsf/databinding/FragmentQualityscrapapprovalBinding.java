// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentQualityscrapapprovalBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton acceptBtn;

  @NonNull
  public final TextView child;

  @NonNull
  public final TextView childTxt;

  @NonNull
  public final TextView childname;

  @NonNull
  public final MaterialButton declineBtn;

  @NonNull
  public final TextView jobordername;

  @NonNull
  public final TextView jobordernameTxt;

  @NonNull
  public final EditText notesEdt;

  @NonNull
  public final TextView notesTxt;

  @NonNull
  public final AppBarLayout productionscrapapprAppbar;

  @NonNull
  public final TextView responsibledep;

  @NonNull
  public final TextView responsibledepTxt;

  @NonNull
  public final TextView scrapqtn;

  @NonNull
  public final TextView scrapqtnTxt;

  private FragmentQualityscrapapprovalBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton acceptBtn, @NonNull TextView child, @NonNull TextView childTxt,
      @NonNull TextView childname, @NonNull MaterialButton declineBtn,
      @NonNull TextView jobordername, @NonNull TextView jobordernameTxt, @NonNull EditText notesEdt,
      @NonNull TextView notesTxt, @NonNull AppBarLayout productionscrapapprAppbar,
      @NonNull TextView responsibledep, @NonNull TextView responsibledepTxt,
      @NonNull TextView scrapqtn, @NonNull TextView scrapqtnTxt) {
    this.rootView = rootView;
    this.acceptBtn = acceptBtn;
    this.child = child;
    this.childTxt = childTxt;
    this.childname = childname;
    this.declineBtn = declineBtn;
    this.jobordername = jobordername;
    this.jobordernameTxt = jobordernameTxt;
    this.notesEdt = notesEdt;
    this.notesTxt = notesTxt;
    this.productionscrapapprAppbar = productionscrapapprAppbar;
    this.responsibledep = responsibledep;
    this.responsibledepTxt = responsibledepTxt;
    this.scrapqtn = scrapqtn;
    this.scrapqtnTxt = scrapqtnTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentQualityscrapapprovalBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentQualityscrapapprovalBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_qualityscrapapproval, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentQualityscrapapprovalBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.accept_btn;
      MaterialButton acceptBtn = ViewBindings.findChildViewById(rootView, id);
      if (acceptBtn == null) {
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

      id = R.id.childname;
      TextView childname = ViewBindings.findChildViewById(rootView, id);
      if (childname == null) {
        break missingId;
      }

      id = R.id.decline_btn;
      MaterialButton declineBtn = ViewBindings.findChildViewById(rootView, id);
      if (declineBtn == null) {
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

      id = R.id.notes_edt;
      EditText notesEdt = ViewBindings.findChildViewById(rootView, id);
      if (notesEdt == null) {
        break missingId;
      }

      id = R.id.notes_txt;
      TextView notesTxt = ViewBindings.findChildViewById(rootView, id);
      if (notesTxt == null) {
        break missingId;
      }

      id = R.id.productionscrapappr_appbar;
      AppBarLayout productionscrapapprAppbar = ViewBindings.findChildViewById(rootView, id);
      if (productionscrapapprAppbar == null) {
        break missingId;
      }

      id = R.id.responsibledep;
      TextView responsibledep = ViewBindings.findChildViewById(rootView, id);
      if (responsibledep == null) {
        break missingId;
      }

      id = R.id.responsibledep_txt;
      TextView responsibledepTxt = ViewBindings.findChildViewById(rootView, id);
      if (responsibledepTxt == null) {
        break missingId;
      }

      id = R.id.scrapqtn;
      TextView scrapqtn = ViewBindings.findChildViewById(rootView, id);
      if (scrapqtn == null) {
        break missingId;
      }

      id = R.id.scrapqtn_txt;
      TextView scrapqtnTxt = ViewBindings.findChildViewById(rootView, id);
      if (scrapqtnTxt == null) {
        break missingId;
      }

      return new FragmentQualityscrapapprovalBinding((ConstraintLayout) rootView, acceptBtn, child,
          childTxt, childname, declineBtn, jobordername, jobordernameTxt, notesEdt, notesTxt,
          productionscrapapprAppbar, responsibledep, responsibledepTxt, scrapqtn, scrapqtnTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
