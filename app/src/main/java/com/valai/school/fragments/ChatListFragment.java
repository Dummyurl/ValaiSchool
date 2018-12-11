package com.valai.school.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.ChatAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.utils.AnimationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class ChatListFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View ChildView;
    private int recyclerViewItemPosition;
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private ArrayList<String> Names;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private AnimationItem mSelectedItem;

    public ChatListFragment() {
        // Required empty public constructor
    }

    public static ChatListFragment newInstance() {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Names = new ArrayList<>();
        Names.add("Admin");
        Names.add("Maths Teacher");
        Names.add("English Teacher");
        Names.add("Science Teacher");
        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[2];

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    recyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    fragmentListner.onFragmentAttach(ChatDetailFragment.newInstance(Names.get(recyclerViewItemPosition), ""),
                            ChatDetailFragment.TAG);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //setAdapter();
    }

    private void setAdapter() {
        if (Names != null && Names.size() > 0) {
            ChatAdapter chatAdapter = new ChatAdapter(Names);
            recycler_view.setAdapter(chatAdapter);
            runLayoutAnimation(recycler_view, mSelectedItem);
        } else {
            recycler_view.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}