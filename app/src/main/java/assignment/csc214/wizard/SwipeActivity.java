package assignment.csc214.wizard;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import assignment.csc214.wizard.databinding.ActivitySwipeBinding;

public class SwipeActivity extends AppCompatActivity {

    private SwipeCollectionPagerAdapter mSwipeView;

    private DownloadManager mDownloadManager;

    private ViewPager mViewPager;

    private Bitmap mBitmap;

    private Message mMessage;

    private Bundle mPass;

    private ActivitySwipeBinding mSwipeBinding;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        mSwipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_swipe);

        setSupportActionBar(mSwipeBinding.swipeDetailToolbar);

        mSwipeBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("wizard", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDownloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse("https://docs.google.com/document/d/1oMMhT9gvcZ4jbr6Qva11hLRij2H5tMDOoqgCpk1BYkk/edit");
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = mDownloadManager.enqueue(request);
                            }
                        }).show();
            }
        });

        //startDownload("http://www.tutorialspoint.com/green/images/logo.png");

        mSwipeView = new SwipeCollectionPagerAdapter(getSupportFragmentManager(), mPass);
        mSwipeBinding.swipeViewContainer.setAdapter(mSwipeView);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connection.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static class SwipeCollectionPagerAdapter extends FragmentStatePagerAdapter{

        private Bundle mBundle;

        public SwipeCollectionPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            mBundle = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return wizardFragment.newInstance("Day 1", wizardFragment.PROMPT01,mBundle );
                case 1:
                    return wizardFragment.newInstance("Day 2", wizardFragment.PROMPT02, mBundle);
                case 2:
                    return wizardFragment.newInstance("Day 3", wizardFragment.PROMPT03, mBundle);
                case 3:
                    return wizardFragment.newInstance("Day 4", wizardFragment.PROMPT04, mBundle);
                case 4:
                    return wizardFragment.newInstance("Day 5", wizardFragment.PROMPT05, mBundle);
                case 5:
                    return wizardFragment.newInstance("Day 6", "Roc Speak", mBundle);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
}
