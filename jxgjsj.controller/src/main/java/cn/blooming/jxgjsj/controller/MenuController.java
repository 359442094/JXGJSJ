package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.entity.Menu;
import cn.blooming.jxgjsj.service.MenuService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"菜单配置"})
@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(path = "/toMenu",method = RequestMethod.GET)
    public String toMenu(Model model,@RequestParam(value = "name",defaultValue = "") String name){
        List<Menu> menus;

        if(StringUtils.isEmpty(redisUtil.get("menus"))){
            if(StringUtils.isEmpty(name)){
                menus = menuService.getMenus();
            }else{
                menus= menuService.getMenusLikeName(name);
            }
            redisUtil.set("menus",JSON.toJSONString(menus));
        }else {
            menus = (List<Menu>) JSON.parse(redisUtil.get("menus").toString());
        }
        model.addAttribute("menus",menus);
        return "menu";
    }

    @RequestMapping(path = "/toMenu_Create",method = RequestMethod.GET)
    public String toMenu_Create(){
        return "create_menu";
    }

    @RequestMapping(path = "/toMenu_Update/{id}",method = RequestMethod.GET)
    public String toMenu_Update(Model model,@PathVariable("id") Integer id){
        Menu menu = menuService.getMenusById(id);
        model.addAttribute("menu",menu);
        return "update_menu";
    }

    @RequestMapping(path = "/menu_Insert",method = RequestMethod.POST)
    @ResponseBody
    public boolean menu_Insert(Menu menu){
        if(!StringUtils.isEmpty(menu)&&!StringUtils.isEmpty(menu.getMname())&&!StringUtils.isEmpty(menu.getMpath())){
            boolean menusInsert = menuService.menusInsert(menu);
            redisUtil.delete("menus");
            return menusInsert;
        }
        return false;
    }

    @RequestMapping(path = "/menu_Update",method = RequestMethod.POST)
    @ResponseBody
    public boolean menu_Update(Menu menu){
        boolean menusUpdate = menuService.menusUpdate(menu);
        redisUtil.delete("menus");
        return menusUpdate;
    }

    @RequestMapping(path = "/menu_Delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean menu_Delete(@PathVariable("id") Integer id){
        boolean menusDetele = menuService.menusDetele(id);
        redisUtil.delete("menus");
        return menusDetele;
    }

    @RequestMapping(path = "/menu_Delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean menu_Delete(String [] ids){
        for (String id : ids) {
            id=id.replace("[","");
            id=id.replace("]","");
            Integer mid = Integer.valueOf(id);
            menuService.menusDetele(mid);
        }
        redisUtil.delete("menus");
        return true;
    }


}
