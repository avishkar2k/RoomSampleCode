// Generated code from Butter Knife. Do not modify!
package com.example.appinventiv.roomdatabasesample.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.appinventiv.roomdatabasesample.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RestaurantListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private RestaurantListAdapter.ViewHolder target;

  @UiThread
  public RestaurantListAdapter$ViewHolder_ViewBinding(RestaurantListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.ivLogoIcon = Utils.findRequiredViewAsType(source, R.id.iv_logo_icon, "field 'ivLogoIcon'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", AppCompatTextView.class);
    target.tvRatings = Utils.findRequiredViewAsType(source, R.id.tv_ratings, "field 'tvRatings'", AppCompatTextView.class);
    target.rlItemContainer = Utils.findRequiredViewAsType(source, R.id.rl_item_container, "field 'rlItemContainer'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RestaurantListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLogoIcon = null;
    target.tvTitle = null;
    target.tvRatings = null;
    target.rlItemContainer = null;
  }
}
