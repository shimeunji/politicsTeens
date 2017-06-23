package kr.hs.e_mirim.politicsteens;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.e_mirim.politicsteens.CustomAdapter;
import kr.hs.e_mirim.politicsteens.PostItem;
import kr.hs.e_mirim.politicsteens.PostListAdapter;
import kr.hs.e_mirim.politicsteens.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RelativeLayout layout;

    FragmentManager manager;  //Fragment를 관리하는 클래스의 참조변수
    FragmentTransaction tran;  //실제로 Fragment를 추가/삭제/재배치 하는 클래스의 참조변수

    View view;
    TextView title;
    TextView description;
    TextView pubDate;
    private static final String MOVIE_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";

    public HomeFragment() {
        // Required empty public constructor
        Log.d("========LOG======","t시발");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("========LOG======","t시발");

        layout=(RelativeLayout)view.findViewById(R.id.news_webview);

        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        String API_URL="https://openapi.naver.com/";
        manager = (FragmentManager) getFragmentManager();
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.client(
                httpClient.build()
        ).build();

        NaverNewsClient client=retrofit.create(NaverNewsClient.class);

        // Call<List<GitHubRepo>> call= client.reposForUser("shimeunji");
        Call<NaverNewsRepo> call=client.getAllNews();

        call.enqueue(new Callback<NaverNewsRepo>() {
            @Override
            public void onResponse(Call<NaverNewsRepo> call, Response<NaverNewsRepo> response) {
                /*List<GitHubRepo> result=response.body();
                S.gitHubRepos=new ArrayList<GitHubRepo>(result);
                adapter=new GitAdapter(getActivity().getApplicationContext(),R.layout.git_listview_item,S.gitHubRepos);
                listView.setAdapter(adapter);*/
                title=(TextView) view.findViewById(R.id.title);
                description=(TextView)view.findViewById(R.id.description);
                pubDate=(TextView)view.findViewById(R.id.pubDate);

                NaverNewsRepo result=response.body();
                List<NaverNewsRepo.Item> items=result.getItems();
                Log.d("11111111111",items.get(1).getTitle());
                title.setText(items.get(1).getTitle());
                //originallink.setText(items.get(1).getLink());
                //link.setText(items.get(1).getLink());
                description.setText(items.get(1).getDescription());
                pubDate.setText(items.get(1).getPubDate());
                S.news_link=items.get(1).getLink();

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tran=manager.beginTransaction();
                        Fragment fragment=new WebViewFragment();
                        tran.replace(R.id.home_frag,fragment);
                        tran.addToBackStack(null);
                        tran.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<NaverNewsRepo> call, Throwable t) {
                Log.e(getClass().getName(),t.getMessage());
            }
        });

        return view;
    }

}
