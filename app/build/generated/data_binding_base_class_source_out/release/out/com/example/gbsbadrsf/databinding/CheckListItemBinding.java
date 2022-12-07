// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CheckListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView checkDescription;

  @NonNull
  public final CheckBox checkbox;

  private CheckListItemBinding(@NonNull LinearLayout rootView, @NonNull TextView checkDescription,
      @NonNull CheckBox checkbox) {
    this.rootView = rootView;
    this.checkDescription = checkDescription;
    this.checkbox = checkbox;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CheckListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CheckListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.check_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CheckListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.check_description;
      TextView checkDescription = ViewBindings.findChildViewById(rootView, id);
      if (checkDescription == null) {
        break missingId;
      }

      id = R.id.checkbox;
      CheckBox checkbox = ViewBindings.findChildViewById(rootView, id);
      if (checkbox == null) {
        break missingId;
      }

      return new CheckListItemBinding((LinearLayout) rootView, checkDescription, checkbox);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
