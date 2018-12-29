package com.watson.pureenjoy.news.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    final String TAG = ViewPagerAdapter.class.getSimpleName();
    private FragmentManager fm;
    private List<String> titleList;
    private List<BaseFragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<String> titleList, List<BaseFragment> fragments) {
        super(fm);
        this.fm = fm;
        this.titleList = titleList;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        try{
            super.finishUpdate(container);
        } catch (Exception e){
            Log.d(TAG,"Catch the Exception in SpaceServiceAutoFragmentPagerAdapter.finishUpdate");
        }
    }

    public void setTitleAndFragments(List<String> titleList, List<BaseFragment> fragments) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fragments) {
            ft.remove(f);
        }
        try {
            ft.commit();
//            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.titleList.clear();
        this.titleList.addAll(titleList);
        this.fragments.clear();
        this.fragments.addAll(fragments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}