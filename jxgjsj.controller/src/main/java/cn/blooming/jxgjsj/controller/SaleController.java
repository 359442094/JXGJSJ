package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.PublicData;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 热装楼盘
 * */
@Api(tags = {"热装楼盘"})
@Controller
public class SaleController {

    @Autowired
    private PublicData publicData;

    @ShowLogger(info = "热装楼盘页面")
    @RequestMapping(path = "/toSale",method = RequestMethod.GET)
    public String toSale(Model model){
        publicData.getPublicData(model);
        return "sale";
    }

}
