package com.example.todo.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.R;
import com.example.todo.data.TodoData;

import java.util.List;

/**
 * Todo一覧表示用Adapter
 */
public class TodoListAdapter extends ArrayAdapter<TodoData> {
    private List<TodoData> mTodos;

    public TodoListAdapter(final Context context, final int layoutResourceId, final List<TodoData> toDoData) {
        super(context, layoutResourceId, toDoData);
        this.mTodos = toDoData;
    }

    @Override
    public int getCount() {
        return mTodos.size();
    }

    @Nullable
    @Override
    public TodoData getItem(final int position) {
        return mTodos.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        View view;

        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_todo, null);
            viewHolder = new ViewHolder();

            viewHolder.todoItem = (LinearLayout) view.findViewById(R.id.todo_item);
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.title_text_view);
            viewHolder.completeCheckBox = (CheckBox) view.findViewById(R.id.complete);
            view.setTag(viewHolder);
        }

        final TodoData toDoData = mTodos.get(position);
        viewHolder.titleTextView.setText(toDoData.getTitle());
        viewHolder.completeCheckBox.setChecked(toDoData.isCompleted());
        viewHolder.completeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!toDoData.isCompleted()) {
                    ((ListView) viewGroup).performItemClick(view, position, R.id.complete);
                }
            }
        });
        viewHolder.todoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) viewGroup).performItemClick(view, position, R.id.todo_item);
            }
        });
        return view;
    }

    public TodoData getTodoDataKey(final String key) {
        for (TodoData toDoData : mTodos) {
            if (toDoData.getFirebaseKey().equals(key)) {
                return toDoData;
            }
        }

        return null;
    }

    static class ViewHolder {
        LinearLayout todoItem;
        TextView titleTextView;
        CheckBox completeCheckBox;
    }

}