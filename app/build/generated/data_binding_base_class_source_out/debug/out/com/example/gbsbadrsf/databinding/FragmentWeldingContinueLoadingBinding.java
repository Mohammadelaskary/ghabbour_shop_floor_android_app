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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentWeldingContinueLoadingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextInputLayout basketcodeEdt;

  @NonNull
  public final TextView childcode;

  @NonNull
  public final TextView childcodeTxt;

  @NonNull
  public final TextView childesc;

  @NonNull
  public final TextInputLayout diecodeEdt;

  @NonNull
  public final TextView jobordernum;

  @NonNull
  public final TextView jobordernumTxt;

  @NonNull
  public final TextInputLayout machinecodeEdt;

  @NonNull
  public final TextInputEditText machinecodeNewedttxt;

  @NonNull
  public final TextView machineloadingTxt;

  @NonNull
  public final TextInputEditText newdiecodeEdt;

  @NonNull
  public final MaterialButton saveBtn;

  private FragmentWeldingContinueLoadingBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextInputLayout basketcodeEdt, @NonNull TextView childcode,
      @NonNull TextView childcodeTxt, @NonNull TextView childesc,
      @NonNull TextInputLayout diecodeEdt, @NonNull TextView jobordernum,
      @NonNull TextView jobordernumTxt, @NonNull TextInputLayout machinecodeEdt,
      @NonNull TextInputEditText machinecodeNewedttxt, @NonNull TextView machineloadingTxt,
      @NonNull TextInputEditText newdiecodeEdt, @NonNull MaterialButton saveBtn) {
    this.rootView = rootView;
    this.basketcodeEdt = basketcodeEdt;
    this.childcode = childcode;
    this.childcodeTxt = childcodeTxt;
    this.childesc = childesc;
    this.diecodeEdt = diecodeEdt;
    this.jobordernum = jobordernum;
    this.jobordernumTxt = jobordernumTxt;
    this.machinecodeEdt = machinecodeEdt;
    this.machinecodeNewedttxt = machinecodeNewedttxt;
    this.machineloadingTxt = machineloadingTxt;
    this.newdiecodeEdt = newdiecodeEdt;
    this.saveBtn = saveBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentWeldingContinueLoadingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentWeldingContinueLoadingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_welding_continue_loading, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentWeldingContinueLoadingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basketcode_edt;
      TextInputLayout basketcodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (basketcodeEdt == null) {
        break missingId;
      }

      id = R.id.childcode;
      TextView childcode = ViewBindings.findChildViewById(rootView, id);
      if (childcode == null) {
        break missingId;
      }

      id = R.id.childcode_txt;
      TextView childcodeTxt = ViewBindings.findChildViewById(rootView, id);
      if (childcodeTxt == null) {
        break missingId;
      }

      id = R.id.childesc;
      TextView childesc = ViewBindings.findChildViewById(rootView, id);
      if (childesc == null) {
        break missingId;
      }

      id = R.id.diecode_edt;
      TextInputLayout diecodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (diecodeEdt == null) {
        break missingId;
      }

      id = R.id.jobordernum;
      TextView jobordernum = ViewBindings.findChildViewById(rootView, id);
      if (jobordernum == null) {
        break missingId;
      }

      id = R.id.jobordernum_txt;
      TextView jobordernumTxt = ViewBindings.findChildViewById(rootView, id);
      if (jobordernumTxt == null) {
        break missingId;
      }

      id = R.id.machinecode_edt;
      TextInputLayout machinecodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (machinecodeEdt == null) {
        break missingId;
      }

      id = R.id.machinecode_newedttxt;
      TextInputEditText machinecodeNewedttxt = ViewBindings.findChildViewById(rootView, id);
      if (machinecodeNewedttxt == null) {
        break missingId;
      }

      id = R.id.machineloading_txt;
      TextView machineloadingTxt = ViewBindings.findChildViewById(rootView, id);
      if (machineloadingTxt == null) {
        break missingId;
      }

      id = R.id.newdiecode_edt;
      TextInputEditText newdiecodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (newdiecodeEdt == null) {
        break missingId;
      }

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      return new FragmentWeldingContinueLoadingBinding((ConstraintLayout) rootView, basketcodeEdt,
          childcode, childcodeTxt, childesc, diecodeEdt, jobordernum, jobordernumTxt,
          machinecodeEdt, machinecodeNewedttxt, machineloadingTxt, newdiecodeEdt, saveBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}