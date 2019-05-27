package savepet.example.com.savepet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import savepet.example.com.savepet.R;

public class HolderImagenes  extends RecyclerView.ViewHolder {
    ImageView imageView;

    public HolderImagenes(View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.ver_imagen);
    }
    public void bind(String imagen)
    {
        Picasso.get()
                .load(imagen)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
