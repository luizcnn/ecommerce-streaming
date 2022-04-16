package core.service;

import dataprovider.dao.UserDao;
import dataprovider.models.UserEntity;

import java.util.List;

public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public Boolean existsUser(String email) {
    return userDao.existsUserBy(email);
  }

  public void save(UserEntity userEntity) {
    userDao.save(userEntity);
  }

  public List<UserEntity> findAll() {
    return userDao.findAll();
  }

  public UserEntity findByEmail(String email) {
    return userDao.findByEmail(email);
  }
}
