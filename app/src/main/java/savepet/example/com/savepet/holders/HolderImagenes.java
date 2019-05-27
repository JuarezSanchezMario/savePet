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
        imageView = (ImageView) itemView.findViewById(R.id.imagen_animal_card);
    }
    public void bind(String imagen)
    {
        Picasso.get()
                .load(imagen)
                .fit()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imageView);
    }
}
