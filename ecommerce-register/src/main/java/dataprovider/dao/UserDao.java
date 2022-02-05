package dataprovider.dao;

import dataprovider.models.User;

import java.util.List;

public interface UserDao {

  Boolean existsUserBy(String email);

  void save(User user);

  List<User> findAll();
}
