package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.redis.RedisUtil;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.model.entity.Menu;
import cn.blooming.jxgjsj.service.MenuService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ShowLogger(info = "跳转至菜单配置页面")
    @ApiOperation(value = "跳转至菜单配置页面",notes = "跳转至菜单配置页面")
    @RequestMapping(path = "/menuAdmin",method = RequestMethod.GET)
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

    @ShowLogger(info = "跳转至菜单添加页面")
    @ApiOperation(value = "跳转至菜单添加页面",notes = "跳转至菜单添加页面")
    @RequestMapping(path = "/menuAdmin/info",method = RequestMethod.GET)
    public String toMenuCreate(){
        return "create_menu";
    }

    @ShowLogger(info = "查询单个菜单")
    @ApiOperation(value = "查询单个菜单",notes = "查询单个菜单")
    @RequestMapping(path = "/menuAdmin/info/{id}",method = RequestMethod.GET)
    public String toMenuUpdate(Model model,@PathVariable("id") Integer id){
        Menu menu = menuService.getMenusById(id);
        model.addAttribute("menu",menu);
        return "update_menu";
    }

    @ShowLogger(info = "新增单个菜单")
    @ApiOperation(value = "新增单个菜单",notes = "新增单个菜单")
    @RequestMapping(path = "/menuAdmin/info",method = RequestMethod.POST)
    @ResponseBody
    public boolean menuInsert(Menu menu){
        if(!StringUtils.isEmpty(menu)&&!StringUtils.isEmpty(menu.getMname())&&!StringUtils.isEmpty(menu.getMpath())){
            boolean menusInsert = menuService.menusInsert(menu);
            redisUtil.delete("menus");
            return menusInsert;
        }
        return false;
    }

    @ShowLogger(info = "修改单个菜单")
    @ApiOperation(value = "修改单个菜单",notes = "修改单个菜单")
    @RequestMapping(path = "/menuAdmin/info",method = RequestMethod.PUT)
    @ResponseBody
    public boolean menuUpdate(Menu menu){
        boolean menusUpdate = menuService.menusUpdate(menu);
        redisUtil.delete("menus");
        return menusUpdate;
    }

    @ShowLogger(info = "删除单个菜单")
    @ApiOperation(value = "删除单个菜单",notes = "删除单个菜单")
    @RequestMapping(path = "/menuAdmin/info/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean menuDelete(@PathVariable("id") Integer id){
        boolean menusDetele = menuService.menusDetele(id);
        redisUtil.delete("menus");
        return menusDetele;
    }

    @ShowLogger(info = "删除多个菜单")
    @ApiOperation(value = "删除多个菜单",notes = "删除多个菜单")
    @RequestMapping(path = "/menuAdmin/info",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean menuDelete(String [] ids){
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
