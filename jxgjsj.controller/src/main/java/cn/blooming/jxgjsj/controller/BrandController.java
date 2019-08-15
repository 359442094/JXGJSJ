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
 * 合作品牌配置
 * */
@Api(tags = {"合作品牌配置"})
@Controller
@Log4j
public class BrandController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @RequestMapping(path = "/toBrand",method = RequestMethod.GET)
    public String toBrand(Model model){
        List<Config> brands;
        if(StringUtils.isEmpty(redisUtil.get("brands"))){
            brands = configService.getConfigs("brand");
            redisUtil.set("brands", JSON.toJSONString(brands));
        }else{
            brands =(List<Config>) JSON.parse(redisUtil.get("brands").toString());
        }
        model.addAttribute("brands",brands);
        return "brand";
    }

    @RequestMapping(path = "/toBrandInsert",method = RequestMethod.GET)
    public String toBrandInsert(){
        return "create_brand";
    }

    @RequestMapping(path = "/toBrandUpdate/{id}",method = RequestMethod.GET)
    public String toBrandUpdate(Model model,@PathVariable("id")Integer id){
        Config brand = configService.getConfigById(id);
        model.addAttribute("brand",brand);
        return "update_brand";
    }

    @RequestMapping(path = "/brand_upload_update",method = RequestMethod.POST)
    @ResponseBody
    public boolean brand_upload_update(@RequestParam("id")Integer id, @RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCid(id);
            config.setCtype("brand");
            config.setCvalue(savePath+fileName);
            boolean update = configService.configUpdate(config);
            if(upload == true && update == true){
                log.info("合作品牌图标修改成功");
                redisUtil.delete("brands");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "/brand_upload_insert",method = RequestMethod.POST)
    @ResponseBody
    public boolean brand_upload_insert(@RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCtype("brand");
            config.setCvalue(savePath+fileName);
            boolean update = configService.configInsert(config);
            if(upload == true && update == true){
                log.info("合作品牌图标新增成功");
                redisUtil.delete("brands");
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "/brand_upload_delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean brand_upload_delete(@PathVariable("id")Integer id){
        boolean delete = configService.configDelete(id);
        redisUtil.delete("brands");
        return delete;
    }

    @RequestMapping(path = "/brand_upload_delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean brand_upload_delete(String [] ids){
        for (String id : ids) {
            id=id.replace("[","");
            id=id.replace("]","");
            Integer mid = Integer.valueOf(id);
            configService.configDelete(mid);
        }
        redisUtil.delete("brands");
        return true;
    }

}
