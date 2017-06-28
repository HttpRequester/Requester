package ir.bpadashi.requester.model;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by BabakPadashi on 28/03/2017.
 */
public class ParentContext {

    private Context activity;
    private Fragment fragment;


    public ParentContext(Context activity) {
        this.activity = activity;
    }

    public ParentContext(Fragment fragment) {
        this.fragment = fragment;
    }

    public Context getActivity() {

        if (fragment != null)
            return fragment.getActivity();

        return activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

}
