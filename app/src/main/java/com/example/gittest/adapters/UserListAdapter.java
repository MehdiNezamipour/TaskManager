package com.example.gittest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskManageActivity;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskRepository;
import com.example.gittest.repositories.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    private List<User> mUsers;
    private Context mContext;
    private UserRepository mUserRepository;
    private TaskRepository mTaskRepository;


    public UserListAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
        mUserRepository = UserRepository.getInstance();
        mTaskRepository = TaskRepository.getInstance();
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.user_row_fragment, viewGroup, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int position) {
        userHolder.bindUser(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private CardView mCardViewUserContainer;
        private TextView mTextViewUserName;
        private TextView mTextViewSignUpDate;
        private ImageButton mImageButtonDelete;
        private TextView mTextViewUserIcon;


        public UserHolder(@NonNull View itemView) {
            super(itemView);

            mCardViewUserContainer = itemView.findViewById(R.id.cardView_user_container);
            mTextViewUserName = itemView.findViewById(R.id.textView_userName);
            mTextViewSignUpDate = itemView.findViewById(R.id.textView_signUp_date);
            mTextViewUserIcon = itemView.findViewById(R.id.textView_user_icon);
            mImageButtonDelete = itemView.findViewById(R.id.imageButton_delete);

        }

        public void bindUser(User user) {
            mTextViewUserName.setText(user.getUserName());
            mTextViewSignUpDate.setText(user.getDate().toString());

            mCardViewUserContainer.setOnClickListener(view -> mContext.startActivity(TaskManageActivity.newIntent(mContext, mTextViewUserName.getText().toString())));
            mImageButtonDelete.setOnClickListener(view -> new MaterialAlertDialogBuilder(mContext)
                    .setTitle(R.string.removeUser)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        mUserRepository.remove(user);
                        mTaskRepository.removeAllUserTasks(user);
                        setUsers(mUserRepository.getList());
                        notifyDataSetChanged();

                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show());

        }

    }
}



