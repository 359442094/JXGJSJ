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
import java.util.Vector;

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

    @ShowLogger(info = "跳转至合作品牌后端页面")
    @ApiOperation(value = "跳转至合作品牌后端页面",notes = "跳转至合作品牌页面")
    @RequestMapping(path = "/brandAdmin",method = RequestMethod.GET)
    public String brandAdmin(Model model){
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

    @ShowLogger(info = "跳转至合作品牌添加页面")
    @ApiOperation(value = "跳转至合作品牌添加页面",notes = "跳转至合作品牌添加页面")
    @RequestMapping(path = "/brandAdmin/info",method = RequestMethod.GET)
    public String toBrandInsert(){
        return "create_brand";
    }

    @ShowLogger(info = "查询单个合作品牌")
    @ApiOperation(value = "查询单个合作品牌",notes = "查询单个合作品牌")
    @RequestMapping(path = "/brandAdmin/info/{id}",method = RequestMethod.GET)
    public String toBrandUpdate(Model model,@PathVariable("id")Integer id){
        Config brand = configService.getConfigById(id);
        model.addAttribute("brand",brand);
        return "update_brand";
    }

    @ShowLogger(info = "修改单个合作品牌LOGO")
    @ApiOperation(value = "修改单个合作品牌LOGO",notes = "修改单个合作品牌LOGO")
    @RequestMapping(path = "/brandAdmin/upload/logo",method = RequestMethod.PUT)
    @ResponseBody
    public boolean brandUpload(@RequestParam("id")Integer id, @RequestParam("file")MultipartFile file) throws IOException {
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

    @ShowLogger(info = "新增单个合作品牌")
    @ApiOperation(value = "新增单个合作品牌",notes = "新增单个合作品牌")
    @RequestMapping(path = "/brandAdmin/info",method = RequestMethod.POST)
    @ResponseBody
    public boolean brandUploadSave(@RequestParam("file")MultipartFile file) throws IOException {
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

    @ShowLogger(info = "删除单个合作品牌")
    @ApiOperation(value = "删除单个合作品牌",notes = "删除单个合作品牌")
    @RequestMapping(path = "/brandAdmin/info/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean brandUploadDelete(@PathVariable("id")Integer id){
        boolean delete = configService.configDelete(id);
        redisUtil.delete("brands");
        return delete;
    }

    @ShowLogger(info = "删除多个合作品牌")
    @ApiOperation(value = "删除多个合作品牌",notes = "删除多个合作品牌")
    @RequestMapping(path = "/brandAdmin/info",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean brandUploadDelete(String [] ids){
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
