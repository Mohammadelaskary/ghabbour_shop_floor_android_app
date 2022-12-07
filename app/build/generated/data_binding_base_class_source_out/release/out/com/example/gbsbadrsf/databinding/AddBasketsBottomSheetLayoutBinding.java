// Generated by view binder compiler. Do not edit!
package com.example.gbsbadrsf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gbsbadrsf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AddBasketsBottomSheetLayoutBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextInputLayout basketQty;

  @NonNull
  public final TextView basketQtyTxt;

  @NonNull
  public final TextInputLayout basketcodeEdt;

  @NonNull
  public final RecyclerView basketcodeRv;

  @NonNull
  public final Button bulk;

  @NonNull
  public final MaterialButtonToggleGroup bulkGroup;

  @NonNull
  public final MaterialButton cancel;

  @NonNull
  public final TextView childdesc;

  @NonNull
  public final Button diff;

  @NonNull
  public final ImageView dragIcon;

  @NonNull
  public final TextView loadingQty;

  @NonNull
  public final TextInputEditText newbasketcodeEdt;

  @NonNull
  public final MaterialButton saveBtn;

  @NonNull
  public final TextView signoffqtyTxt;

  @NonNull
  public final LinearLayout tableTitle;

  @NonNull
  public final TextView totalAddedQtn;

  @NonNull
  public final TextView totalqtnTxt;

  private AddBasketsBottomSheetLayoutBinding(@NonNull ScrollView rootView,
      @NonNull TextInputLayout basketQty, @NonNull TextView basketQtyTxt,
      @NonNull TextInputLayout basketcodeEdt, @NonNull RecyclerView basketcodeRv,
      @NonNull Button bulk, @NonNull MaterialButtonToggleGroup bulkGroup,
      @NonNull MaterialButton cancel, @NonNull TextView childdesc, @NonNull Button diff,
      @NonNull ImageView dragIcon, @NonNull TextView loadingQty,
      @NonNull TextInputEditText newbasketcodeEdt, @NonNull MaterialButton saveBtn,
      @NonNull TextView signoffqtyTxt, @NonNull LinearLayout tableTitle,
      @NonNull TextView totalAddedQtn, @NonNull TextView totalqtnTxt) {
    this.rootView = rootView;
    this.basketQty = basketQty;
    this.basketQtyTxt = basketQtyTxt;
    this.basketcodeEdt = basketcodeEdt;
    this.basketcodeRv = basketcodeRv;
    this.bulk = bulk;
    this.bulkGroup = bulkGroup;
    this.cancel = cancel;
    this.childdesc = childdesc;
    this.diff = diff;
    this.dragIcon = dragIcon;
    this.loadingQty = loadingQty;
    this.newbasketcodeEdt = newbasketcodeEdt;
    this.saveBtn = saveBtn;
    this.signoffqtyTxt = signoffqtyTxt;
    this.tableTitle = tableTitle;
    this.totalAddedQtn = totalAddedQtn;
    this.totalqtnTxt = totalqtnTxt;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static AddBasketsBottomSheetLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddBasketsBottomSheetLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_baskets_bottom_sheet_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddBasketsBottomSheetLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.basket_qty;
      TextInputLayout basketQty = ViewBindings.findChildViewById(rootView, id);
      if (basketQty == null) {
        break missingId;
      }

      id = R.id.basket_qty_txt;
      TextView basketQtyTxt = ViewBindings.findChildViewById(rootView, id);
      if (basketQtyTxt == null) {
        break missingId;
      }

      id = R.id.basketcode_edt;
      TextInputLayout basketcodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (basketcodeEdt == null) {
        break missingId;
      }

      id = R.id.basketcode_rv;
      RecyclerView basketcodeRv = ViewBindings.findChildViewById(rootView, id);
      if (basketcodeRv == null) {
        break missingId;
      }

      id = R.id.bulk;
      Button bulk = ViewBindings.findChildViewById(rootView, id);
      if (bulk == null) {
        break missingId;
      }

      id = R.id.bulk_group;
      MaterialButtonToggleGroup bulkGroup = ViewBindings.findChildViewById(rootView, id);
      if (bulkGroup == null) {
        break missingId;
      }

      id = R.id.cancel;
      MaterialButton cancel = ViewBindings.findChildViewById(rootView, id);
      if (cancel == null) {
        break missingId;
      }

      id = R.id.childdesc;
      TextView childdesc = ViewBindings.findChildViewById(rootView, id);
      if (childdesc == null) {
        break missingId;
      }

      id = R.id.diff;
      Button diff = ViewBindings.findChildViewById(rootView, id);
      if (diff == null) {
        break missingId;
      }

      id = R.id.drag_icon;
      ImageView dragIcon = ViewBindings.findChildViewById(rootView, id);
      if (dragIcon == null) {
        break missingId;
      }

      id = R.id.loading_qty;
      TextView loadingQty = ViewBindings.findChildViewById(rootView, id);
      if (loadingQty == null) {
        break missingId;
      }

      id = R.id.newbasketcode_edt;
      TextInputEditText newbasketcodeEdt = ViewBindings.findChildViewById(rootView, id);
      if (newbasketcodeEdt == null) {
        break missingId;
      }

      id = R.id.save_btn;
      MaterialButton saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      id = R.id.signoffqty_txt;
      TextView signoffqtyTxt = ViewBindings.findChildViewById(rootView, id);
      if (signoffqtyTxt == null) {
        break missingId;
      }

      id = R.id.table_title;
      LinearLayout tableTitle = ViewBindings.findChildViewById(rootView, id);
      if (tableTitle == null) {
        break missingId;
      }

      id = R.id.total_added_qtn;
      TextView totalAddedQtn = ViewBindings.findChildViewById(rootView, id);
      if (totalAddedQtn == null) {
        break missingId;
      }

      id = R.id.totalqtn_txt;
      TextView totalqtnTxt = ViewBindings.findChildViewById(rootView, id);
      if (totalqtnTxt == null) {
        break missingId;
      }

      return new AddBasketsBottomSheetLayoutBinding((ScrollView) rootView, basketQty, basketQtyTxt,
          basketcodeEdt, basketcodeRv, bulk, bulkGroup, cancel, childdesc, diff, dragIcon,
          loadingQty, newbasketcodeEdt, saveBtn, signoffqtyTxt, tableTitle, totalAddedQtn,
          totalqtnTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}