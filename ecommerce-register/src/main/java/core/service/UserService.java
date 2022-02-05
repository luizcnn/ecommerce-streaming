package core.service;

import dataprovider.dao.UserDao;
import dataprovider.models.User;

import java.util.List;

public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public Boolean existsUser(String email) {
    return userDao.existsUserBy(email);
  }

  public void save(User user) {
    userDao.save(user);
  }

  public List<User> findAll() {
    return userDao.findAll();
  }

}
