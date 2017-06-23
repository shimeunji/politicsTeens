package kr.hs.e_mirim.politicsteens.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import java.util.List;

import kr.hs.e_mirim.politicsteens.NaverNewsClient;
import kr.hs.e_mirim.politicsteens.NaverNewsRepo;
import kr.hs.e_mirim.politicsteens.PostDetailActivity;
import kr.hs.e_mirim.politicsteens.R;
import kr.hs.e_mirim.politicsteens.S;
import kr.hs.e_mirim.politicsteens.WebViewFragment;
import kr.hs.e_mirim.politicsteens.models.Post;
import kr.hs.e_mirim.politicsteens.viewholder.PostViewHolder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class PostListFragment_main extends Fragment {

    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    RelativeLayout layout;

    FragmentManager manager;  //Fragment를 관리하는 클래스의 참조변수
    FragmentTransaction tran;  //실제로 Fragment를 추가/삭제/재배치 하는 클래스의 참조변수

    View view;
    TextView title;
    TextView description;
    TextView pubDate;

    public PostListFragment_main() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list_home);
        mRecycler.setHasFixedSize(true);

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
                title=(TextView) rootView.findViewById(R.id.title);
                description=(TextView)rootView.findViewById(R.id.description);
                pubDate=(TextView)rootView.findViewById(R.id.pubDate);

                NaverNewsRepo result=response.body();
                List<NaverNewsRepo.Item> items=result.getItems();
                Log.d("11111111111",items.get(1).getTitle());
                title.setText(items.get(1).getTitle());
                //originallink.setText(items.get(1).getLink());
                //link.setText(items.get(1).getLink());
               // description.setText(items.get(1).getDescription());
                pubDate.setText(items.get(1).getPubDate());
                S.news_link=items.get(1).getLink();
            }

            @Override
            public void onFailure(Call<NaverNewsRepo> call, Throwable t) {
                Log.e(getClass().getName(),t.getMessage());
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.item_post_main,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.bbb);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.aaa);
                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
