package it.uniroma3.stud.cidici.wifilocator.ui;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

import java.util.Map;

/**
 * A simple implementation in HTML5 to show the UniversityMap
 */
public class WebViewMapActivity extends AbstractMapActivity {

    private WebView webview;

    @Override
    protected void loadContentView() {
        webview = new WebView(this);
        setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webview.loadUrl("file:///android_asset/index.html");
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                drawAps();
            }
        });
    }

    @Override
    public void onPositionUpdate(Position position) {
        webview.loadUrl("javascript:onPositionUpdate(" + position.getX() + ", " + position.getY() + ");");
    }

    @Override
    public void onPositionError(String message) {
        webview.loadUrl("javascript:onPositionUpdate(0,0);");
//        webview.loadUrl("javascript:onPositionError('Non riesco a localizzarti');");
    }

    private void drawAps() {
        Map<String, Ap> bssidApMap = getUniversityMap().getBssidApMap();
        for (Map.Entry<String, Ap> apEntry : bssidApMap.entrySet()) {
            Ap ap = apEntry.getValue();
            Position position = ap.getPosition();
            webview.loadUrl("javascript:drawAp(" + position.getX() + ", " + position.getY() + ");");
        }
    }
}