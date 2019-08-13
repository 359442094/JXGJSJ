package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.IndexData;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 主页
 * */
@Controller
public class IndexController {

    @Autowired
    private IndexData indexData;

    @ShowLogger(info = "主页")
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String toIndex(Model model){
        indexData.getIndexData(model);
        return "index";
    }

}
