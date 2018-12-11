package com.valai.school.fragmentsAdmin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerAdmin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListFragmentAdmin extends BaseFragment {
    public static final String TAG = ChatListFragmentAdmin.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private FragmentListnerAdmin fragmentListner;

    public ChatListFragmentAdmin() {
        // Required empty public constructor
    }

    public static ChatListFragmentAdmin newInstance(String param1, String param2) {
        ChatListFragmentAdmin fragment = new ChatListFragmentAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNotFound.setVisibility(View.VISIBLE);
        viewpager.setVisibility(View.GONE);
        tabs.setVisibility(View.GONE);
        //viewpager.setOffscreenPageLimit(2);
        //setupViewPager(viewpager);
        //tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new ParentFragmentCircularAdmin(), getString(R.string.parent_text));
        adapter.addFragment(new StaffFragmentCircularAdmin(), getString(R.string.staff_text));
        viewPager.setAdapter(adapter);
    }

    private class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListnerAdmin) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}