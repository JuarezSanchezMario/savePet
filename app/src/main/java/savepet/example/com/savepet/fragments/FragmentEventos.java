package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import savepet.example.com.savepet.R;
import savepet.example.com.savepet.pager_adapters.PagerAdapterEventos;

public class FragmentEventos extends Fragment {
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        tabs = (TabLayout) view.findViewById(R.id.tablayout);

        tabs.addTab(tabs.newTab().setText("Eventos"));
        tabs.addTab(tabs.newTab().setText("Mis eventos"));
        final ViewPager mviewPager = (ViewPager) view.findViewById(R.id.viewpager);
        final PagerAdapterEventos adapter = new PagerAdapterEventos
                (getFragmentManager(), tabs.getTabCount(), getActivity());
        mviewPager.setAdapter(adapter);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


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
