package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.User;

import java.util.List;

public interface UserService {

    User getUserById(Integer id);

    List<User> getUserAll();

    User getUserLogin(User user);

    List<User> getUserByName(String name);

    List<User> isUserByName(String name);

    boolean userUpdate(User user);

    boolean userDelete(Integer uid);

    boolean userInsert(User user);

}
