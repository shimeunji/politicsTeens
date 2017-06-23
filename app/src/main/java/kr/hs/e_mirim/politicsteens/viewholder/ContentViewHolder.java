package kr.hs.e_mirim.politicsteens.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.hs.e_mirim.politicsteens.R;
import kr.hs.e_mirim.politicsteens.models.Content;
import kr.hs.e_mirim.politicsteens.models.Post;

/**
 * Created by User on 2017-06-23.
 */

public class ContentViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public TextView bodyView;
    public String imgURL;
    public String link;

    public ContentViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.title);
        //authorView = (TextView) itemView.findViewById(R.id.content_author);
        bodyView = (TextView) itemView.findViewById(R.id.content);

    }

    public void bindToContent(Content content, View.OnClickListener starClickListener) {
        titleView.setText(content.title);
       // authorView.setText(content.author);
        bodyView.setText(content.body);
    }
}
