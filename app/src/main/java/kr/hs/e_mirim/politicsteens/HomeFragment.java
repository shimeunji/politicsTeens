package kr.hs.e_mirim.politicsteens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ListView listView;
    PostListAdapter postListAdapter;
    ArrayList<PostItem> postItemArrayList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        final ViewPager viewPager=(ViewPager)view.findViewById(R.id.pager);
        CustomAdapter adapter=new CustomAdapter(getActivity().getLayoutInflater());
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(getResources().getDisplayMetrics().widthPixels/-20);
        viewPager.setOffscreenPageLimit(2);

        listView=(ListView)view.findViewById(R.id.listview);
        postItemArrayList=new ArrayList<PostItem>();

        postItemArrayList.add(new PostItem(R.drawable.write1));
        postItemArrayList.add(new PostItem(R.drawable.write2));
        postItemArrayList.add(new PostItem(R.drawable.write3));
        postItemArrayList.add(new PostItem(R.drawable.write4));

        postListAdapter=new PostListAdapter(getContext(),postItemArrayList);
        listView.setAdapter(postListAdapter);

        return view;
    }

}
