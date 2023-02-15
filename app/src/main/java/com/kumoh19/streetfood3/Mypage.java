package com.kumoh19.streetfood3;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mypage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mypage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment fg;

    public Mypage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mypage.
     */
    // TODO: Rename and change types and number of parameters
    public static Mypage newInstance(String param1, String param2) {
        Mypage fragment = new Mypage();
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
    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment3, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
    String strNickname, strProfile, strId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        TextView tvNickname = view.findViewById(R.id.profile_name);
        ImageView ivProfile = view.findViewById(R.id.profile_image);

        //데이터 받기 -> 실패
//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            bundle = getArguments();
//            strNickname = bundle.getString("name");
//            tvNickname.setText(strNickname);
//            //strProfile = bundle.getString("profile");
//        }

        // Inflate the layout for this fragment
        strId = ((HomeActivity)getActivity()).strId;
        strNickname = ((HomeActivity)getActivity()).strNickname;
        strProfile = ((HomeActivity)getActivity()).strProfile;
        //Log.d("HomeActivity","myseo: nickname" + strNickname);
        tvNickname.setText(strNickname);
        Glide.with(this).load(strProfile).into(ivProfile);

        // MY등록 버튼
        Button btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = MyregisterFragment.newInstance(strId, null);
                setChildFragment(fg);
            }
        });

        // 로그아웃 버튼
        Button btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

        return view;
    }
}