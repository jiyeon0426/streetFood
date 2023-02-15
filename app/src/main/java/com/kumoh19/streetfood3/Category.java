package com.kumoh19.streetfood3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment fg;

    public Category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category.
     */
    // TODO: Rename and change types and number of parameters
    public static Category newInstance(String param1, String param2) {
        Category fragment = new Category();
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
            childFt.replace(R.id.child_fragment2, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        
        // 붕어빵 버튼
        ImageButton btn_fish = (ImageButton) root.findViewById(R.id.btn_fish);
        btn_fish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fg = CategorymapFragment.newInstance("붕어빵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_corn= (ImageButton) root.findViewById(R.id.btn_corn);
        btn_corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fg = CategorymapFragment.newInstance("찐옥수수", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_dalgona= (ImageButton) root.findViewById(R.id.btn_dalgona);
        btn_dalgona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fg = CategorymapFragment.newInstance("달고나", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_toast= (ImageButton) root.findViewById(R.id.btn_toast);
        btn_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("토스트", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_sweet= (ImageButton) root.findViewById(R.id.btn_sweet);
        btn_sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("군고구마", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_kukhwa= (ImageButton) root.findViewById(R.id.btn_kukhwa);
        btn_kukhwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("국화빵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_peanut= (ImageButton) root.findViewById(R.id.btn_peanut);
        btn_peanut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("땅콩빵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_kkochi= (ImageButton) root.findViewById(R.id.btn_kkochi);
        btn_kkochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("꼬치", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_waffle= (ImageButton) root.findViewById(R.id.btn_waffle);
        btn_waffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("와플", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_sudae= (ImageButton) root.findViewById(R.id.btn_sudae);
        btn_sudae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("순대", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_ttekpokke= (ImageButton) root.findViewById(R.id.btn_ttekpokke);
        btn_ttekpokke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("떡볶이", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_aumuk= (ImageButton) root.findViewById(R.id.btn_aumuk);
        btn_aumuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("어묵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_egg= (ImageButton) root.findViewById(R.id.btn_egg);
        btn_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("계란빵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_tacoyaki= (ImageButton) root.findViewById(R.id.btn_takoyaki);
        btn_tacoyaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("문어빵", null);
                setChildFragment(fg);
            }
        });

        ImageButton btn_hodduck= (ImageButton) root.findViewById(R.id.btn_hodduk);
        btn_hodduck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fg = CategorymapFragment.newInstance("호떡", null);
                setChildFragment(fg);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}