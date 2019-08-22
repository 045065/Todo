package com.example.todo.todo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todo.R;
import com.example.todo.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Todo一覧表示用Activity
 */
public class TodoListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TodoListPresenter mTodoListPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        TodoListFragment todoListFragment = (TodoListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (todoListFragment == null) {
            todoListFragment = TodoListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), todoListFragment, R.id.contentFrame);
        }
        mTodoListPresenter = new TodoListPresenter(todoListFragment);
    }
}