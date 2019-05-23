package savepet.example.com.savepet.pager_adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import savepet.example.com.savepet.fragments.FragmentRecyclerMensajes;


public class PagerAdapterMensajes extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Context context;

    public PagerAdapterMensajes(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        switch (position) {
            case 0:
                FragmentRecyclerMensajes tab1 = new FragmentRecyclerMensajes();
                return tab1;
            case 1:
                FragmentRecyclerMensajes tab2 = new FragmentRecyclerMensajes();
                    args.clear();
                    args.putBoolean("propios", true);
                    tab2.setArguments(args);
                    return tab2;
            default:
                FragmentRecyclerMensajes def = new FragmentRecyclerMensajes();
                return def;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}