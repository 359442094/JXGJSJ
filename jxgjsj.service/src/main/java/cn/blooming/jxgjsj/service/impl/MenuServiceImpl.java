package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.Menu;
import cn.blooming.jxgjsj.model.entity.MenuExample;
import cn.blooming.jxgjsj.model.mapper.MenuMapper;
import cn.blooming.jxgjsj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenus() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public Menu getMenusById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean menusInsert(Menu menu) {
        return menuMapper.insert(menu)>0?true:false;
    }

    @Override
    public boolean menusUpdate(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu)>0?true:false;
    }

    @Override
    public boolean menusDetele(Integer id) {
        return menuMapper.deleteByPrimaryKey(id)>0?true:false;
    }

    @Override
    public List<Menu> getMenusLikeName(String name) {
        MenuExample menuExample=new MenuExample();
        menuExample.createCriteria().andMnameLike("%"+name+"%");
        return menuMapper.selectByExample(menuExample);
    }
}
