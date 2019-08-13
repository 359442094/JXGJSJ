package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.HouseType;
import cn.blooming.jxgjsj.model.entity.HouseTypeExample;
import cn.blooming.jxgjsj.model.mapper.HouseTypeMapper;
import cn.blooming.jxgjsj.service.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseTypeServiceImpl implements HouseTypeService {

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    @Override
    public HouseType getHouseTypeById(Integer id) {
        return houseTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<HouseType> getHouseTypeAll() {
        return houseTypeMapper.selectByExample(new HouseTypeExample());
    }

}
