package com.watson.pureenjoy.news.app.listener;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPAND,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0){
            if (mCurrentState!= State.EXPAND){
                onStateChanged(appBarLayout,State.EXPAND);
            }
            mCurrentState = State.EXPAND;
        }else if (Math.abs(verticalOffset)>= appBarLayout.getTotalScrollRange()){
            if (mCurrentState != State.COLLAPSED){
                onStateChanged(appBarLayout,State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        }else {
            if (mCurrentState != State.IDLE){
                onStateChanged(appBarLayout,State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
