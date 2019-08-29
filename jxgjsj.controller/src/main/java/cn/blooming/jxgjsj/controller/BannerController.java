package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
import com.alibaba.fastjson.JSON;
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
 * 轮播图片配置化
 * */
@Api(tags = {"轮播图片配置化"})
@Controller
@Log4j
public class BannerController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private ConfigService configService;

    @RequestMapping(path = "/toBanner",method = RequestMethod.GET)
    public String toBanner(Model model){
        List<Config> banners;
        if(StringUtils.isEmpty(redisUtil.get("banners"))){
            banners = configService.getConfigs("banner");
            redisUtil.set("banners", JSON.toJSONString(banners));
        }else{
            banners = (List<Config>) JSON.parse(redisUtil.get("banners").toString());
        }
        model.addAttribute("banners",banners);
        return "banner";
    }

    @RequestMapping(path = "/toBannerInsert",method = RequestMethod.GET)
    public String toBannerInsert(){
        return "create_banner";
    }

    @RequestMapping(path = "/toBannerUpdate/{id}",method = RequestMethod.GET)
    public String toBannerUpdate(Model model,@PathVariable("id") Integer id){
        Config banner = configService.getConfigById(id);
        model.addAttribute("banner",banner);
        return "update_banner";
    }

    @RequestMapping(path = "/banner_upload_create",method = RequestMethod.POST)
    @ResponseBody
    public boolean banner_upload_create(@RequestParam("file")MultipartFile file) throws IOException {

        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCtype("banner");
            config.setCvalue(savePath+fileName);
            boolean insert = configService.configInsert(config);
            if(upload==true && insert==true){
                log.info("轮播图片新增成功");
                redisUtil.delete("banners");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "/banner_upload_update",method = RequestMethod.POST)
    @ResponseBody
    public boolean banner_upload_update(@RequestParam("id") Integer id,@RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCtype("banner");
            config.setCid(id);
            config.setCvalue(savePath+fileName);
            boolean update = configService.configUpdate(config);
            if(upload == true && update == true){
                log.info("轮播图片修改成功");
                redisUtil.delete("banners");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "/banner_delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean banner_delete(@PathVariable("id")Integer id){
        boolean delete = configService.configDelete(id);
        redisUtil.delete("banners");
        return delete;
    }

    @RequestMapping(path = "/banner_delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean banner_delete(String [] ids){
        for (String id : ids) {
            id=id.replace("[","");
            id=id.replace("]","");
            Integer mid = Integer.valueOf(id);
            configService.configDelete(mid);
        }
        redisUtil.delete("banners");
        return true;
    }

}
