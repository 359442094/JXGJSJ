package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.House;
import cn.blooming.jxgjsj.model.mapper.HouseMapper;
import cn.blooming.jxgjsj.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public House getHouseById(Integer id) {
        return houseMapper.selectByPrimaryKey(id);
    }

}
