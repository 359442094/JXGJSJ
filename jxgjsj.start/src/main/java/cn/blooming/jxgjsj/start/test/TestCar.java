package cn.blooming.jxgjsj.start.test;


import org.apache.commons.lang.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCar {

    public static void main(String[] arges){
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 10);
        Date time = calendar.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(time);
        System.out.println(DateFormatUtils.format(time,"yyyy-MM-dd"));
        /*
        CarFacotry carFacotry=new CarFacotry();
        Car carByName = carFacotry.getCarByName("B");
        System.out.println(carByName.show());
        */
    }
}
