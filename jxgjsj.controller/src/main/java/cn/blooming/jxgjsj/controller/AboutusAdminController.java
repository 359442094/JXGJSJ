package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.AboutUsAdminResponse;
import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Log4j
@Controller
public class AboutusAdminController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @RequestMapping(path = "/toAboutusAdmin",method = RequestMethod.GET)
    public String toAboutusAdmin(Model model){
        AboutUsAdminResponse response=new AboutUsAdminResponse();
            Config logo = configService.getConfigs("aboutus_logo").get(0);
            Config text = configService.getConfigs("aboutus_text").get(0);
            Config addr = configService.getConfigs("aboutus_addr").get(0);
            response.setAddr(addr.getCvalue());
            response.setText(text.getCvalue());
            response.setLogo(logo.getCvalue());
        model.addAttribute("response",response);
        return "aboutus_admin";
    }

    @RequestMapping(path = "/toAboutusAdminUpdate",method = RequestMethod.GET)
    public String toAboutusAdminUpdate(Model model){
        AboutUsAdminResponse response=new AboutUsAdminResponse();
            Config logo = configService.getConfigs("aboutus_logo").get(0);
            Config text = configService.getConfigs("aboutus_text").get(0);
            Config addr = configService.getConfigs("aboutus_addr").get(0);
            response.setAddr(addr.getCvalue());
            response.setText(text.getCvalue());
            response.setLogo(logo.getCvalue());
        model.addAttribute("response",response);
        return "update_aboutus_admin";
    }

    @RequestMapping(path = "/updateAboutusAdmin_UpdateLogo",method = RequestMethod.POST)
    @ResponseBody
    public boolean updateAboutusAdmin_UpdateLogo(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            String fileName=file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            boolean flag = ftpClientUtil.startUpload(inputStream, fileName);
            if(flag){
                Config config = configService.getConfigs("aboutus_logo").get(0);
                config.setCtype("aboutus_logo");
                config.setCvalue(savePath+fileName);

                boolean row = configService.configUpdate(config);
                log.info("文件上传成功");
                return row;
            }
        }
        return false;
    }

    @RequestMapping(path = "/updateAboutusAdmin_Update",method = RequestMethod.POST)
    @ResponseBody
    public boolean updateAboutusAdmin(@RequestParam("text")String text,@RequestParam("addr")String addr){
        Config aboutus_text = configService.getConfigs("aboutus_text").get(0);
        aboutus_text.setCvalue(text);
        configService.configUpdate(aboutus_text);
        Config aboutus_addr = configService.getConfigs("aboutus_addr").get(0);
        aboutus_text.setCvalue(addr);
        configService.configUpdate(aboutus_addr);
        return true;
    }


}
