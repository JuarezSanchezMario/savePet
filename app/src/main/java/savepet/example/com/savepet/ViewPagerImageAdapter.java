package savepet.example.com.savepet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewPagerImageAdapter extends PagerAdapter {
    private Context context;
    private String[] imagenUrls;

    ViewPagerImageAdapter(Context context,String[] imagenUrls)
    {
        this.context = context;
        this.imagenUrls = imagenUrls;
    }
    @Override
    public int getCount() {
        return imagenUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(imagenUrls[position])
                .fit()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.not_found)
                .centerCrop()
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }
}
