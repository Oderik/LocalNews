package localnews.nordmedia.de.localnews;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import localnews.nordmedia.de.localnews.image.LoadBitmapTask;
import localnews.nordmedia.de.localnews.news.News;
import localnews.nordmedia.de.localnews.news.NewsFeed;

import static localnews.nordmedia.de.localnews.Views.*;

/**
 * Created by Oderik on 17.07.2014.
 */
public class NewsAdapter extends BaseAdapter {

    private List<News> data = Collections.emptyList();

    public NewsAdapter() {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public News getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder = obtainViewHolder(view, viewGroup);

        bind(holder, getItem(i));

        return holder.view;
    }

    private void bind(final ViewHolder holder, final News item) {
        holder.title.setText(item.title);
        holder.image.setImageResource(R.drawable.news_placeholder);

        if (item.imageUrl != null) {
            holder.image.setTag(item.imageUrl);
            new LoadBitmapTask() {
                @Override
                protected void onPostExecute(final Bitmap bitmap) {
                    if (bitmap != null && item.imageUrl.equals(holder.image.getTag())) {
                        holder.image.setImageBitmap(bitmap);
                    }
                }
            }.execute(item.imageUrl);
        }
    }

    private ViewHolder obtainViewHolder(View view, ViewGroup viewGroup) {
        if (view != null) {
            return (ViewHolder) view.getTag();
        }
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.listitem_teaser, viewGroup, false));
    }

    public void setData(List<News> data) {
        if (this.data != data) {
            if (data == null) {
                this.data = Collections.emptyList();
                notifyDataSetInvalidated();
            } else {
                this.data = data;
                notifyDataSetChanged();
            }
        }
    }

    public void setData(NewsFeed data) {
        setData(data.news);
    }

    static class ViewHolder {
        public final View view;
        private final TextView title;
        private final ImageView image;

        ViewHolder(View view) {
            this.view = view;
            view.setTag(this);
            title = find(view, R.id.title);
            image = find(view, R.id.image);
        }

    }

}
