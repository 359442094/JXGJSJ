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



}
