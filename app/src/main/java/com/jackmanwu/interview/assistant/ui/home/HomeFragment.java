package com.jackmanwu.interview.assistant.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jackmanwu.interview.assistant.common.Constants;
import com.jackmanwu.interview.assistant.common.CursorResult;
import com.jackmanwu.interview.assistant.common.SQLiteHelper;
import com.jackmanwu.interview.assistant.dao.ArticleDao;
import com.jackmanwu.interview.assistant.databinding.FragmentHomeBinding;
import com.jackmanwu.interview.assistant.domain.Article;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private MyRecyclerViewAdapter recyclerViewAdapter;
    private ArticleDao articleDao;

    private String cursor = CursorResult.START_CURSOR;
    private static final int count = 15;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        articleDao = new ArticleDao(new SQLiteHelper(getContext()));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new MyRecyclerViewAdapter();
        binding.recyclerView.setAdapter(recyclerViewAdapter);

        getData();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            try {
                cursor = CursorResult.START_CURSOR;
                recyclerViewAdapter.getDataList().clear();
                getData();
            } finally {
                if (binding.swipeRefresh.isRefreshing()) {
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });

        binding.recyclerView.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void onLoading(int count, int last) {
                getData();
            }
        });

//        String url = "https://www.oschina.net/";
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String targetUrl = bundle.getString("target_url");
//            Log.d(Constants.TAG, "target url: " + targetUrl);
//            if (targetUrl != null) {
//                url = targetUrl;
//            }
//        } else {
//            Log.d(Constants.TAG, "空的 ");
//        }
//        binding.webView.loadUrl(url);
//        binding.webView.addJavascriptInterface(this, "android");
//        binding.webView.setWebViewClient(webViewClient);
//        binding.webView.setWebChromeClient(webChromeClient);
//        binding.webView.setOnKeyListener((view, i, keyEvent) -> {
//            if ((i == KeyEvent.KEYCODE_BACK) && binding.webView.canGoBack()) {
//                binding.webView.goBack();
//                return true;
//            }
//            return false;
//        });
//
//        WebSettings webSettings = binding.webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding.webView.destroy();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CursorResult<Article> cursorResult = articleDao.list(cursor, count);
        cursor = cursorResult.getCursor();
        if (!cursor.equals(CursorResult.END_CURSOR)) {
            recyclerViewAdapter.getDataList().addAll(cursorResult.getList());
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

//    private final WebViewClient webViewClient = new WebViewClient() {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            binding.progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            binding.progressBar.setVisibility(View.GONE);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.d(Constants.TAG, url);
//            return super.shouldOverrideUrlLoading(view, "url: " + url);
//        }
//    };

    private final WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            result.confirm();
//            return true;
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d(Constants.TAG, "title: " + title);
        }
    };
}