package cn.blooming.jxgjsj.api;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.model.entity.Menu;
import cn.blooming.jxgjsj.service.ConfigService;
import cn.blooming.jxgjsj.service.MenuService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.SessionAttributes;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionAttributes(value = {
        "menus", "services", "copyrights", "wechat_qrcodes","logo"
})
public class PublicData {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ConfigService configService;

    public void getPublicData(Model model){
        //网页图标
        Config logo;
        if(StringUtils.isEmpty(redisUtil.get("logo"))){
            logo = configService.getConfigs("logo").get(0);
            redisUtil.set("logo",logo);
        }else{
            logo = (Config) redisUtil.get("logo");
        }

        //菜单项
        List<Menu> menus = new ArrayList<>();
        if(StringUtils.isEmpty(redisUtil.get("menus"))){
            menus = menuService.getMenus();
            redisUtil.set("menus", JSON.toJSONString(menus));
        }else{
            menus = (List<Menu>)JSON.parse(redisUtil.get("menus").toString());
        }
        //客服项
        List<Config> services;
        if(StringUtils.isEmpty(redisUtil.get("services"))){
            services = configService.getConfigs("service");
            redisUtil.set("services",JSON.toJSONString(services));
        }else{
            services = (List<Config>)JSON.parse(redisUtil.get("services").toString());
        }
        //版权项
        List<Config> copyrights;
        if(StringUtils.isEmpty(redisUtil.get("copyrights"))){
            copyrights = configService.getConfigs("copyright");
            redisUtil.set("copyrights",JSON.toJSONString(copyrights));
        }else{
            copyrights = (List<Config>)JSON.parse(redisUtil.get("copyrights").toString());
        }
        //微信二维码
        List<Config> wechat_qrcodes;
        if(StringUtils.isEmpty(redisUtil.get("wechat_qrcodes"))){
            wechat_qrcodes =configService.getConfigs("wechat_qrcode");
            redisUtil.set("wechat_qrcodes",JSON.toJSONString(wechat_qrcodes));
        }else{
            wechat_qrcodes = (List<Config>)JSON.parse(redisUtil.get("wechat_qrcodes").toString());
        }

        model.addAttribute("menus",menus);
        model.addAttribute("services",services);
        model.addAttribute("copyrights",copyrights);
        model.addAttribute("wechat_qrcodes",wechat_qrcodes);
        model.addAttribute("logo",logo);

    }

}
