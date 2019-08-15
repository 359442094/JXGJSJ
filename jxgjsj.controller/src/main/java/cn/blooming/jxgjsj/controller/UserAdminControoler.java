package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.User;
import cn.blooming.jxgjsj.service.UserService;
import io.swagger.annotations.Api;
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

/**
 * 用户管理配置
 * */
@Api(tags = {"用户管理配置"})
@Controller
@Log4j
public class UserAdminControoler {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/toUserAdmin",method = RequestMethod.GET)
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

    @RequestMapping(path = "/toUserAdminInsert",method = RequestMethod.GET)
    public String toUserAdminInsert(){
        return "create_user";
    }

    @RequestMapping(path = "/toUserAdminUpdate/{id}",method = RequestMethod.GET)
    public String toUserAdminUpdate(Model model,@PathVariable("id")Integer id){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "update_user";
    }

    @RequestMapping(path = "/user_upload_update_upath",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_upload_update_upath(@RequestParam("id")Integer id,@RequestParam("file") MultipartFile file) throws IOException {
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

    @RequestMapping(path = "/user_upload_update_bpath",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_upload_update_bpath(@RequestParam("id")Integer id,@RequestParam("file") MultipartFile file) throws IOException {
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

    @RequestMapping(path = "/user_insert",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_insert(User user){
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

    @RequestMapping(path = "/user_insert_upath",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_insert_upath(@RequestParam("file")MultipartFile file) throws IOException {
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

    @RequestMapping(path = "/user_insert_bpath",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_insert_bpath(@RequestParam("file")MultipartFile file) throws IOException {
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

    @RequestMapping(path = "/user_update",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_upload_update(User user){
        Integer uid = user.getUid();
        User userById = userService.getUserById(uid);
        user.setUpath(userById.getUpath());
        user.setBpath(userById.getBpath());
        boolean update = userService.userUpdate(user);
        redisUtil.delete("users");
        return update;
    }

    @RequestMapping(path = "/user_delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean user_delete(@PathVariable("id")Integer id){
        boolean delete = userService.userDelete(id);
        redisUtil.delete("users");
        return delete;
    }

    @RequestMapping(path = "/user_delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean service_Delete(String [] ids){
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
