package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.User;
import cn.blooming.jxgjsj.model.entity.UserExample;
import cn.blooming.jxgjsj.model.mapper.UserMapper;
import cn.blooming.jxgjsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> getUserAll() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User getUserLogin(User user) {
        if(StringUtils.isEmpty(user)||StringUtils.isEmpty(user.getName())||StringUtils.isEmpty(user.getPwd())){
            return null;
        }else{
            UserExample userExample=new UserExample();
            userExample.createCriteria()
                    .andNameEqualTo(user.getName()).andPwdEqualTo(user.getPwd()).andRoteEqualTo("管理员");
            return userMapper.selectByExample(userExample).get(0);
        }
    }

    @Override
    public List<User> getUserByName(String name) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andNameLike("%"+name+"%");
        return userMapper.selectByExample(userExample);
    }

    @Override
    public List<User> isUserByName(String name) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public boolean userUpdate(User user) {
        return userMapper.updateByPrimaryKey(user)>0?true:false;
    }

    @Override
    public boolean userDelete(Integer uid) {
        return userMapper.deleteByPrimaryKey(uid)>0?true:false;
    }

    @Override
    public boolean userInsert(User user) {
        return userMapper.insert(user)>0?true:false;
    }

}
