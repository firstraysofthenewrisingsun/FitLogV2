package com.example.anameplease.fitlogalpha;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.FragmentUpperMaxBinding;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpperMaxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpperMaxFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FragmentUpperMaxBinding binding;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();

    private appFunc heyump = new appFunc();


    public UpperMaxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpperMaxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpperMaxFragment newInstance(String param1, String param2) {
        UpperMaxFragment fragment = new UpperMaxFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upper_max, container, false);
        View view = binding.getRoot();


        final Context context  = getContext();

        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Calculate")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(1));

        items.add(new RFACLabelItem<Integer>().setLabel("Clear")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(2));


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
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch(item.getLabel()){
            case "Calculate":

                if (TextUtils.isEmpty(binding.editText2.getText())){
                    Toast toast1 = Toast.makeText(getContext(), "Please enter the appropriate data", Toast.LENGTH_LONG);
                    toast1.show();
                } else {

                    Double answ = Double.valueOf(binding.editText2.getText().toString());
                    String s = heyump.upperCalc(answ, getContext());

                    binding.txtvwLog.setText(s);
                }

                break;

            case "Clear":
                binding.editText2.setText("");
                binding.txtvwLog.setText("Cleared");

                break;


        }

    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

    }
}
