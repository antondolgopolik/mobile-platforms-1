package by.bsuir.mobileplatforms1.service;

import android.content.Context;

import by.bsuir.mobileplatforms1.datasource.dao.UserDao;

public class UserService {
    private final UserDao userDao;

    public UserService(Context context) {
        userDao = new UserDao(context);
    }

    public void createUser(String username) {
        userDao.create(username);
    }
}
