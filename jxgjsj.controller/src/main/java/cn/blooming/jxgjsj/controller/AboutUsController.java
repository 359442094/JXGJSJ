package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.AboutUsAdminResponse;
import cn.blooming.jxgjsj.api.ftp.FtpClientUtil;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.model.entity.Menu;
import cn.blooming.jxgjsj.service.ConfigService;
import cn.blooming.jxgjsj.service.MenuService;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"关于我们"})
@Log4j
@Controller
@SessionAttributes(value = {
        "menus", "services", "copyrights", "wechat_qrcodes", "logo", "aboutus"
})
public class AboutUsController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MenuService menuService;

    @ShowLogger(info = "跳转至关于我们前端页面")
    @ApiOperation(value = "跳转至关于我们前端页面",notes = "跳转至关于我们前端页面")
    @RequestMapping(path = "/aboutUs", method = RequestMethod.GET)
    public String aboutUs(Model model) {
        //网页图标
        Config logo;
        if (StringUtils.isEmpty(redisUtil.get("logo"))) {
            logo = configService.getConfigs("logo").get(0);
            redisUtil.set("logo", logo);
        } else {
            logo = (Config) redisUtil.get("logo");
        }

        //菜单项
        List<Menu> menus = new ArrayList<>();
        if (StringUtils.isEmpty(redisUtil.get("menus"))) {
            menus = menuService.getMenus();
            redisUtil.set("menus", JSON.toJSONString(menus));
        } else {
            menus = (List<Menu>) JSON.parse(redisUtil.get("menus").toString());
        }
        //客服项
        List<Config> services;
        if (StringUtils.isEmpty(redisUtil.get("services"))) {
            services = configService.getConfigs("service");
            redisUtil.set("services", JSON.toJSONString(services));
        } else {
            services = (List<Config>) JSON.parse(redisUtil.get("services").toString());
        }
        //版权项
        List<Config> copyrights;
        if (StringUtils.isEmpty(redisUtil.get("copyrights"))) {
            copyrights = configService.getConfigs("copyright");
            redisUtil.set("copyrights", JSON.toJSONString(copyrights));
        } else {
            copyrights = (List<Config>) JSON.parse(redisUtil.get("copyrights").toString());
        }
        //微信二维码
        List<Config> wechat_qrcodes;
        if (StringUtils.isEmpty(redisUtil.get("wechat_qrcodes"))) {
            wechat_qrcodes = configService.getConfigs("wechat_qrcode");
            redisUtil.set("wechat_qrcodes", JSON.toJSONString(wechat_qrcodes));
        } else {
            wechat_qrcodes = (List<Config>) JSON.parse(redisUtil.get("wechat_qrcodes").toString());
        }
        //关于我们logo
        List<Config> aboutusLogo = configService.getConfigs("aboutus_logo");
        List<Config> aboutusText = configService.getConfigs("aboutus_text");
        List<Config> aboutusAddr = configService.getConfigs("aboutus_addr");
        if (aboutusLogo.size() >= 1 && aboutusText.size() >= 1 && aboutusAddr.size() >= 1) {
            AboutUsAdminResponse aboutUsResponse = new AboutUsAdminResponse();
            aboutUsResponse.setLogo(aboutusLogo.get(0).getCvalue());
            aboutUsResponse.setText(aboutusText.get(0).getCvalue());
            aboutUsResponse.setAddr(aboutusAddr.get(0).getCvalue());
            model.addAttribute("aboutus", aboutUsResponse);
            redisUtil.set("aboutus", aboutUsResponse);
        }

        model.addAttribute("menus", menus);
        model.addAttribute("services", services);
        model.addAttribute("copyrights", copyrights);
        model.addAttribute("wechat_qrcodes", wechat_qrcodes);
        model.addAttribute("logo", logo);


        return "about_us";
    }

    @ShowLogger(info = "跳转至关于我们后端页面")
    @ApiOperation(value = "跳转至关于我们后端页面",notes = "跳转至关于我们后端页面")
    @RequestMapping(path = "/aboutusAdmin",method = RequestMethod.GET)
    public String aboutusAdmin(Model model){
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

    @ShowLogger(info = "查询关于我们后端数据")
    @ApiOperation(value = "查询关于我们后端数据",notes = "查询关于我们后端数据")
    @RequestMapping(path = "/aboutusAdmin/info",method = RequestMethod.GET)
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

    @ShowLogger(info = "修改关于我们LOGO")
    @ApiOperation(value = "修改关于我们LOGO",notes = "修改关于我们LOGO")
    @RequestMapping(path = "/aboutusAdmin/upload/logo",method = RequestMethod.PUT)
    @ResponseBody
    public boolean aboutusAdminUpdateLogo(@RequestParam("file") MultipartFile file) throws IOException {
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

    @ShowLogger(info = "修改关于我们数据")
    @ApiOperation(value = "修改关于我们数据",notes = "修改关于我们数据")
    @RequestMapping(path = "/aboutusAdmin/info",method = RequestMethod.PUT)
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
