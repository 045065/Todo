package com.example.todo.save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Todo追加・編集用Fragment
 */
public class SaveFragment extends Fragment implements SaveContract.View {
    @BindView(R.id.title)
    EditText mTitleEditText;

    @BindView(R.id.content)
    EditText mContentEditText;

    @BindView(R.id.fab_save_todo_done)
    FloatingActionButton mSaveTodoDoneFab;

    private SaveContract.Presenter mPresenter;

    public SaveFragment() {
    }

    public static SaveFragment newInstance() {
        return new SaveFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull final SaveContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_save, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.fab_save_todo_done)
    void onClickSaveTodoDoneFab(final View view) {
        if (StringUtils.isEmpty(mTitleEditText.getText().toString())) {
            Snackbar.make(getView(), getContext().getString(R.string.empty_title), Snackbar.LENGTH_LONG).show();
            return;
        }
        mPresenter.saveTodo(
                mTitleEditText.getText().toString(),
                mContentEditText.getText().toString());
    }

    @Override
    public void showTodoListUi() {
        getActivity().finish();
    }

    @Override
    public void setTitle(final String title) {
        mTitleEditText.setText(title);
    }

    @Override
    public void setContent(final String content) {
        mContentEditText.setText(content);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
