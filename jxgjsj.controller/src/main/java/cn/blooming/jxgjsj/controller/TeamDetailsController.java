package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.CaseDetailImage;
import cn.blooming.jxgjsj.api.CaseDetailResponse;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.*;
import cn.blooming.jxgjsj.service.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计团队详情
 * */
@Api(tags = {"设计团队详情"})
@Controller
@SessionAttributes(value = {
        "menus", "services", "copyrights", "wechat_qrcodes","logo","user","teamDetail"
})
public class TeamDetailsController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserService userService;
    @Autowired
    private DecorationStypeDetailsService stypeDetailsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseTypeService houseTypeService;

    @ShowLogger(info = "设计团队详情页面")
    @RequestMapping(path = "/toTeam_XX/{id}",method = RequestMethod.GET)
    public String toTeam(Model model, @PathVariable("id") Integer id){
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
        //设计师列表
        User user = userService.getUserById(id);

        CaseDetailResponse detailResponse=new CaseDetailResponse();
        List<DecorationStypeDetails> details = stypeDetailsService.getDecorationStypeDetails();
        List<CaseDetailImage> imageLists = new ArrayList<>();
        for (DecorationStypeDetails ds : details) {
            CaseDetailImage caseDetailImage = new CaseDetailImage();
            caseDetailImage.setDid(ds.getDid());
            List<Image> did = imageService.getImageByDid(ds.getDid());
            caseDetailImage.setPath(did.get(0).getPath());
            House houseById = houseService.getHouseById(ds.getHid());
            HouseType houseType = houseTypeService.getHouseTypeById(houseById.getType());
            String title = " 【" + ds.getName() + "】 " + houseType.getType() + " " + houseById.getSize() + "平方 " + houseById.getMoney() + "万";
            caseDetailImage.setTitle(title);
            imageLists.add(caseDetailImage);
        }
        detailResponse.setImageLists(imageLists);
        model.addAttribute("teamDetail", detailResponse);

        model.addAttribute("menus",menus);
        model.addAttribute("services",services);
        model.addAttribute("copyrights",copyrights);
        model.addAttribute("wechat_qrcodes",wechat_qrcodes);
        model.addAttribute("logo",logo);
        model.addAttribute("user",user);

        return "team_xx";
    }

}
