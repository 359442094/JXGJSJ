package cn.blooming.jxgjsj.common;

import cn.blooming.jxgjsj.model.entity.User;
import cn.blooming.jxgjsj.model.entity.UserExample;
import cn.blooming.jxgjsj.model.mapper.UserMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@ToString
@Getter
@Setter
public class Context {

    private static ThreadLocal<Context> tl=new ThreadLocal<>();

    private static Context context1;

    private static User user;

    private static String uName;

    private static String uPass;

    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void init(){
        context1=this;
    }

    public static void initContext(String sessionId){
        Context context=new Context();
        if(StringUtils.isEmpty(sessionId)){
            throw new ServiceException(Constant.VALUE_ERROR,"sessionId不存在");
        }
        if(sessionId.indexOf("&")==-1){
            throw new ServiceException(Constant.VALUE_ERROR,"sessionId格式非法:"+sessionId);
        }
        context.uName = sessionId.split("&")[0];
        context.uPass = sessionId.split("&")[1];

        UserExample userExample=new UserExample();
        userExample.createCriteria().andPwdEqualTo(uPass).andNameEqualTo(uName);
        List<User> users = context1.userMapper.selectByExample(userExample);
        if(users.size()>0){
            context.user = users.get(0);
        }
        tl.set(context);
    }

    public static User getUser(){
        if(StringUtils.isEmpty(user)){
            throw new ServiceException(Constant.VALUE_ERROR,"用户不存在");
        }
        return user;
    }

}
