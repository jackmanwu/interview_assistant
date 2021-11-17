package com.jackmanwu.interview.assistant;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jackmanwu.interview.assistant.common.SQLiteHelper;
import com.jackmanwu.interview.assistant.dao.ArticleDao;
import com.jackmanwu.interview.assistant.dao.CategoryDao;
import com.jackmanwu.interview.assistant.databinding.ActivityMainBinding;
import com.jackmanwu.interview.assistant.domain.Article;
import com.jackmanwu.interview.assistant.domain.Category;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainBody.toolbar);
//        binding.mainBody.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        binding.mainBody.fab.setOnClickListener(view -> {
            SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
            CategoryDao categoryDao = new CategoryDao(sqLiteHelper);
            categoryDao.save(new Category("算法"));

            ArticleDao articleDao = new ArticleDao(sqLiteHelper);
            for (int i = 0; i < 62; i++) {
                articleDao.save(new Article(0, 1, "hello" + i, ""));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.main_content);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu menu = navigationView.getMenu();
        for (int i = 0; i < 3; i++) {
            menu.add("hello" + i).setOnMenuItemClickListener(menuItem -> {
                Bundle bundle = new Bundle();
                bundle.putString("target_url", "https://www.baidu.com");
                navController.navigate(R.id.nav_home, bundle);
                return false;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_content);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}