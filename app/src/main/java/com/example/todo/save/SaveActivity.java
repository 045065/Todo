package com.example.todo.save;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todo.R;
import com.example.todo.data.TodoData;
import com.example.todo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Todo追加・編集用Activity
 */
public class SaveActivity extends AppCompatActivity {
    public static final String EXTRA_TODO_DATA = "EXTRA_TODO_DATA";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SavePresenter mSavePresenter;
    private TodoData mEditTodoData;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            mEditTodoData = (TodoData) getIntent().getSerializableExtra(EXTRA_TODO_DATA);
        }

        SaveFragment saveFragment = (SaveFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (saveFragment == null) {
            saveFragment = SaveFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), saveFragment, R.id.contentFrame);
        }
        mSavePresenter = new SavePresenter(saveFragment, mEditTodoData);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}