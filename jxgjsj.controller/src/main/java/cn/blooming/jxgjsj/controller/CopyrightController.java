package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"版权说明配置"})
public class CopyrightController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisUtil redisUtil;

    @ShowLogger(info = "跳转至版权说明后台页面")
    @ApiOperation(value = "跳转至版权说明后台页面",notes = "跳转至版权说明后台页面")
    @RequestMapping(path = "/copyrightAdmin",method = RequestMethod.GET)
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

    @ShowLogger(info = "查询单个版权说明")
    @ApiOperation(value = "查询单个版权说明",notes = "查询单个版权说明")
    @RequestMapping(path = "/copyrightAdmin/{id}",method = RequestMethod.GET)
    public String toCopyrightUpdate(Model model,@PathVariable("id") Integer id){
        Config copyright = configService.getConfigById(id);
        model.addAttribute("copyright",copyright);
        return "update_copyright";
    }

    @ShowLogger(info = "修改单个版权说明")
    @ApiOperation(value = "修改单个版权说明",notes = "修改单个版权说明")
    @RequestMapping(path = "/copyrightAdmin",method = RequestMethod.PUT)
    @ResponseBody
    public boolean copyrightUpdate(Config config){
        config.setCtype("copyright");
        boolean flag = configService.configUpdate(config);
        redisUtil.delete("copyrights");
        return flag;
    }

    @ShowLogger(info = "删除单个版权说明")
    @ApiOperation(value = "删除单个版权说明",notes = "删除单个版权说明")
    @RequestMapping(path = "/copyrightAdmin/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean copyrightDelete(@PathVariable("id") Integer id){
        boolean flag = configService.configDelete(id);
        redisUtil.delete("copyrights");
        return flag;
    }

}
