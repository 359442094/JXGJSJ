package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.service.ConfigService;
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
@Api(tags = {"客服电话配置"})
public class ServiceController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigService configService;

    @ShowLogger(info = "跳转至客服后端页面")
    @ApiOperation(value = "跳转至客服后端页面",notes = "跳转至客服后端页面")
    @RequestMapping(path = "/serviceAdmin",method = RequestMethod.GET)
    public String getServices(Model model){
        List<Config> services = configService.getConfigs("service");
        model.addAttribute("services",services);
        return "service";
    }

    @ShowLogger(info = "查询单个客服")
    @ApiOperation(value = "查询单个客服",notes = "查询单个客服")
    @RequestMapping(path = "/serviceAdmin/{id}",method = RequestMethod.GET)
    public String serviceById(Model model,@PathVariable("id") Integer id){
        Config config = configService.getConfigById(id);
        model.addAttribute("config",config);
        return "update_service";
    }

    @ShowLogger(info = "跳转至客服添加页面")
    @ApiOperation(value = "跳转至客服添加页面",notes = "跳转至客服添加页面")
    @RequestMapping(path = "/serviceAdmin/info",method = RequestMethod.GET)
    public String toServiceInsert(){
        return "create_service";
    }

    @ShowLogger(info = "添加单个客服")
    @ApiOperation(value = "添加单个客服",notes = "添加单个客服")
    @RequestMapping(path = "/serviceAdmin/info",method = RequestMethod.POST)
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

    @ShowLogger(info = "修改单个客服")
    @ApiOperation(value = "修改单个客服",notes = "修改单个客服")
    @RequestMapping(path = "/serviceAdmin/info",method = RequestMethod.PUT)
    @ResponseBody
    public boolean serviceUpdate(Config config){
        if(!StringUtils.isEmpty(config)&&!StringUtils.isEmpty(config.getCid())&&!StringUtils.isEmpty(config.getCtype())&&!StringUtils.isEmpty(config.getCvalue())){
            boolean configUpdate = configService.configUpdate(config);
            redisUtil.delete("services");
            return configUpdate;
        }
        return false;
    }

    @ShowLogger(info = "删除单个客服")
    @ApiOperation(value = "删除单个客服",notes = "删除单个客服")
    @RequestMapping(path = "/serviceAdmin/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean service_Delete(@PathVariable("id") Integer id){
        boolean configDelete = configService.configDelete(id);
        redisUtil.delete("services");
        return configDelete;
    }

    @ShowLogger(info = "删除多个客服")
    @ApiOperation(value = "删除多个客服",notes = "删除多个客服")
    @RequestMapping(path = "/serviceAdmin",method = RequestMethod.DELETE)
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
