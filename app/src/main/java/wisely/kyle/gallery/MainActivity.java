package wisely.kyle.gallery;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Show.image = new ArrayList<>();
        Show.folder = new ArrayList<>();
        Show.recyclerView = findViewById(R.id.gallery);
        Show.spinner = findViewById(R.id.spinner);
        Show.namafolder  = findViewById(R.id.namaFolder);
        Show.backButton= findViewById(R.id.backButton);
        context = getApplicationContext();

        String[] basicoradvance = {"Basic","Advance"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,basicoradvance);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Show.spinner.setAdapter(adapter);

        Show.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item  = parent.getItemAtPosition(position).toString();

                if(item == "Basic" && isStoragePermissionGranted()){
                    Show.ShowImageList(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Basic");
                }
                else if (item == "Advance" && isStoragePermissionGranted())
                {
                    Show.ShowImageList(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Advance");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public static Context getAppContext(){
        return context;
    }
        public static class Show{
            public static File file;
            public static File[] listFile;
            public static ArrayList<File> image;
            public static ArrayList<File> folder;
            public static RecyclerView recyclerView;
            public static Spinner spinner;
            public static String path1;
            public static TextView namafolder;
            public static ImageButton backButton;

            public static void ShowImageList(String path){
                clear();
                path1 = path;
                file = new File(path);
                listFile = file.listFiles();
                namafolder.setText(file.getName());

                if(!file.getName().equals("Basic") && !file.getName().equals("Advance")){
                    backButton.setVisibility(View.VISIBLE);
                    backButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String previosPath = path1.replace("/"+file.getName(),"");
                            ShowImageList(previosPath);
                        }
                    });
                }
                else
                {
                    backButton.setVisibility(View.GONE);
                }

                for (int i = 0; i<listFile.length;i++){
                    if(listFile[i].getName().endsWith(".jpg") || listFile[i].getName().endsWith(".png") || listFile[i].isDirectory()) {
                        image.add(listFile[i]);
                    }
                }

                Toast.makeText(getAppContext(), image.size()+" Photos", Toast.LENGTH_SHORT).show();

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getAppContext(),3);
                Adapter adapter = new Adapter(image);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            public static void clear(){
                image.clear();
            }
        }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 120);
                return false;
            }
        }
        else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
            Show.ShowImageList(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Basic");
        }
        else
        {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
