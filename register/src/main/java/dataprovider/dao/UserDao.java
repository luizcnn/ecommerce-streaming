package dataprovider.dao;

import dataprovider.models.User;

public interface UserDao {

  Boolean existsUserBy(String email);

  void save(User user);
}
