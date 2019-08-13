package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.User;
import cn.blooming.jxgjsj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
@Log4j
@Api(tags = {"用户信息中心"})
@Controller
public class UserController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    @ShowLogger(info = "查询单个用户")
    @ApiOperation(value = "查询单个用户",notes = "查询单个用户")
    @RequestMapping(path = "/user/{id}",method = RequestMethod.POST)
    @ResponseBody
    public User getUserById(@PathVariable(value = "id") Integer id){
        return userService.getUserById(id);
    }

    @ShowLogger(info = "登录界面")
    @ApiOperation(value = "登录界面",notes = "登录界面")
    @RequestMapping(path = "/user/toLogin",method = RequestMethod.GET)
    public String UserToLogin(){
        return "login";
    }

    @ShowLogger(info = "登录处理")
    @ApiOperation(value = "登录处理",notes = "登录处理")
    @RequestMapping(path = "/user/login",method = RequestMethod.POST)
    public String UserLogin(User user){
        redisUtil.delete("users");
        User login = userService.getUserLogin(user);
        if(!StringUtils.isEmpty(login)){
            return "home";
        }else{
            return "redirect:/";
        }
    }

}
