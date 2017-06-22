package kr.hs.e_mirim.politicsteens.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import kr.hs.e_mirim.politicsteens.CustomAdapter;
import kr.hs.e_mirim.politicsteens.PostItem;
import kr.hs.e_mirim.politicsteens.PostListAdapter;
import kr.hs.e_mirim.politicsteens.R;

public class RecentPostsFragment_main extends PostListFragment_main {

    public RecentPostsFragment_main() {}

    ListView listView;
    PostListAdapter postListAdapter;
    ArrayList<PostItem> postItemArrayList;
    VideoView videoView;
    private static final String MOVIE_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";

    /*
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

        return view;
    }
    */
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(3);
        // [END recent_posts_query]


        return recentPostsQuery;
    }
}