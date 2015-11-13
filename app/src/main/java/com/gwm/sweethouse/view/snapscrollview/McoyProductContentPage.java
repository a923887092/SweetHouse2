package com.gwm.sweethouse.view.snapscrollview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gwm.sweethouse.R;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {

	private LinearLayout mBottomView;
	private Context context;

	private View rootView = null;

	public McoyProductContentPage(Context context,View rootView) {
		this.context = context;
		this.rootView = rootView;
		mBottomView = (LinearLayout) this.rootView
				.findViewById(R.id.ll_root);
	}

	@Override
	public View getRootView() {
		return rootView;
	}

	@Override
	public boolean isAtTop() {
		/*int scrollY = mBottomView.getScrollY();
		int height = mBottomView.getHeight();
		int scrollViewMeasuredHeight = mBottomView.getChildAt(1).getMeasuredHeight();
		if ((scrollY + height) >= scrollViewMeasuredHeight) {
			return true;
		}*/
		return true;
	}

	@Override
	public boolean isAtBottom() {
		return false;
	}
}
