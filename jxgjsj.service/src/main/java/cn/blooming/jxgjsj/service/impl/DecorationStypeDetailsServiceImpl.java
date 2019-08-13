package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.DecorationStypeDetails;
import cn.blooming.jxgjsj.model.entity.DecorationStypeDetailsExample;
import cn.blooming.jxgjsj.model.mapper.DecorationStypeDetailsMapper;
import cn.blooming.jxgjsj.service.DecorationStypeDetailsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecorationStypeDetailsServiceImpl implements DecorationStypeDetailsService {

    @Autowired
    private DecorationStypeDetailsMapper decorationStypeDetailsMapper;

    @Override
    public DecorationStypeDetails getDecorationStypeDetailsById(Integer id) {
        return decorationStypeDetailsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DecorationStypeDetails> getDecorationStypeDetailsBySid(Integer sid) {
        DecorationStypeDetailsExample example=new DecorationStypeDetailsExample();
        example.createCriteria().andSidEqualTo(sid);
        return decorationStypeDetailsMapper.selectByExample(example);
    }

    @Override
    public Page<DecorationStypeDetails> getDecorationStypeDetailsPage(Integer pageIndex, Integer pageSize) {
        Page<DecorationStypeDetails> pages = PageHelper.startPage(pageIndex, pageSize);
        List<DecorationStypeDetails> details = decorationStypeDetailsMapper.selectByExample(new DecorationStypeDetailsExample());
        return  pages;
    }

    @Override
    public List<DecorationStypeDetails> getDecorationStypeDetails() {
        return decorationStypeDetailsMapper.selectByExample(new DecorationStypeDetailsExample());
    }
}
