package by.bsuir.mobileplatforms1.service;

import android.content.Context;

import java.util.List;

import by.bsuir.mobileplatforms1.datasource.dao.ResultDao;
import by.bsuir.mobileplatforms1.datasource.dao.UserDao;
import by.bsuir.mobileplatforms1.entity.Result;
import by.bsuir.mobileplatforms1.entity.User;

public class ResultService {
    private final UserDao userDao;
    private final ResultDao resultDao;

    public ResultService(Context context) {
        userDao = new UserDao(context);
        resultDao = new ResultDao(context);
    }

    public void saveResultForUser(String username, int points) {
        User user = userDao.read(username);
        resultDao.create(user.getUserId(), points);
    }

    public Result getLastResultForUser(String username) {
        User user = userDao.read(username);
        return resultDao.readLastUserResult(user.getUserId());
    }

    public List<Result> getAllResults() {
        return resultDao.read();
    }
}
