package com.peecee.overclock;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.*;

public class MainActivity extends Activity {
    // >>> TROQUE pelo endereço do seu site no InfinityFree (com https:// e barra no final) <<<
    static final String SITE_URL = "https://SEU-SITE.infinityfreeapp.com/";
    WebView w;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(p);
        }
        w = new WebView(this);
        w.setBackgroundColor(Color.BLACK);
        w.setOverScrollMode(View.OVER_SCROLL_NEVER);
        setContentView(w);
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setUserAgentString(s.getUserAgentString() + " PeeCeeApp");
        w.setWebChromeClient(new WebChromeClient());
        w.setWebViewClient(new WebViewClient() {
            @Override public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                if (r.isForMainFrame())
                    v.loadDataWithBaseURL(null, "<body style='background:#000;color:#0df;font:700 22px sans-serif;text-align:center;padding-top:18vh'>Sem conexão.<br><br><a style='color:#fff' href='" + SITE_URL + "'>Tentar de novo</a></body>", "text/html", "UTF-8", null);
            }
        });
        if (b != null) w.restoreState(b); else w.loadUrl(SITE_URL);
        hide();
    }

    @SuppressWarnings("deprecation")
    void hide() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
    @Override public void onWindowFocusChanged(boolean f) { super.onWindowFocusChanged(f); if (f) hide(); }
    @Override protected void onSaveInstanceState(Bundle o) { super.onSaveInstanceState(o); w.saveState(o); }
    @Override protected void onPause() { super.onPause(); w.onPause(); }
    @Override protected void onResume() { super.onResume(); w.onResume(); }
    @Override public void onBackPressed() { moveTaskToBack(true); }
}
