package androidgroup.com.googleimagesearch.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidgroup.com.googleimagesearch.Models.ImageSearch;
import androidgroup.com.googleimagesearch.R;

/**
 * Created by Sowmya on 2/28/15.
 */
public class ImageAdapter extends ArrayAdapter<ImageSearch> {

    public ImageAdapter(Context context, List<ImageSearch> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageSearch imageSearch = (ImageSearch) getItem(position);
        final ImageViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ImageViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridlayout,parent,false);
            viewHolder.imgImage = (ImageView) convertView.findViewById(R.id.imgImage);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ImageViewHolder) convertView.getTag();
        }

        viewHolder.txtTitle.setText(Html.fromHtml(imageSearch.title));
        viewHolder.imgImage.setImageResource(0);
        Picasso.with(getContext()).load(imageSearch.tbUrl).into(viewHolder.imgImage);

        return  convertView;
    }


    public class ImageViewHolder {
          public ImageView imgImage;
          public TextView txtTitle;
    }
}