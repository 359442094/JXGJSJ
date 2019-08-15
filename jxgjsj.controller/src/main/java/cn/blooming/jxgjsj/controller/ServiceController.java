package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
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
@Api(tags = {"客服电话配置"})
public class ServiceController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @RequestMapping(path = "/toService",method = RequestMethod.GET)
    public String getServices(Model model){
        List<Config> services = configService.getConfigs("service");
        model.addAttribute("services",services);
        return "service";
    }

    @RequestMapping(path = "/toServiceInsert",method = RequestMethod.GET)
    public String toServiceInsert(){
        return "create_service";
    }

    @RequestMapping(path = "/toServiceUpdate/{id}",method = RequestMethod.GET)
    public String service_Insert(Model model,@PathVariable("id") Integer id){
        Config config = configService.getConfigById(id);
        model.addAttribute("config",config);
        return "update_service";
    }


    @RequestMapping(path = "/service_Insert",method = RequestMethod.POST)
    @ResponseBody
    public boolean serviceInsert(Config config){
        if(!StringUtils.isEmpty(config)&&!StringUtils.isEmpty(config.getCvalue())){
            config.setCtype("service");
            boolean configInsert = configService.configInsert(config);
            redisUtil.delete("services");
            return configInsert;
        }
        return false;
    }

    @RequestMapping(path = "/service_Update",method = RequestMethod.POST)
    @ResponseBody
    public boolean service_Update(Config config){
        if(!StringUtils.isEmpty(config)&&!StringUtils.isEmpty(config.getCid())&&!StringUtils.isEmpty(config.getCtype())&&!StringUtils.isEmpty(config.getCvalue())){
            boolean configUpdate = configService.configUpdate(config);
            redisUtil.delete("services");
            return configUpdate;
        }
        return false;
    }

    @RequestMapping(path = "/service_Delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean service_Delete(@PathVariable("id") Integer id){
        boolean configDelete = configService.configDelete(id);
        redisUtil.delete("services");
        return configDelete;
    }

    @RequestMapping(path = "/service_Delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean service_Delete(String [] ids){
        for (String id : ids) {
            id=id.replace("[","");
            id=id.replace("]","");
            Integer mid = Integer.valueOf(id);
            configService.configDelete(mid);
        }
        redisUtil.delete("services");
        return true;
    }

}
