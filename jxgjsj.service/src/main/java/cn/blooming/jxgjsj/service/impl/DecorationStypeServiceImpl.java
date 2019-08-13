package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.DecorationStype;
import cn.blooming.jxgjsj.model.entity.DecorationStypeExample;
import cn.blooming.jxgjsj.model.mapper.DecorationStypeMapper;
import cn.blooming.jxgjsj.service.DecorationStypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DecorationStypeServiceImpl implements DecorationStypeService {

    @Autowired
    private DecorationStypeMapper decorationStypeMapper;

    @Override
    public List<DecorationStype> getDecorationStypeServices() {
        DecorationStypeExample stypeExample = new DecorationStypeExample();
        List<DecorationStype> stypes = decorationStypeMapper.selectByExample(stypeExample);
        return stypes;
    }

    @Override
    public DecorationStype getDecorationStypeById(Integer id) {
        return decorationStypeMapper.selectByPrimaryKey(id);
    }

}
