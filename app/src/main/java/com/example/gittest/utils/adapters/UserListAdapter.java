package com.example.gittest.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskManageActivity;
import com.example.gittest.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    private List<User> mUsers;
    private Context mContext;


    public UserListAdapter(Context context) {
        mContext = context;
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
        private TextView mTextViewUserName;
        private TextView mTextViewSignUpDate;
        private TextView mTextViewUserIcon;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewUserName = itemView.findViewById(R.id.textView_userName);
            mTextViewSignUpDate = itemView.findViewById(R.id.textView_signUp_date);
            mTextViewUserIcon = itemView.findViewById(R.id.textView_user_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(TaskManageActivity.newIntent(mContext, mTextViewUserName.getText().toString()));
                }
            });
        }

        public void bindUser(User user) {
            mTextViewUserName.setText(user.getUserName());
            mTextViewSignUpDate.setText(user.getDate().toString());

        }

    }
}



