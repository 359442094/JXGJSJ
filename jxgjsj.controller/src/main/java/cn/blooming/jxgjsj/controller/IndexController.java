package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.IndexData;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 主页
 * */
@Log4j
@Controller
@Api(tags = {"主页配置"})
public class IndexController {

    @Autowired
    private IndexData indexData;

    @ShowLogger(info = "跳转至主页")
    @ApiOperation(value = "跳转至主页",notes = "跳转至主页")
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String index(Model model){
        indexData.getIndexData(model);
        return "index";
    }

}
