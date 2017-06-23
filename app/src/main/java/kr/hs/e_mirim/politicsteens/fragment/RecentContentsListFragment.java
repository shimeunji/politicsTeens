package kr.hs.e_mirim.politicsteens.fragment;

import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by User on 2017-06-23.
 */

public class RecentContentsListFragment extends ContentListFragment {
    public RecentContentsListFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("contents")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
