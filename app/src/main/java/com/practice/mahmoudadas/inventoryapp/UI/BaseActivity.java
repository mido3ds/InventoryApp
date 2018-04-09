package com.practice.mahmoudadas.inventoryapp.UI;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;

// parent class for shared functionality
abstract class BaseActivity extends AppCompatActivity {
    protected void runLoader(int loaderId, LoaderManager.LoaderCallbacks callbacks) {
        LoaderManager loaderManager = getLoaderManager();
        Loader loader = loaderManager.getLoader(loaderId);

        if (loader != null && loader.isReset()) {
            loaderManager.restartLoader(loaderId, null, callbacks);
        } else {
            loaderManager.initLoader(loaderId, null, callbacks);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
