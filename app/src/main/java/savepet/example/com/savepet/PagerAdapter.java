package savepet.example.com.savepet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentRecycler_animales tab1 = new FragmentRecycler_animales();
                return tab1;
            default:
                return new FragmentRecycler_animales();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}