package savepet.example.com.savepet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import savepet.example.com.savepet.fragments.FragmentRecyclerAnimales;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Context context;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        switch (position) {
            case 0:
                FragmentRecyclerAnimales tab1 = new FragmentRecyclerAnimales();
                return tab1;
            case 1:
                    FragmentRecyclerAnimales tab2 = new FragmentRecyclerAnimales();
                    args.clear();
                    args.putBoolean("propios", true);
                    tab2.setArguments(args);
                    return tab2;
            default:
                FragmentRecyclerAnimales def = new FragmentRecyclerAnimales();
                return def;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}