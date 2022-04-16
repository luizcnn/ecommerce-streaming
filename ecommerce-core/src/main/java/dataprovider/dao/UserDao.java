package dataprovider.dao;

import dataprovider.models.UserEntity;

import java.util.List;

public interface UserDao {

  Boolean existsUserBy(String email);

  void save(UserEntity userEntity);

  List<UserEntity> findAll();

  UserEntity findByEmail(String email);

}
