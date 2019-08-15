package cn.blooming.jxgjsj.start.aop;

import cn.blooming.jxgjsj.api.TestRequest;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Log4j
@Component
@Aspect
public class ArgsAop {

    @Pointcut(value = "@annotation(cn.blooming.jxgjsj.model.annotation.ShowLogger)")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof TestRequest){
                TestRequest request = (TestRequest)arg;
                if(StringUtils.isEmpty(request.getId())){
                    request.setId(1234);
                }
            }
        }

    }

}
