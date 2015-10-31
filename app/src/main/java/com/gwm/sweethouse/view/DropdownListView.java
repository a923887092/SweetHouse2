package com.gwm.sweethouse.view;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.UiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
public class DropdownListView extends ScrollView {
    public LinearLayout linearLayout;

    public DropdownItemObject current;
    private Context context;
    List<? extends DropdownItemObject> list;

    public DropdownButton button;

    public DropdownListView(Context context) {
        this(context, null);
    }

    public DropdownListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();

    }

    private void init() {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_list, this,true);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
    }


    public void flush() {
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                DropdownListItemView itemView = (DropdownListItemView) view;
                DropdownItemObject data = (DropdownItemObject) itemView.getTag();
                if (data == null) {
                    return;
                }
                boolean checked = data == current;
                String suffix = data.getSuffix();
                itemView.bind(TextUtils.isEmpty(suffix) ? data.text : data.text + suffix, checked);
                if (checked) button.setText(data.text);
            }

        }
    }

    public void bind(List<? extends DropdownItemObject> list,
                     DropdownButton button,
                     final Container container,
                     int selectedId
    ) {
        current = null;
        this.list = list;
        this.button = button;
        LinkedList<View> cachedDividers = new LinkedList<>();
        LinkedList<DropdownListItemView> cachedViews = new LinkedList<>();
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                cachedViews.add((DropdownListItemView) view);
            } else {
                cachedDividers.add(view);
            }
        }

        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        boolean isFirst = true;
        for (DropdownItemObject item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                View divider = cachedDividers.poll();
                if (divider == null) {
                    divider = inflater.inflate(R.layout.dropdown_tab_list_divider, linearLayout, false);
                }
                linearLayout.addView(divider);
            }
            DropdownListItemView view = cachedViews.poll();
            if (view == null) {
                view = (DropdownListItemView) inflater.inflate(R.layout.dropdown_tab_list_item, linearLayout, false);
            }
            view.setTag(item);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropdownItemObject data = (DropdownItemObject) v.getTag();
                    if (data == null) return;
                    DropdownItemObject oldOne = current;
                    current = data;
                    flush();
                    container.hide();
                    if (oldOne != current) {
                        container.onSelectionChanged(DropdownListView.this);
                    }

                    /*ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(2000);
                            pageNo = 1;
                            final String dirTime1 = System.currentTimeMillis() + "";
                            GoodsProtocol protocol = new GoodsProtocol(getUrl(), dirTime1);
                            final ArrayList<Product> recommends_refresh = protocol.loadData();
                            UiUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (recommends_refresh != null) {
                                        File dir = FilesUtils.getCacheDri();
                                        if (dirTime != null) {
                                            File file = new File(dir, dirTime);
                                            if (file.isFile() && file.exists()) {
                                                file.delete();
                                            }
                                            dirTime = dirTime1;
                                        }
                                        goods.clear();
                                        goods.addAll(recommends_refresh);
                                        LogUtils.d("aaaaa" + goods);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    });*/
                }
            });
            linearLayout.addView(view);
            if (item.id == selectedId && current == null) {
                current = item;
            }
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == VISIBLE) {
                    container.hide();
                } else {
                    container.show(DropdownListView.this);
                }
            }
        });

        if (current == null && list.size() > 0) {
            current = list.get(0);
        }
        flush();
    }


    public static interface Container {
        void show(DropdownListView listView);

        void hide();

        void onSelectionChanged(DropdownListView view);
    }


}
