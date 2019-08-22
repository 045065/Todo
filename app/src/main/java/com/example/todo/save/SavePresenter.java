package com.example.todo.save;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.application.TodoApplication;
import com.example.todo.data.TodoData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.todo.data.TodoData.USERS;

/**
 * Todo追加・編集用Presenter
 */
public class SavePresenter implements SaveContract.Presenter {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private SaveContract.View mAddView;
    private TodoData mEditTodoData;

    public SavePresenter(@NonNull final SaveContract.View addView, @Nullable final TodoData editTodoData) {
        mEditTodoData = editTodoData;
        mAddView = addView;
        mAddView.setPresenter(this);
    }

    @Override
    public void start() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        if (mAddView.isActive() && isNewTodo() == false) {
            mAddView.setTitle(mEditTodoData.getTitle());
            mAddView.setContent(mEditTodoData.getContent());
        }
    }

    /**
     * Todoの新規作成かどうか
     *
     * @return
     */
    private boolean isNewTodo() {
        return mEditTodoData == null;
    }

    @Override
    public void saveTodo(final String title, final String content) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String key;
        TodoData toDoData;
        if (isNewTodo()) {
            // 新規
            key = mDatabaseReference.push().getKey();
        } else {
            // 編集
            key = mEditTodoData.getFirebaseKey();
        }
        toDoData = new TodoData(key, title, content, false);
        mDatabaseReference.child(TodoApplication.getContext().getString(USERS)).child(uid).
                updateChildren(toDoData.toMap());
        mAddView.showTodoListUi();
    }
}
