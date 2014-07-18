package localnews.nordmedia.de.localnews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import localnews.nordmedia.de.localnews.image.LoadBitmapTask;
import localnews.nordmedia.de.localnews.news.News;

import static localnews.nordmedia.de.localnews.Views.*;

/**
 * A fragment representing a single News detail screen.
 * This fragment is either contained in a {@link NewsListActivity}
 * in two-pane mode (on tablets) or a {@link NewsDetailActivity}
 * on handsets.
 */
public class NewsDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_NEWS = "news";


    private News news;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle arguments = getArguments();

        news = (News) arguments.getSerializable(ARG_NEWS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        news = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        final ViewHolder viewHolder = new ViewHolder(rootView);
        new LoadBitmapTask() {
            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                viewHolder.image.setImageBitmap(bitmap);
            }
        }.execute(news.imageUrl);
        viewHolder.title.setText(news.title);
        viewHolder.content.setText(news.teaser);
        viewHolder.url.setText(Html.fromHtml(getString(R.string.label_link_url, news.url)));
        return rootView;
    }

    static class ViewHolder {
        final TextView title, content, url;
        final ImageView image;
        final View view;

        ViewHolder(View view) {
            this.view = view;
            title = find(view, R.id.title);
            content = find(view, R.id.content);
            url = find(view, R.id.link);
            url.setMovementMethod(LinkMovementMethod.getInstance());
            image = find(view, R.id.image);
        }
    }
}
