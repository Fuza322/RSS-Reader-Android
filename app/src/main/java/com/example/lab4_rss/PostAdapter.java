package com.example.lab4_rss;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post>
{
    private Activity myContext;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, int textViewResourceId, ArrayList<Post> objects)
    {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        posts = objects;
    }

    static class ViewHolder
    {
        TextView postTitleView;
        TextView postDateView;
        ImageView postImageView;
        String postImageURL;
        Bitmap bitmapImage;
        String cachedBitmap;
        ImageView postCachedView;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {

            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.post, null);

            viewHolder = new ViewHolder();
            viewHolder.postImageView = convertView
                    .findViewById(R.id.postThumb);
            viewHolder.postTitleView = convertView
                    .findViewById(R.id.postTitleLabel);
            viewHolder.postDateView =  convertView
                    .findViewById(R.id.postDateLabel);
            viewHolder.postCachedView = convertView
                    .findViewById(R.id.cacheMode);
            viewHolder.postCachedView.setTag(position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.cachedBitmap = null;
            viewHolder.postCachedView.setTag(position);
        }
        Post post = posts.get(position);
        viewHolder.postImageView.setImageResource(R.drawable.ic_photo_black_24dp);

        if (post.getCachedBitmapString() != null)
        {
            viewHolder.cachedBitmap = post.getCachedBitmapString();
        }
        else {
            viewHolder.postImageURL = post.getImage();
        }

        if (post.isCached){
            viewHolder.postCachedView.setImageResource(R.drawable.cached);
        }else{
            viewHolder.postCachedView.setImageResource(R.drawable.uncached);
        }

        new DownloadImageTask().execute(viewHolder);
        viewHolder.postTitleView.setText(post.getTitle());
        viewHolder.postDateView.setText(post.getDate().substring(0, 25));
        return convertView;
    }

    void magic(int position){

    }

    private class DownloadImageTask extends AsyncTask<ViewHolder, Void, ViewHolder>
    {
        @Override
        protected ViewHolder doInBackground(ViewHolder... params)
        {
            ViewHolder viewHolder = params[0];
            if (viewHolder.cachedBitmap != null)
            {
                byte[] b = Base64.decode(viewHolder.cachedBitmap, Base64.DEFAULT);
                viewHolder.bitmapImage = BitmapFactory.decodeByteArray(b, 0, b.length);
            } else {
                try
                {
                    URL imageURL = new URL(viewHolder.postImageURL);
                    viewHolder.bitmapImage = BitmapFactory.decodeStream(imageURL.openStream());
                } catch (IOException e)
                {
                    viewHolder.bitmapImage = null;
                }
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result)
        {
            if (result.bitmapImage != null)
            {
                result.postImageView.setImageBitmap(result.bitmapImage);

            }
        }
    }
}