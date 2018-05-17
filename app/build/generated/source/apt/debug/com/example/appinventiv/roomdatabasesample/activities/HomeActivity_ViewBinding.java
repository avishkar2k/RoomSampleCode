// Generated code from Butter Knife. Do not modify!
package com.example.appinventiv.roomdatabasesample.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.appinventiv.roomdatabasesample.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  private View view2131165238;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(final HomeActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.et_address, "field 'etAddress' and method 'onViewClicked'");
    target.etAddress = Utils.castView(view, R.id.et_address, "field 'etAddress'", EditText.class);
    view2131165238 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.rvRestaurantList = Utils.findRequiredViewAsType(source, R.id.rv_restaurant_list, "field 'rvRestaurantList'", RecyclerView.class);
    target.srlRestaurantList = Utils.findRequiredViewAsType(source, R.id.srl_restaurant_list, "field 'srlRestaurantList'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etAddress = null;
    target.rvRestaurantList = null;
    target.srlRestaurantList = null;

    view2131165238.setOnClickListener(null);
    view2131165238 = null;
  }
}
