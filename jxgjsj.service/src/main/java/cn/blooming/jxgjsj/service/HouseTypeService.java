package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.HouseType;

import java.util.List;

public interface HouseTypeService {

    HouseType getHouseTypeById(Integer id);

    List<HouseType> getHouseTypeAll();

}
