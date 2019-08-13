package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.Image;
import cn.blooming.jxgjsj.model.entity.ImageExample;
import cn.blooming.jxgjsj.model.mapper.ImageMapper;
import cn.blooming.jxgjsj.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<Image> getImageBySid(Integer sid) {
        ImageExample example=new ImageExample();
        example.createCriteria().andHidIsNull().andSidEqualTo(sid);
        return imageMapper.selectByExample(example);
    }

    @Override
    public List<Image> getImageByDid(Integer did) {
        ImageExample example=new ImageExample();
        example.createCriteria().andHidIsNull().andDidEqualTo(did);
        return imageMapper.selectByExample(example);
    }
}
