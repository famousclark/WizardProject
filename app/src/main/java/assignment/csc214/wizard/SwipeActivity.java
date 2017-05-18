package assignment.csc214.wizard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import assignment.csc214.wizard.databinding.ActivitySwipeBinding;

public class SwipeActivity extends AppCompatActivity {

    private SwipeCollectionPagerAdapter mSwipeView;

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
                                startDownload("http://www.tutorialspoint.com/green/images/logo.png");
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

    private void startDownload(String something){
        if (isNetworkConnected()){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Hold on one sec...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            final String url = something;

            new Thread(){
                public void run(){
                    InputStream inputStream = null;
                    Message message = Message.obtain();
                    message.what = 1;

                    try{
                        inputStream  = openHttpConnection(url);
                        mBitmap = BitmapFactory.decodeStream(inputStream);
                        Bundle bunBunBundle = new Bundle();
                        bunBunBundle.putParcelable("map", mBitmap);
                        message.setData(bunBunBundle);
                        inputStream.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                    messageHandler.sendMessage(message);
                }
            }.start();

        }else{
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("It seems your internet connection is off. Please turn it on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO set cancel and ok later
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
       //new DownLoadWizardResponseTask(this).execute("https://api.github.com/repositories");
    }

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            mSwipeBinding.swipeToolbarLayout.setBackground((Drawable) (message.getData().getParcelable("map")));
            mProgressDialog.dismiss();
        }
    };

    public InputStream openHttpConnection(String urlStr) {
        InputStream inputStream = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
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
