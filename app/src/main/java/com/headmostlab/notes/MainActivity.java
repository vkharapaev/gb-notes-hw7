package com.headmostlab.notes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.headmostlab.notes.databinding.ActivityMainBinding;
import com.headmostlab.notes.ui.notelist.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initView();

        if (savedInstanceState != null) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, NoteListFragment.newNoteListFragment())
                .commitNow();
    }

    private void initView() {
        initToolBar();
        initDrawer();
    }

    private void initToolBar() {
        setSupportActionBar(binding.appBarMain.toolbar);
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                binding.drawer, binding.appBarMain.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(item -> {
            if (navigateFragment(item.getItemId())) {
                binding.drawer.closeDrawer(GravityCompat.START);
            }
            return false;
        });

    }

    private boolean navigateFragment(int menuId) {
        switch (menuId) {
            case R.id.action_about:
                // TODO: 2/17/2021
                return true;
        }
        return false;
    }

}
