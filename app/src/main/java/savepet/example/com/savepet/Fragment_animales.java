package savepet.example.com.savepet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_animales extends Fragment {
    TabLayout tabs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animales,container,false);
        tabs = (TabLayout) view.findViewById(R.id.tablayout);
        //añadimos elementos a el tab
        tabs.addTab(tabs.newTab().setText("Animales"));
        tabs.addTab(tabs.newTab().setText("Mis Animales"));
        final ViewPager mviewPager = (ViewPager) view.findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabs.getTabCount());
        mviewPager.setAdapter(adapter);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        //texto=(TextView)findViewById(R.id.textView);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
}
