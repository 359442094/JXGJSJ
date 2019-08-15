package cn.blooming.jxgjsj.controller;

import cn.blooming.jxgjsj.api.TestRequest;
import cn.blooming.jxgjsj.api.TestResponse;
import cn.blooming.jxgjsj.model.annotation.ShowLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"测试信息中心"})
@RestController
public class TestController {

    @ShowLogger
    @ApiOperation(value = "测试信息",notes = "测试信息")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public TestResponse test(TestRequest request){
        return new TestResponse(request.getId().toString());
    }

}
