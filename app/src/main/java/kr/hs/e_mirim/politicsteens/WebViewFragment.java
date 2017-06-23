package kr.hs.e_mirim.politicsteens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {
    WebView webView;
    String myUrl;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_web_view, container, false);
        webView=(WebView)view.findViewById(R.id.news_link);
        Log.d("Webview Load 중~", S.news_link);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(S.news_link);
        webView.setWebViewClient(new MyWebViewClient());
        return view;
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = S.news_link;
            view.loadUrl(S.news_link);
            return true;
        }
    }
}
