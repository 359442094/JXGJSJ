package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.PublicData;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import cn.blooming.jxgjsj.service.ConfigService;
import cn.blooming.jxgjsj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 热装楼盘详情
 * */
@Controller
public class SaleDetailsController {

    @Autowired
    private PublicData publicData;

    @ShowLogger(info = "热装楼盘详情页面")
    @RequestMapping(path = "/toSale_XX",method = RequestMethod.GET)
    public String toSale_XX(Model model){
        publicData.getPublicData(model);
        return "sale_xx";
    }

}
