package com.example.gittest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskManageActivity;
import com.example.gittest.controller.fragments.UserDetailFragment;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    public static final String USER_DETAIL_FRAGMENT_TAG = "userDetailFragment";
    private List<User> mUsers;
    private Context mContext;
    private TaskDBRepository mTaskDBRepository;
    private UserDBRepository mUserDBRepository;
    private FragmentManager mFragmentManager;


    public UserListAdapter(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mTaskDBRepository = TaskDBRepository.getInstance(mContext);
        mUserDBRepository = UserDBRepository.getInstance(mContext);
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
        private TextView mTextViewRole;
        private ImageButton mImageButtonDelete;
        private TextView mTextViewUserIcon;


        public UserHolder(@NonNull View itemView) {
            super(itemView);

            mCardViewUserContainer = itemView.findViewById(R.id.cardView_user_container);
            mTextViewUserName = itemView.findViewById(R.id.textView_userName);
            mTextViewRole = itemView.findViewById(R.id.textView_role);
            mTextViewUserIcon = itemView.findViewById(R.id.textView_user_icon);
            mImageButtonDelete = itemView.findViewById(R.id.imageButton_delete);


        }

        public void bindUser(User user) {
            mTextViewUserName.setText(user.getUserName());
            mTextViewRole.setText(String.format("Role : %s", user.getRole().toString()));


            mCardViewUserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(user);
                    userDetailFragment.show(mFragmentManager, USER_DETAIL_FRAGMENT_TAG);
                }
            });
            mImageButtonDelete.setOnClickListener(view -> new MaterialAlertDialogBuilder(mContext)
                    .setTitle(R.string.removeUser)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        mUserDBRepository.remove(user);
                        mTaskDBRepository.removeAllUserTasks(user.getId());
                        setUsers(mUserDBRepository.getList());
                        notifyDataSetChanged();

                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show());

        }

    }
}



