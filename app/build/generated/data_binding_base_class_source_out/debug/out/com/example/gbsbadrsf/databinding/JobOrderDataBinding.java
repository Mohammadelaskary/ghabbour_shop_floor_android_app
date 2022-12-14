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

public final class JobOrderDataBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView Joborderqtn;

  @NonNull
  public final ConstraintLayout jobOrderData;

  @NonNull
  public final TextView jobordernum;

  @NonNull
  public final TextView jobordernumTxt;

  private JobOrderDataBinding(@NonNull ConstraintLayout rootView, @NonNull TextView Joborderqtn,
      @NonNull ConstraintLayout jobOrderData, @NonNull TextView jobordernum,
      @NonNull TextView jobordernumTxt) {
    this.rootView = rootView;
    this.Joborderqtn = Joborderqtn;
    this.jobOrderData = jobOrderData;
    this.jobordernum = jobordernum;
    this.jobordernumTxt = jobordernumTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static JobOrderDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static JobOrderDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.job_order_data, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static JobOrderDataBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Joborderqtn;
      TextView Joborderqtn = ViewBindings.findChildViewById(rootView, id);
      if (Joborderqtn == null) {
        break missingId;
      }

      id = R.id.job_order_data;
      ConstraintLayout jobOrderData = ViewBindings.findChildViewById(rootView, id);
      if (jobOrderData == null) {
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

      return new JobOrderDataBinding((ConstraintLayout) rootView, Joborderqtn, jobOrderData,
          jobordernum, jobordernumTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
