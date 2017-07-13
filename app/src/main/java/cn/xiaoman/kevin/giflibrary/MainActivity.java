package cn.xiaoman.kevin.giflibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cn.hadcn.davinci.DaVinci;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        DaVinci.with(this)
                .getImageLoader()
                .load("http://qq.yh31.com/tp/zjbq/201706231712500189.gif")
                .into(new WeakReference<>(imageView));
    }
}
