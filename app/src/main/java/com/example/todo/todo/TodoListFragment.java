package com.example.todo.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo.R;
import com.example.todo.data.TodoData;
import com.example.todo.login.LoginActivity;
import com.example.todo.util.DialogUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Todo一覧表示用Fragment
 */
public class TodoListFragment extends Fragment implements TodoListContract.View {
    @BindView(R.id.list_view)
    ListView mListView;

    @BindView(R.id.fab_add_todo)
    FloatingActionButton mAddTodoFab;

    private TodoListAdapter mTodoListAdapter;
    private TodoListContract.Presenter mPresenter;

    public TodoListFragment() {
    }

    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTodoListAdapter = new TodoListAdapter(getActivity(), R.layout.view_todo, new ArrayList<TodoData>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull final TodoListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo_list, container, false);
        ButterKnife.bind(this, root);

        setHasOptionsMenu(true);

        mListView.setAdapter(mTodoListAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            final TodoData toDoData = (TodoData) mTodoListAdapter.getItem(position);
            switch (view.getId()) {
                case R.id.complete:
                    // 完了
                    if (!toDoData.isCompleted()) {
                        mPresenter.completeTodo(toDoData);
                    }
                    break;
                case R.id.todo_item:
                    // 詳細表示・編集
                    showDetailEditTodoUi(toDoData);
                    break;
                default:

                    break;
            }
        });

        return root;
    }

    @OnClick(R.id.fab_add_todo)
    void onClickAddTodoFab(final View view) {
        showAddTodoUi();
    }

    @Override
    public void addTodo(final TodoData toDoData) {
        mTodoListAdapter.add(toDoData);
        mTodoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeTodo(final String firebaseKey) {
        TodoData item = mTodoListAdapter.getTodoDataKey(firebaseKey);
        mTodoListAdapter.remove(item);
        mTodoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeTodo(final TodoData toDoData) {
        TodoData item = mTodoListAdapter.getTodoDataKey(toDoData.getFirebaseKey());
        item.setTitle(toDoData.getTitle());
        item.setContent(toDoData.getContent());
        mTodoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoginUi() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    /**
     * Todo追加画面表示
     */
    private void showAddTodoUi() {
//        Intent intent = new Intent(getContext(), SaveActivity.class);
//        startActivity(intent);
    }

    /**
     * Todo詳細・編集画面表示
     */
    private void showDetailEditTodoUi(final TodoData toDoData) {
//        Intent intent = new Intent(getContext(), SaveActivity.class);
//        intent.putExtra(EXTRA_TODO_DATA, toDoData);
//        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_todo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                DialogUtils.showCallbackPositiveDialog(getContext(),
                        R.string.title_logout, R.string.positive_logout,
                        R.string.negative_logout,
                        (dialog, which) -> mPresenter.logoutAccount());
                break;
        }
        return true;
    }
}