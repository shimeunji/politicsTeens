package kr.hs.e_mirim.politicsteens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by User on 2017-06-23.
 */

public class ContentAdapter extends BaseAdapter{

    Context context;
    ArrayList<ContentItem> contentItemArrayList;

    ImageView content_image;

    public ContentAdapter(Context context,ArrayList<ContentItem> contentItemArrayList)
    {
        this.context=context;
        this.contentItemArrayList=contentItemArrayList;
    }
    @Override
    public int getCount() {
        return this.contentItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contentItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            content_image=(ImageView)convertView.findViewById(R.id.img);
        }
        content_image.setImageResource(contentItemArrayList.get(position).getContent_image());
        return convertView;
    }
}
