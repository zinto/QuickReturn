package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderListFragment extends ListFragment {

    // region Constants
    // endregion

    // region Member Variables
    private ListView mListView;
    private TextView mQuickReturnTextView;
    private String[] mValues;
    private int mMinHeaderTranslation;
    private int mHeaderHeight;
    private int mPrevScrollY = 0;
    private int mDiffTotal = 0;
    private TranslateAnimation mAnim;
    private boolean isActionUp = false;
    // endregion

    //region Listeners
    private AbsListView.OnScrollListener mListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            int scrollY = QuickReturnUtils.getScrollY(mListView);
            int diff = mPrevScrollY - scrollY;

            if(diff <=0){ // scrolling down
                mDiffTotal = Math.max(mDiffTotal+diff, mMinHeaderTranslation);
            } else { // scrolling up
                mDiffTotal = Math.min(Math.max(mDiffTotal+diff, mMinHeaderTranslation), 0);
            }

            /** this can be used if the build is below honeycomb **/
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mAnim = new TranslateAnimation(0, 0, mDiffTotal,
                        mDiffTotal);
                mAnim.setFillAfter(true);
                mAnim.setDuration(0);
                mQuickReturnTextView.startAnimation(mAnim);
            } else {
                mQuickReturnTextView.setTranslationY(mDiffTotal);
            }

            mPrevScrollY = scrollY;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnHeaderListFragment newInstance() {
        QuickReturnHeaderListFragment fragment = new QuickReturnHeaderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderListFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height2);
//        mMinHeaderTranslation = -(mHeaderHeight) + QuickReturnUtils.getActionBarHeight(getActivity());

        mMinHeaderTranslation = -(mHeaderHeight);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_quick_return_header, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(mListViewOnScrollListener);

//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction())
//                {
//
//                    case MotionEvent.ACTION_DOWN:
//                        isActionUp = false;
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_DOWN");
////                        return true;
//                    case MotionEvent.ACTION_CANCEL:
////                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_CANCEL");
//                    case MotionEvent.ACTION_OUTSIDE:
////                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_OUTSIDE");
////                        return true;
//                    case MotionEvent.ACTION_UP:
//                        if(isActionUp){
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_UP : mDiffTotal - "+mDiffTotal);
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : mHeaderHeight - "+mHeaderHeight);
//
//
//                            if(-mDiffTotal >= mHeaderHeight/2){
//                                Log.d("QuickReturnFooterListFragment", "onTouch() : slide up");
//
////                                mAnim = new TranslateAnimation(0, 0, mHeaderHeight-mDiffTotal,
////                                        0);
//
//                                mAnim = new TranslateAnimation(0, 0, 0, mDiffTotal);
//
//                                mAnim.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                        RelativeLayout parent = (RelativeLayout) mQuickReturnTextView.getParent();
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-mQuickReturnTextView.getHeight(), mQuickReturnTextView.getWidth() , mQuickReturnTextView.getHeight());
//
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-QuickReturnUtils.dp2px(getActivity(), 80), mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , 0);
//
//                                        mQuickReturnTextView.setTranslationY(-mHeaderHeight);
//                                        mDiffTotal = -mHeaderHeight;
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//
//                                    }
//                                });
//                                mAnim.setDuration(300);
//                                mQuickReturnTextView.startAnimation(mAnim);
//
////                                mQuickReturnTextView.setTranslationY(-mHeaderHeight);
//
//
//
//                            } else {
////                            mQuickReturnTextView.setTranslationY(mFooterHeight);
//
//                                mAnim = new TranslateAnimation(0, 0, 0, -mDiffTotal);
//
//                                mAnim.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                        RelativeLayout parent = (RelativeLayout) mQuickReturnTextView.getParent();
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-mQuickReturnTextView.getHeight(), mQuickReturnTextView.getWidth() , mQuickReturnTextView.getHeight());
//
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-QuickReturnUtils.dp2px(getActivity(), 80), mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , 0);
//
//                                        mQuickReturnTextView.setTranslationY(0);
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//
//                                    }
//                                });
//                                mAnim.setDuration(300);
//                                mQuickReturnTextView.startAnimation(mAnim);
//
//                                Log.d("QuickReturnFooterListFragment", "onTouch() : slide down");
//
//                            }
//
////                        v.setBackgroundDrawable(null);
////                        Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
////                        startActivity(myIntent);
//
////                            return true;
//                        }
//
//                        isActionUp = true;
//
//                }
//                return false;
//            }
//
//
//        });
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mListView = (ListView) view.findViewById(android.R.id.list);
        mQuickReturnTextView = (TextView) view.findViewById(R.id.quick_return_tv);
    }
    // endregion
}
