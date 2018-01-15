package com.ehighsun.contactasnycread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RelationShipListActivity extends AppCompatActivity {

    private RelationShipAdapter mAdapter;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_listview)
    ListView lvListview;
    @BindView(R.id.main_contact_title)
    LinearLayout titleLayout;
    @BindView(R.id.main_contact_sortkey)
    TextView title;
    @BindView(R.id.section_toast_layout)
    RelativeLayout rlToast;
    @BindView(R.id.layout)
    LinearLayout llIndex;
    @BindView(R.id.section_toast_text)
    TextView tvToast;
    @BindView(R.id.to_add_contact)
    RelativeLayout mToAdd;
    @BindView(R.id.contact)
    RelativeLayout mContact;

    private int lastFirstVisibleItem = -1;
    private Context context;
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";

    private boolean flag;
    private int height;//字体高度
    private String[] indexStr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "#"};

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_ship_list);
        context = this;
        ButterKnife.bind(this);
        setTitle("通讯录");
        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initViews() {
        llBack.setVisibility(View.VISIBLE);
        tvTitle.setText("通讯录");

    }

    private void initData() {
        new MyAsyncTask().execute();
    }

    public void goBack(View view){
        finish();
    }


    public class MyAsyncTask extends AsyncTask<Void,Void,List<Contacts>>{


        @Override
        protected List<Contacts> doInBackground(Void... params) {
            return ContactsGetUtil.getPhoneNum(context);
        }


        @Override
        protected void onPostExecute(final List<Contacts> contactses) {
            mContact.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            mAdapter = new RelationShipAdapter(contactses, context);
            if (contactses.size() > 0) {
                setupContactsListView();
                mContact.setVisibility(View.VISIBLE);
                mToAdd.setVisibility(View.GONE);
            } else {
                mContact.setVisibility(View.GONE);
                mToAdd.setVisibility(View.VISIBLE);
            }
            lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("data", contactses.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            ViewTreeObserver observer = llIndex.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    llIndex.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    height = (llIndex.getMeasuredHeight() - llIndex.getPaddingTop() - llIndex.getPaddingBottom()) / indexStr.length;
                    Log.d("getMeasuredHeight","height="+llIndex.getMeasuredHeight());
                    getIndextView();
                }
            });
        }
    }


    /**
     * 为联系人ListView设置监听事件，根据当前的滑动状态来改变分组的显示位置，从而实现挤压动画的效果。
     */
    private void setupContactsListView() {
        lvListview.setAdapter(mAdapter);
        lvListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

                int section = mAdapter.getSectionForPosition(firstVisibleItem);
                int nextSection = mAdapter.getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = mAdapter.getPositionForSection(nextSection);
                Log.d("onScroll", "section=>" + section);
                Log.d("onScroll", "nextSection=>" + nextSection);
                Log.d("onScroll", "nextSecPosition=>" + nextSecPosition);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    Log.d("onScroll", "进入=>");
                    Log.d("onScroll", "firstVisibleItem=>" + firstVisibleItem);
                    Log.d("onScroll", "lastFirstVisibleItem=>" + lastFirstVisibleItem);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(String.valueOf(alphabet.charAt(section)));
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    Log.d("onScroll", "firstVisibleItem=>" + firstVisibleItem);
                    Log.d("onScroll", "nextSecPosition=>" + nextSecPosition);
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            Log.d("onScroll", "bottom=>" + bottom);
                            Log.d("onScroll", "titleHeight=>" + titleHeight);
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        if (!flag) {
//            height = (llIndex.getMeasuredHeight() - llIndex.getPaddingTop() - llIndex.getPaddingBottom()) / indexStr.length;
//            getIndextView();
//            flag = true;
//        }

    }

    private void getIndextView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setPadding(10, 0, 10, 0);
            llIndex.addView(tv);
        }

        llIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                int index = (int) ((y - llIndex.getPaddingTop()) / height);
                Log.d("MotionEvent","y="+y+",index="+index+",height="+height);
                String sectionLetter = null;
                if (index < 0) {
                    index = 0;
                } else if (index > 26) {
                    index = 26;
                }
                int position = mAdapter.getPositionForSection(index);
                sectionLetter = String.valueOf(alphabet.charAt(index));
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rlToast.setVisibility(View.VISIBLE);
                        tvToast.setText(sectionLetter);
                        lvListview.setSelection(position);
                        break;


                    case MotionEvent.ACTION_MOVE:
                        tvToast.setText(sectionLetter);
                        lvListview.setSelection(position);
                        break;
                    case MotionEvent.ACTION_UP:
                        rlToast.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
