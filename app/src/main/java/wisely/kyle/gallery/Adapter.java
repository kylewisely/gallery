package wisely.kyle.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<File> image;

    Context context;

    public Adapter(ArrayList<File> image) {
        this.image = image;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context == null){
            context = viewGroup.getContext();
        }

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final File selected = image.get(i);
        if(selected.isDirectory()){
            ((MyViewHolder)viewHolder).textView.setText(selected.getName());
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            ((MyViewHolder)viewHolder).imageView.getLayoutParams().height = displayMetrics.widthPixels/3;
            ((MyViewHolder)viewHolder).imageView.getLayoutParams().width = displayMetrics.widthPixels/3;
            ((MyViewHolder)viewHolder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.folder));

           final String path = MainActivity.Show.path1;
            ((MyViewHolder)viewHolder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.Show.ShowImageList(path+"/"+selected.getName());
                }
            });

        }
        else {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            ((MyViewHolder)viewHolder).imageView.getLayoutParams().height = displayMetrics.widthPixels/2;
            ((MyViewHolder)viewHolder).imageView.getLayoutParams().width = displayMetrics.widthPixels/2;
            ((MyViewHolder)viewHolder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.folder));
           Picasso.get().load(image.get(i)).resize(500,500).centerCrop().into(((MyViewHolder)viewHolder).imageView);
            ((MyViewHolder)viewHolder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Image.class);
                    intent.putExtra("uri", selected.getAbsolutePath());
                    context.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public CardView cardView;
        public TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
