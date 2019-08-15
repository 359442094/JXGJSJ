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
 * 二维码配置
 * */
@Api(tags = {"二维码配置"})
@Controller
@Log4j
public class QrCodeController {

    @Value("${ftp.savePath}")
    private String savePath;
    @Autowired
    private FtpClientUtil ftpClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @RequestMapping(path = "/toQrCode",method = RequestMethod.GET)
    public String toQrCode(Model model){
        List<Config> qrcodes;
        if(StringUtils.isEmpty(redisUtil.get("qrcodes"))){
            qrcodes = configService.getConfigs("wechat_qrcode");
            redisUtil.set("qrcodes", JSON.toJSONString(qrcodes));
        }else{
            qrcodes = (List<Config>) JSON.parse(redisUtil.get("qrcodes").toString());
        }
        model.addAttribute("qrcodes",qrcodes);
        return "qrcode";
    }

    @RequestMapping(path = "/toQrCodeUpdate/{id}",method = RequestMethod.GET)
    public String toQrCodeUpdate(Model model,@PathVariable("id")Integer id){
        Config qrcode = configService.getConfigById(id);
        model.addAttribute("qrcode",qrcode);
        return "update_qrcode";
    }

    @RequestMapping(path = "/qrcode_upload_update",method = RequestMethod.POST)
    @ResponseBody
    public boolean qrcode_upload_update(@RequestParam("id")Integer id, @RequestParam("file")MultipartFile file) throws IOException {
        if(!StringUtils.isEmpty(file)){
            String fileName = file.getOriginalFilename();
            boolean upload = ftpClientUtil.startUpload(file.getInputStream(), fileName);
            Config config=new Config();
            config.setCid(id);
            config.setCtype("wechat_qrcode");
            config.setCvalue(savePath+fileName);
            boolean update = configService.configUpdate(config);
            if(upload == true && update == true){
                log.info("扫码图片修改成功");
                redisUtil.delete("qrcodes");
                return true;
            }
        }
        return false;
    }


}
