package com.example.gittest.repositories;

import com.example.gittest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User> {

    public static UserRepository sUserRepository;
    private List<User> mUsers;

    private UserRepository() {
        mUsers = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (sUserRepository == null)
            sUserRepository = new UserRepository();
        return sUserRepository;
    }

    @Override
    public List<User> getList() {
        return mUsers;
    }

    @Override
    public User get(UUID id) {
        for (User user : mUsers) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User get(String userName) {
        for (User user : mUsers) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    @Override
    public void add(User user) {
        mUsers.add(user);
    }

    @Override
    public void remove(User user) {
        mUsers.remove(user);
    }

    @Override
    public void update(User user) {
        User oldUser = get(user.getId());
        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());
    }
}
