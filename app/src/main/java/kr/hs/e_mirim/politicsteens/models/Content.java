package kr.hs.e_mirim.politicsteens.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017-06-23.
 */

public class Content {
    public String uid;
    public String author;
    public String title;
    public String body;
    public String imageURL;
    public String link;

    public Content() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Content(String uid, String author, String title, String body, String imageURL, String link) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.imageURL = imageURL;
        this.link = link;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("imageURL",imageURL);
        result.put("link",link);

        return result;
    }
    // [END post_to_map]
}
