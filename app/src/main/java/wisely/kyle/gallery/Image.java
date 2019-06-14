package wisely.kyle.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class Image extends AppCompatActivity {

    ImageView imageView;
    ImageButton backButton;
    TextView namaGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.image_View);
        namaGambar = findViewById(R.id.namaGambar);
        backButton = findViewById(R.id.backButton);
        String uri = getIntent().getExtras().getString("uri");



        File file = new File(uri);
        Toast.makeText(this, uri, Toast.LENGTH_SHORT).show();
        Picasso.get().load(file).into(imageView);

        namaGambar.setText(file.getName());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
