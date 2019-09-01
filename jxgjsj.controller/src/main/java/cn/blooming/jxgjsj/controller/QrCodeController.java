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

    @ShowLogger(info = "跳转至二维码页面")
    @ApiOperation(value = "跳转至二维码页面",notes = "跳转至二维码页面")
    @RequestMapping(path = "/qrCode",method = RequestMethod.GET)
    public String qrCode(Model model){
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

    @ShowLogger(info = "查询单个二维码")
    @ApiOperation(value = "查询单个二维码",notes = "查询单个二维码")
    @RequestMapping(path = "/qrCode/{id}",method = RequestMethod.GET)
    public String toQrCodeInfo(Model model,@PathVariable("id")Integer id){
        Config qrcode = configService.getConfigById(id);
        model.addAttribute("qrcode",qrcode);
        return "update_qrcode";
    }

    @ShowLogger(info = "修改单个二维码")
    @ApiOperation(value = "修改单个二维码",notes = "修改单个二维码")
    @RequestMapping(path = "/qrcode/upload/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public boolean qrcodeUploadUpdate(@PathVariable("id")Integer id, @RequestParam("file")MultipartFile file) throws IOException {
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
