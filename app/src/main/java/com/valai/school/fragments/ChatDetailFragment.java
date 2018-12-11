package com.valai.school.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.interfaces.DrawerLocker;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.utils.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class ChatDetailFragment extends BaseFragment {
    public static final String TAG = ChatDetailFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.chat_user)
    TextView chat_user;

    @BindView(R.id.edittext_chatbox)
    EditText edittext_chatbox;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.reveal_items)
    LinearLayout mRevealView;

    @BindView(R.id.menu1)
    LinearLayout menu1;

    @BindView(R.id.menu2)
    LinearLayout menu2;

    @BindView(R.id.menu3)
    LinearLayout menu3;

    @BindView(R.id.menu4)
    LinearLayout menu4;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private boolean hidden = true;

    public ChatDetailFragment() {
        // Required empty public constructor
    }

    public static ChatDetailFragment newInstance(String param1, String param2) {
        ChatDetailFragment fragment = new ChatDetailFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_chat_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chat_user.setText(mParam1);
        mRevealView.setVisibility(View.GONE);
        setRecycleItemTouchListener();
    }

    private void setRecycleItemTouchListener() {
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                recycler_view, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (!hidden) {
                    hideRevealView();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if (!hidden) {
                    hideRevealView();
                }
            }
        }));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @OnClick(R.id.img_back)
    void onBackButtonClick() {
        if (!hidden) {
            hideRevealView();
            return;
        }
        fragmentListner.onFragmentDetach(TAG);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_ChatBox_Send)
    void onSendButtonClick() {
        if (!hidden) {
            hideRevealView();
        }
    }

    @OnClick(R.id.menu1)
    void onDocButtonClick() {
        hideRevealView();
    }

    @OnClick(R.id.menu2)
    void onCameraButtonClick() {
        hideRevealView();
    }

    @OnClick(R.id.menu3)
    void onGalleryButtonClick() {
        hideRevealView();
    }

    @OnClick(R.id.menu4)
    void onRecordButtonClick() {
        hideRevealView();
    }

    @OnClick(R.id.relativeWhole)
    void onOutsideViewTouch() {
        if (!hidden) {
            hideRevealView();
        }
    }

    @OnClick(R.id.button_chatBox_Attach)
    void onAttachClick() {
        int cx = (mRevealView.getRight() - 200);
        int cy = mRevealView.getBottom();
        int radius = mRevealView.getWidth();

        //Below Android LOLIPOP Version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(600);

            SupportAnimator animator_reverse = animator.reverse();

            if (hidden) {
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
                setItemsAnimations();
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();
            }
        }
        // Android LOLIPOP And ABOVE Version
        else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;
                setItemsAnimations();
            } else {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                anim.start();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            ((DrawerLocker) getActivity()).setDrawerEnabled(true);
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

    private void hideRevealView() {
        if (mRevealView.getVisibility() == View.VISIBLE) {
            mRevealView.setVisibility(View.GONE);
            hidden = true;
        }
    }

    private void setItemsAnimations() {
        Animation popUp1 = AnimationUtils.loadAnimation(getContext(), R.anim.popup);
        Animation popUp2 = AnimationUtils.loadAnimation(getContext(), R.anim.popup);
        Animation popUp3 = AnimationUtils.loadAnimation(getContext(), R.anim.popup);
        Animation popUp4 = AnimationUtils.loadAnimation(getContext(), R.anim.popup);

        popUp1.setStartOffset(50);
        popUp2.setStartOffset(100);
        popUp3.setStartOffset(150);
        popUp4.setStartOffset(200);

        menu4.startAnimation(popUp1);
        menu3.startAnimation(popUp2);
        menu2.startAnimation(popUp3);
        menu1.startAnimation(popUp4);
    }
}