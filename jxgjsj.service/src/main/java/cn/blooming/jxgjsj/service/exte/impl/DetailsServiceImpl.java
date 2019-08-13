package cn.blooming.jxgjsj.service.exte.impl;

import cn.blooming.jxgjsj.model.entity.DecorationStypeDetails;
import cn.blooming.jxgjsj.model.mapper.exte.DetailsMapper;
import cn.blooming.jxgjsj.service.exte.DetailsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public Page<DecorationStypeDetails> details(Integer index,Integer size,String style,String type,Integer startSize,Integer endSize) {
        Page<DecorationStypeDetails> pages = PageHelper.startPage(index, size);
        detailsMapper.caseDetailAll(style,type,startSize,endSize);
        return pages;
    }

}
