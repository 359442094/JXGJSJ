package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
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

/**
 * 网站图标配置
 * */
@Log4j
@Api(tags = {"网站图标配置"})
@Controller
public class LogoController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @ShowLogger(info = "跳转至图标配置页面")
    @ApiOperation(value = "跳转至图标配置页面",notes = "跳转至图标配置页面")
    @RequestMapping(path = "/logoAdmin",method = RequestMethod.GET)
    public String toLogo(Model model){
        List<Config> configs;
        if(StringUtils.isEmpty(redisUtil.get("logos"))){
            configs=configService.getConfigs("logo");
            redisUtil.set("logos", JSON.toJSONString(configs));
        }else{
            configs = (List<Config>) JSON.parse(redisUtil.get("logos").toString());
        }
        model.addAttribute("logos",configs);
        return "logo";
    }

    @ShowLogger(info = "查询单个图标")
    @ApiOperation(value = "查询单个图标",notes = "查询单个图标")
    @RequestMapping(path = "/logoAdmin/{id}",method = RequestMethod.GET)
    public String toLogoUpdate(Model model,@PathVariable("id")Integer id){
        Config logo = configService.getConfigById(id);
        model.addAttribute("logo",logo);
        return "update_logo";
    }

    @ShowLogger(info = "修改单个图标")
    @ApiOperation(value = "修改单个图标",notes = "修改单个图标")
    @RequestMapping(path = "/logoAdmin",method = RequestMethod.PUT)
    @ResponseBody
    public boolean logoUpdate(@RequestParam("id")Integer id, @RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCid(id);
            config.setCtype("logo");
            config.setCvalue(savePath+fileName);
            boolean update = configService.configUpdate(config);
            if(upload == true && update == true){
                log.info("网站图标修改成功");
                redisUtil.delete("logos");
                return true;
            }
        }
        return false;
    }

}
