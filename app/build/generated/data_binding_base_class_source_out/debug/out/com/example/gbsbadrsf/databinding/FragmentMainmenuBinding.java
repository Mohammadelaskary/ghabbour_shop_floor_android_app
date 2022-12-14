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

public final class FragmentMainmenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton basketInfoBtn;

  @NonNull
  public final MaterialButton changeIp;

  @NonNull
  public final MaterialButton engineeringImg;

  @NonNull
  public final MaterialButton handling;

  @NonNull
  public final TextView mainmenuTxt;

  @NonNull
  public final MaterialButton planningImg;

  @NonNull
  public final MaterialButton productionImg;

  @NonNull
  public final MaterialButton qualityImg;

  @NonNull
  public final MaterialButton stoppages;

  @NonNull
  public final MaterialButton warhouse;

  private FragmentMainmenuBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton basketInfoBtn, @NonNull MaterialButton changeIp,
      @NonNull MaterialButton engineeringImg, @NonNull MaterialButton handling,
      @NonNull TextView mainmenuTxt, @NonNull MaterialButton planningImg,
      @NonNull MaterialButton productionImg, @NonNull MaterialButton qualityImg,
      @NonNull MaterialButton stoppages, @NonNull MaterialButton warhouse) {
    this.rootView = rootView;
    this.basketInfoBtn = basketInfoBtn;
    this.changeIp = changeIp;
    this.engineeringImg = engineeringImg;
    this.handling = handling;
    this.mainmenuTxt = mainmenuTxt;
    this.planningImg = planningImg;
    this.productionImg = productionImg;
    this.qualityImg = qualityImg;
    this.stoppages = stoppages;
    this.warhouse = warhouse;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMainmenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMainmenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_mainmenu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMainmenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basket_info_btn;
      MaterialButton basketInfoBtn = ViewBindings.findChildViewById(rootView, id);
      if (basketInfoBtn == null) {
        break missingId;
      }

      id = R.id.change_ip;
      MaterialButton changeIp = ViewBindings.findChildViewById(rootView, id);
      if (changeIp == null) {
        break missingId;
      }

      id = R.id.engineering_img;
      MaterialButton engineeringImg = ViewBindings.findChildViewById(rootView, id);
      if (engineeringImg == null) {
        break missingId;
      }

      id = R.id.handling;
      MaterialButton handling = ViewBindings.findChildViewById(rootView, id);
      if (handling == null) {
        break missingId;
      }

      id = R.id.mainmenu_txt;
      TextView mainmenuTxt = ViewBindings.findChildViewById(rootView, id);
      if (mainmenuTxt == null) {
        break missingId;
      }

      id = R.id.planning_img;
      MaterialButton planningImg = ViewBindings.findChildViewById(rootView, id);
      if (planningImg == null) {
        break missingId;
      }

      id = R.id.production_img;
      MaterialButton productionImg = ViewBindings.findChildViewById(rootView, id);
      if (productionImg == null) {
        break missingId;
      }

      id = R.id.quality_img;
      MaterialButton qualityImg = ViewBindings.findChildViewById(rootView, id);
      if (qualityImg == null) {
        break missingId;
      }

      id = R.id.stoppages;
      MaterialButton stoppages = ViewBindings.findChildViewById(rootView, id);
      if (stoppages == null) {
        break missingId;
      }

      id = R.id.warhouse;
      MaterialButton warhouse = ViewBindings.findChildViewById(rootView, id);
      if (warhouse == null) {
        break missingId;
      }

      return new FragmentMainmenuBinding((ConstraintLayout) rootView, basketInfoBtn, changeIp,
          engineeringImg, handling, mainmenuTxt, planningImg, productionImg, qualityImg, stoppages,
          warhouse);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
