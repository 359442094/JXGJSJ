package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@Api(tags = {"版权配置"})
public class CopyrightController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(path = "/toCopyright",method = RequestMethod.GET)
    public String toCopyright(Model model){
        List<Config> copyrights;
        if(StringUtils.isEmpty(redisUtil.get("copyrights"))){
            copyrights = configService.getConfigs("copyright");
            redisUtil.set("copyrights",JSON.toJSONString(copyrights));
        }else{
            copyrights = (List<Config>)JSON.parse(redisUtil.get("copyrights").toString());
        }
        model.addAttribute("copyrights",copyrights);
        return "copyright";
    }

    @RequestMapping(path = "/toCopyrightUpdate/{id}",method = RequestMethod.GET)
    public String toCopyrightUpdate(Model model,@PathVariable("id") Integer id){
        Config copyright = configService.getConfigById(id);
        model.addAttribute("copyright",copyright);
        return "update_copyright";
    }

    @RequestMapping(path = "/copyright_Update",method = RequestMethod.POST)
    @ResponseBody
    public boolean copyright_Update(Config config){
        config.setCtype("copyright");
        boolean flag = configService.configUpdate(config);
        redisUtil.delete("copyrights");
        return flag;
    }

    @RequestMapping(path = "/copyright_Delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean copyright_Update(@PathVariable("id") Integer id){
        boolean flag = configService.configDelete(id);
        redisUtil.delete("copyrights");
        return flag;
    }

}
