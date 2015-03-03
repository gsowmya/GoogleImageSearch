package androidgroup.com.googleimagesearch.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidgroup.com.googleimagesearch.R;

public class ImageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String url = getIntent().getStringExtra("url");
        ImageView imgImage = (ImageView) findViewById(R.id.imgFullImage);
        imgImage.setImageResource(0);
        Picasso.with(getApplicationContext()).load(url).into(imgImage);
    }

}
