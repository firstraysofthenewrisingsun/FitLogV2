package com.example.anameplease.fitlogalpha;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.FragmentRpeCalcBinding;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RPE_Calc_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RPE_Calc_Fragment extends Fragment implements AdapterView.OnItemSelectedListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentRpeCalcBinding binding;

    private List<Double[]> rpe_scale = new ArrayList<>();

    private HashMap<Double, ArrayList> RPE;

    private int effort;
    private int reps;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;


    public RPE_Calc_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RPE_Calc_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RPE_Calc_Fragment newInstance(String param1, String param2) {
        RPE_Calc_Fragment fragment = new RPE_Calc_Fragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rpe__calc_, container, false);
        View view = binding.getRoot();

        final Context context  = getContext();

        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

            rpe_scale.add(new Double[] {0.74,0.76,0.79,0.81,0.84,0.86,0.89,0.92,0.96,1.0});
            rpe_scale.add(new Double[] {0.72,0.75,0.77,0.8,0.82,0.85,0.88,0.91,0.94,0.98});
            rpe_scale.add(new Double[] {0.71,0.74,0.76,0.79,0.81,0.84,0.86,0.89,0.92,0.96});
            rpe_scale.add(new Double[] {0.69,0.72,0.75,0.77,0.8,0.82,0.85,0.88,0.91,0.94});
            rpe_scale.add(new Double[] {0.68,0.71,0.74,0.76,0.79,0.81,0.84,0.86,0.89,0.92});
            rpe_scale.add(new Double[] {0.67,0.69,0.72,0.75,0.77,0.8,0.82,0.85,0.88,0.91});
            rpe_scale.add(new Double[] {0.65,0.68,0.71,0.74,0.76,0.79,0.81,0.84,0.86,0.89});
            rpe_scale.add(new Double[] {0.64,0.67,0.69,0.72,0.75,0.77,0.8,0.82,0.85,0.88});


        ArrayAdapter<CharSequence> fromResource = ArrayAdapter.createFromResource(getContext(), R.array.rpe_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.rep_array, android.R.layout.simple_spinner_item );

        fromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        binding.rpeSpinner.setAdapter(fromResource);
        binding.rpeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){

                    case "Maximal effort (10)":

                        effort = 0;

                        break;
                    case "Maybe 1 more in the tank (9.5)":

                        effort = 1;

                        break;
                    case "Definitely 1 more in the tank (9)":

                        effort = 2;
                        break;
                    case "Maybe 2 more in the tank (8.5)":

                        effort = 3;
                        break;
                    case "Definitely 2 in the tank (8)":

                        effort = 4;
                        break;
                    case  "3 more reps in the tank (7.5)":

                        effort = 5;
                        break;
                    case "Fairly quick (7)":

                        effort = 6;
                        break;
                    case "Borderline warm up (6.5)":

                        effort = 7;
                        break;
                    case "Warm up weight (6)":

                        effort = 7;
                        break;
                    case  "Too easy (5.5)":

                        effort = 7;

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.rpeSpinner1.setAdapter(charSequenceArrayAdapter);
        binding.rpeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){

                    case "(10)":

                        reps = 0;
                        break;
                    case "(9)":

                        reps = 1;
                        break;
                    case "(8)":

                        reps = 2;
                        break;
                    case "(7)":

                        reps = 3;
                        break;
                    case "(6)":

                        reps = 4;
                        break;
                    case  "(5)":

                        reps = 5;
                        break;
                    case "(4)":

                        reps = 6;
                        break;
                    case "(3)":

                        reps = 7;
                        break;
                    case "(2)":

                        reps = 8;
                        break;
                    case  "(1)":

                        reps = 9;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Calculate")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(1));

        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(context, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

        return view;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (item.getLabel()){

            case "Calculate":





                if (binding.editText3.getText().toString() == null || binding.editText3.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(), "Enter weight", Toast.LENGTH_LONG).show();

                } else {


                    final double rpe_intensity = rpe_scale.get(effort)[reps];
                    double weight = Double.parseDouble(binding.editText3.getText().toString());
                    double new_weight = weight * rpe_intensity / 100.0;
                    new_weight *= 100;

                    binding.textView8.setText(String.valueOf(new_weight));
                }
                break;


        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

    }
}
