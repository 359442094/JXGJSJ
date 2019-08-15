package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.CaseImage;
import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.*;
import cn.blooming.jxgjsj.service.*;
import cn.blooming.jxgjsj.service.exte.DetailsService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 风格案例
 */
@Api(tags = {"风格案例"})
@Controller
@SessionAttributes(value = {
        "menus", "services", "copyrights",
        "wechat_qrcodes", "logo", "caseImageList",
        "caseImagepage", "stypes", "typeAll"
})
public class CaseController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private DecorationStypeDetailsService detailsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseTypeService houseTypeService;
    @Autowired
    private DetailsService myService;
    @Autowired
    private DecorationStypeService decorationStypeService;

    @ShowLogger(info = "风格案例页面")
    @RequestMapping(path = "/toCase", method = RequestMethod.GET)
    public String toCase(Model model, @RequestParam(value = "index", defaultValue = "1") Integer index,
                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
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

        //装修风格项
        List<DecorationStype> stypes;
        if (StringUtils.isEmpty(redisUtil.get("stypes"))) {
            stypes = decorationStypeService.getDecorationStypeServices();
            redisUtil.set("stypes", JSON.toJSONString(stypes));
        } else {
            stypes = (List<DecorationStype>) JSON.parse(redisUtil.get("stypes").toString());
        }
        List<HouseType> typeAll;
        if (StringUtils.isEmpty(redisUtil.get("typeAll"))) {
            typeAll = houseTypeService.getHouseTypeAll();
            redisUtil.set("typeAll", JSON.toJSONString(typeAll));
        } else {
            typeAll = (List<HouseType>) JSON.parse(redisUtil.get("typeAll").toString());
        }

        model.addAttribute("menus", menus);
        model.addAttribute("services", services);
        model.addAttribute("copyrights", copyrights);
        model.addAttribute("wechat_qrcodes", wechat_qrcodes);
        model.addAttribute("logo", logo);
        model.addAttribute("stypes", stypes);
        model.addAttribute("typeAll", typeAll);
        Page<DecorationStypeDetails> decorationStypeDetailsPage = detailsService.getDecorationStypeDetailsPage(index, size);
        List<CaseImage> caseImageList = new ArrayList<>();

        for (DecorationStypeDetails detail : decorationStypeDetailsPage.getResult()) {
            CaseImage caseImage = new CaseImage();
            caseImage.setDid(detail.getDid());

            List<Image> imageByDid = imageService.getImageByDid(detail.getDid());
            caseImage.setPath(imageByDid.get(0).getPath());

            User userById = userService.getUserById(detail.getUid());
            caseImage.setUser(userById);

            House houseById = houseService.getHouseById(detail.getHid());
            HouseType houseTypeById = houseTypeService.getHouseTypeById(houseById.getType());

            String title = " 【" + detail.getName() + "】 " + houseTypeById.getType() + " " + houseById.getSize() + "平方 " + houseById.getMoney() + "万";
            caseImage.setTitle(title);

            caseImageList.add(caseImage);

        }

        model.addAttribute("caseImageList", caseImageList);
        model.addAttribute("caseImagepage", decorationStypeDetailsPage);

        return "case";
    }

    @ShowLogger(info = "风格案例页面")
    @RequestMapping(path = "/toCaseLike", method = RequestMethod.GET)
    public String toCaseLike(Model model, @RequestParam(value = "index", defaultValue = "1") Integer index,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             @RequestParam(value = "style", defaultValue = "") String style,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             @RequestParam(value = "startSize", defaultValue = "0") Integer startSize,
                             @RequestParam(value = "endSize", defaultValue = "0") Integer endSize) {

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

        //装修风格项
        List<DecorationStype> stypes;
        if (StringUtils.isEmpty(redisUtil.get("stypes"))) {
            stypes = decorationStypeService.getDecorationStypeServices();
            redisUtil.set("stypes", JSON.toJSONString(stypes));
        } else {
            stypes = (List<DecorationStype>) JSON.parse(redisUtil.get("stypes").toString());
        }
        List<HouseType> typeAll;
        if (StringUtils.isEmpty(redisUtil.get("typeAll"))) {
            typeAll = houseTypeService.getHouseTypeAll();
            redisUtil.set("typeAll", JSON.toJSONString(typeAll));
        } else {
            typeAll = (List<HouseType>) JSON.parse(redisUtil.get("typeAll").toString());
        }

        model.addAttribute("menus", menus);
        model.addAttribute("services", services);
        model.addAttribute("copyrights", copyrights);
        model.addAttribute("wechat_qrcodes", wechat_qrcodes);
        model.addAttribute("logo", logo);
        model.addAttribute("stypes", stypes);
        model.addAttribute("typeAll", typeAll);

        List<CaseImage> caseImageList = new ArrayList<>();
        Page<DecorationStypeDetails> decorationStypeDetailsPage = myService.details(index, size, style, type, startSize, endSize);

        for (DecorationStypeDetails detail : decorationStypeDetailsPage.getResult()) {

            CaseImage caseImage = new CaseImage();
            caseImage.setDid(detail.getDid());

            List<Image> imageByDid = imageService.getImageByDid(detail.getDid());
            caseImage.setPath(imageByDid.get(0).getPath());

            User userById = userService.getUserById(detail.getUid());
            caseImage.setUser(userById);

            House houseById = houseService.getHouseById(detail.getHid());
            HouseType houseTypeById = houseTypeService.getHouseTypeById(houseById.getType());

            String title = " 【" + detail.getName() + "】 " + houseTypeById.getType() + " " + houseById.getSize() + "平方 " + houseById.getMoney() + "万";
            caseImage.setTitle(title);

            caseImageList.add(caseImage);

        }

        model.addAttribute("caseImageList", caseImageList);
        model.addAttribute("caseImagepage", decorationStypeDetailsPage);

        return "case";
    }

}
