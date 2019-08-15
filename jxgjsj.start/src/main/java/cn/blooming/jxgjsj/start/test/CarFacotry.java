package cn.blooming.jxgjsj.start.test;

public class CarFacotry {
    public Car getCarByName(String type){
        if(type.equals("A")){
            return new CarA();
        }else {
            return new CarB();
        }
    }
}
