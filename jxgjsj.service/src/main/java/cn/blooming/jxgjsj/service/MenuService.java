package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenus();

    Menu getMenusById(Integer id);

    boolean menusInsert(Menu menu);

    boolean menusUpdate(Menu menu);

    boolean menusDetele(Integer id);

    List<Menu> getMenusLikeName(String name);

}
