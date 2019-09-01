package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.common.Context;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.User;
import cn.blooming.jxgjsj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j
@Api(tags = {"用户信息中心"})
@Controller
public class UserController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private UserService userService;

    @ApiModelProperty(value = "登录用户",notes = "登录用户")
    @ResponseBody
    @RequestMapping(path = "/user/newLogin",method = RequestMethod.POST)
    public User initUser(User user){

        String sessionId = user.getName()+"&"+user.getPwd();
        redisUtil.set("sessionId",sessionId);

        if(!StringUtils.isEmpty(redisUtil.get("sessionId"))){
            Context.initContext(sessionId);
            User user1 = Context.getUser();
            return user1;
        }
        return null;
    }

    @ShowLogger(info = "跳转至用户登录界面")
    @ApiOperation(value = "跳转至用户登录界面",notes = "跳转至用户登录界面")
    @RequestMapping(path = "/user/toLogin",method = RequestMethod.GET)
    public String UserToLogin(){
        return "login";
    }

    @ShowLogger(info = "用户登录处理")
    @ApiOperation(value = "用户登录处理",notes = "用户登录处理")
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

    @ShowLogger(info = "跳转至用户后台界面")
    @ApiOperation(value = "跳转至用户后台界面",notes = "跳转至用户后台界面")
    @RequestMapping(path = "/user/userAdmin",method = RequestMethod.GET)
    public String toUserAdmin(Model model, @RequestParam(value = "name",defaultValue = "")String name){
        List<User> users;
        if(StringUtils.isEmpty(name)){
            users = userService.getUserAll();
        }else{
            users = userService.getUserByName(name);
        }
        model.addAttribute("users",users);
        return "users";
    }

    @ShowLogger(info = "跳转至用户添加界面")
    @ApiOperation(value = "跳转至用户添加界面",notes = "跳转至用户添加界面")
    @RequestMapping(path = "/toUserAdminInsert",method = RequestMethod.GET)
    public String toUserAdminInsert(){
        return "create_user";
    }

    @ShowLogger(info = "查询单个用户")
    @ApiOperation(value = "查询单个用户",notes = "查询单个用户")
    @RequestMapping(path = "/user/{id}",method = RequestMethod.GET)
    public String toUserAdminUpdate(Model model,@PathVariable("id")Integer id){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "update_user";
    }

    @ShowLogger(info = "修改用户头像")
    @ApiOperation(value = "修改用户头像",notes = "修改用户头像")
    @RequestMapping(path = "/user/upload/icon",method = RequestMethod.PUT)
    @ResponseBody
    public boolean userUploadUpath(@RequestParam("id")Integer id,@RequestParam("file") MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            User user = userService.getUserById(id);
            user.setUpath(savePath+fileName);
            boolean update = userService.userUpdate(user);

            if(upload == true && update == true){
                redisUtil.delete("users");
                log.info("修改用户头像成功");
                return true;
            }
        }
        return false;
    }

    @ShowLogger(info = "修改用户名片背景")
    @ApiOperation(value = "修改用户名片背景",notes = "修改用户名片背景")
    @RequestMapping(path = "/user/upload/visiting",method = RequestMethod.PUT)
    @ResponseBody
    public boolean userUploadBpath(@RequestParam("id")Integer id,@RequestParam("file") MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            User user = userService.getUserById(id);
            user.setBpath(savePath+fileName);
            boolean update = userService.userUpdate(user);

            if(upload == true && update == true){
                redisUtil.delete("users");
                log.info("修改名片背景成功");
                return true;
            }
        }
        return false;
    }

    @ShowLogger(info = "新增用户信息")
    @ApiOperation(value = "新增用户信息",notes = "新增用户信息")
    @RequestMapping(path = "/user/info",method = RequestMethod.POST)
    @ResponseBody
    public boolean userSave(User user){
        if(StringUtils.isEmpty(user)||StringUtils.isEmpty(user.getName())||StringUtils.isEmpty(user.getPwd())
                ||StringUtils.isEmpty(user.getDesignInfo())||StringUtils.isEmpty(user.getDesignNian())
                ||StringUtils.isEmpty(user.getDesignValue())||StringUtils.isEmpty(user.getInfo())
                ||StringUtils.isEmpty(user.getIphone())||StringUtils.isEmpty(user.getRote())
                ||StringUtils.isEmpty(user.getStudentName())||StringUtils.isEmpty(user.getStudentType())
                ||StringUtils.isEmpty(user.getType())
        ){
            return false;
        }else if(StringUtils.isEmpty(redisUtil.get("ufileName"))||StringUtils.isEmpty(redisUtil.get("bfileName"))){
            return false;
        } else {
            if(userService.isUserByName(user.getName()).size()!=0){
                return false;
            }else {
                user.setBpath(redisUtil.get("bfileName").toString());
                user.setUpath(redisUtil.get("ufileName").toString());
                redisUtil.delete("users");
                boolean insert = userService.userInsert(user);
                return insert;
            }
        }
    }

    @ShowLogger(info = "新增用户头像")
    @ApiOperation(value = "新增用户头像",notes = "新增用户头像")
    @RequestMapping(path = "/user/upload/icon",method = RequestMethod.POST)
    @ResponseBody
    public boolean userSaveUpath(@RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            if(upload){
                redisUtil.delete("ufileName");
                redisUtil.set("ufileName",savePath+fileName);
                return true;
            }
        }
        return false;
    }

    @ShowLogger(info = "新增用户名片背景")
    @ApiOperation(value = "新增用户名片背景",notes = "新增用户名片背景")
    @RequestMapping(path = "/user/upload/visiting",method = RequestMethod.POST)
    @ResponseBody
    public boolean userSaveBpath(@RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            if(upload){
                redisUtil.delete("bfileName");
                redisUtil.set("bfileName",savePath+fileName);
                return true;
            }
        }
        return false;
    }

    @ShowLogger(info = "修改用户信息")
    @ApiOperation(value = "修改用户信息",notes = "修改用户信息")
    @RequestMapping(path = "/user/info",method = RequestMethod.PUT)
    @ResponseBody
    public boolean userUpdate(User user){
        Integer uid = user.getUid();
        User userById = userService.getUserById(uid);
        user.setUpath(userById.getUpath());
        user.setBpath(userById.getBpath());
        boolean update = userService.userUpdate(user);
        redisUtil.delete("users");
        return update;
    }

    @ShowLogger(info = "删除单个用户信息")
    @ApiOperation(value = "删除单个用户信息",notes = "删除单个用户信息")
    @RequestMapping(path = "/user/info/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean userDelete(@PathVariable("id")Integer id){
        boolean delete = userService.userDelete(id);
        redisUtil.delete("users");
        return delete;
    }

    @ShowLogger(info = "删除多个用户信息")
    @ApiOperation(value = "删除多个用户信息",notes = "删除多个用户信息")
    @RequestMapping(path = "/user/info",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean userDelete(String [] ids){
        for (String id : ids) {
            id=id.replace("[","");
            id=id.replace("]","");
            Integer mid = Integer.valueOf(id);
            System.out.println("mid:"+mid);
            userService.userDelete(mid);
        }
        redisUtil.delete("users");
        return true;
    }

}
