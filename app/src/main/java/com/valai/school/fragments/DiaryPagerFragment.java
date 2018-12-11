package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.DiaryAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetDiaryPOJO;
import com.valai.school.modal.GetEventDetailsPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.FILE_ROOT_PATH_ASSIGNMENT;
import static com.valai.school.utils.AppConstants.FILE_ROOT_PATH_CIRCULAR;
import static com.valai.school.utils.AppConstants.MODE_GET_MESSAGE;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.convertDateStringFormat2;
import static com.valai.school.utils.CommonUtils.getAnimationItems;

public class DiaryPagerFragment extends BaseFragment {
    public static final String TAG = DiaryPagerFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListener;
    private GetDiaryPOJO.Data data;
    private int position;
    private View ChildView;
    private int recyclerViewItemPosition;
    private int msgId;
    private List<StudentListPOJO.Datum> studentList;

    public DiaryPagerFragment() {
        // Required empty public constructor
    }

    public static DiaryPagerFragment newInstance(GetDiaryPOJO.Data data, int pos) {
        DiaryPagerFragment fragment = new DiaryPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, data);
        args.putInt(ARG_PARAM2, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (GetDiaryPOJO.Data) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diary_pager, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentList = new ArrayList<>();
        studentList = fragmentListener.getStudentList();

        AnimationItem[] mAnimationItems = getAnimationItems();
        AnimationItem mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        setOnItemClickListener();
        setAdapter();
    }

    private void setOnItemClickListener() {
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

                    if (position == 0) {
                        msgId = data.getJan().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 1) {
                        msgId = data.getFeb().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 2) {
                        msgId = data.getMar().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 3) {
                        msgId = data.getApr().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 4) {
                        msgId = data.getMay().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 5) {
                        msgId = data.getJun().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 6) {
                        msgId = data.getJul().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 7) {
                        msgId = data.getAug().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 8) {
                        msgId = data.getSep().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 9) {
                        msgId = data.getOct().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 10) {
                        msgId = data.getNov().get(recyclerViewItemPosition).getMsgId();
                    } else if (position == 11) {
                        msgId = data.getDec().get(recyclerViewItemPosition).getMsgId();
                    }

                    if (msgId != 0) {
                        getEventDetailRequestCall();
                    }
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
    }

    private void setAdapter() {
        DiaryAdapter circularAdapter;
        if (position == 0) {
            if (data != null && data.getJan() != null && data.getJan().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getJan(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 1) {
            if (data != null && data.getFeb() != null && data.getFeb().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getFeb(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 2) {
            if (data != null && data.getMar() != null && data.getMar().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getMar(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 3) {
            if (data != null && data.getApr() != null && data.getApr().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getApr(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 4) {
            if (data != null && data.getMay() != null && data.getMay().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getMay(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 5) {
            if (data != null && data.getJun() != null && data.getJun().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getJun(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 6) {
            if (data != null && data.getJul() != null && data.getJul().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getJul(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 7) {
            if (data != null && data.getAug() != null && data.getAug().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getAug(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 8) {
            if (data != null && data.getSep() != null && data.getSep().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getSep(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 9) {
            if (data != null && data.getOct() != null && data.getOct().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getOct(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 10) {
            if (data != null && data.getNov() != null && data.getNov().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getNov(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        } else if (position == 11) {
            if (data != null && data.getDec() != null && data.getDec().size() > 0) {
                recycler_view.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                circularAdapter = new DiaryAdapter(getContext(), data.getDec(), position);
                recycler_view.setAdapter(circularAdapter);
            } else {
                recycler_view.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }
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
            fragmentListener = (FragmentListner) context;
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

    // Request Rest Client API Call For SignIn
    public void getEventDetailRequestCall() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetEventDetailsPOJO> call = restClientAPI.getEventDetailsForParent(studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(), studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                msgId, studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                MODE_GET_MESSAGE);
        call.enqueue(new Callback<GetEventDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetEventDetailsPOJO> call, @NonNull Response<GetEventDetailsPOJO> response) {
                GetEventDetailsPOJO getEventDetailsPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getEventDetailsPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getEventDetailsPOJO.getResponseStatus().equals(TRUE)) {
                            showDialog(getEventDetailsPOJO.getData());
                        } else {
                            showMessage(getEventDetailsPOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(getEventDetailsPOJO.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetEventDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showDialog(final List<GetEventDetailsPOJO.Datum> data) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog);

        String start_date = convertDateStringFormat2(data.get(0).getStart());
        String end_date = convertDateStringFormat2(data.get(0).getEnd());

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(Html.fromHtml(data.get(0).getTitle()));
        TextView tvDes = dialog.findViewById(R.id.tvDes);
        tvDes.setText(Html.fromHtml(data.get(0).getDescription()));
        TextView tvStartDate = dialog.findViewById(R.id.tvStartDate);
        tvStartDate.setText(getContext().getString(R.string.start_date_txt) + start_date);
        TextView tvEndDate = dialog.findViewById(R.id.tvEndDate);
        tvEndDate.setText(getContext().getString(R.string.end_date_txt) + end_date);

        ImageView imgDownload = dialog.findViewById(R.id.imgDownload);

        if (!data.get(0).getAttachment().equals("") && !data.get(0).getGenFile().equals("")) {
            imgDownload.setVisibility(View.VISIBLE);
        } else {
            imgDownload.setVisibility(View.GONE);
        }

        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        + File.separator
                        + getString(R.string.app_name) + File.separator, data.get(0).getAttachment());

                if (outputFile.exists()) {
                    fragmentListener.getFileDownloadCall("", data.get(0).getAttachment());
                } else {
                    if (data.get(0).getGenFile().startsWith("Cir")) {
                        fragmentListener.getFileDownloadCall(ROOT_DOWNLOAD
                                + ROOT_GROUP_FOLDER
                                + studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getOrgId()
                                + FILE_ROOT_PATH_CIRCULAR
                                + data.get(0).getGenFile(), data.get(0).getAttachment());
                    } else {
                        fragmentListener.getFileDownloadCall(ROOT_DOWNLOAD
                                + ROOT_GROUP_FOLDER
                                + studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getOrgId()
                                + FILE_ROOT_PATH_ASSIGNMENT
                                + data.get(0).getGenFile(), data.get(0).getAttachment());
                    }
                }
            }
        });

        dialog.show();
    }
}