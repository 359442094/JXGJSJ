package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.Community;
import cn.blooming.jxgjsj.model.mapper.CommunityMapper;
import cn.blooming.jxgjsj.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public Community getCommunityById(Integer id) {
        return communityMapper.selectByPrimaryKey(id);
    }

}
