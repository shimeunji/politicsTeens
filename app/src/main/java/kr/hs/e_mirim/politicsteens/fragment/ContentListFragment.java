package kr.hs.e_mirim.politicsteens.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import kr.hs.e_mirim.politicsteens.PostDetailActivity;
import kr.hs.e_mirim.politicsteens.R;
import kr.hs.e_mirim.politicsteens.models.Content;
import kr.hs.e_mirim.politicsteens.models.Post;
import kr.hs.e_mirim.politicsteens.viewholder.ContentViewHolder;
import kr.hs.e_mirim.politicsteens.viewholder.PostViewHolder;

/**
 * Created by User on 2017-06-23.
 */

public abstract class ContentListFragment extends Fragment {
    private static final String TAG = "ContentListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Content, ContentViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    DatabaseReference userPostRef;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_contents, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

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
        Query contentsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Content, ContentViewHolder>(Content.class, R.layout.item_content,
                ContentViewHolder.class, contentsQuery) {
            @Override
            protected void populateViewHolder(final ContentViewHolder viewHolder, final Content model, final int position) {
                final DatabaseReference contentRef = getRef(position);

                // Set click listener for the whole post view

                final String contentKey = contentRef.getKey();
                DatabaseReference globalPostRef = mDatabase.child("contents").child(contentRef.getKey());


                //Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToContent(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored

                    }
                });
                viewHolder.itemView.findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        userPostRef = mDatabase.child("user-contents").child(model.uid).child(contentRef.getKey()).child("link");

                        Log.d("TAG","___userPostRef : "+userPostRef);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com")));
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }//
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
