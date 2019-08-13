package cn.blooming.jxgjsj.service.impl;

import cn.blooming.jxgjsj.model.entity.Config;
import cn.blooming.jxgjsj.model.entity.ConfigExample;
import cn.blooming.jxgjsj.model.mapper.ConfigMapper;
import cn.blooming.jxgjsj.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<Config> getConfigs(String type) {
        ConfigExample configExample = new ConfigExample();
        configExample.createCriteria().andCtypeEqualTo(type);
        return configMapper.selectByExample(configExample);
    }

    @Override
    public boolean configInsert(Config config) {
        return configMapper.insert(config)>0?true:false;
    }

    @Override
    public boolean configUpdate(Config config) {
        return configMapper.updateByPrimaryKey(config)>0?true:false;
    }

    @Override
    public boolean configDelete(Integer id) {
        return configMapper.deleteByPrimaryKey(id)>0?true:false;
    }

    @Override
    public Config getConfigById(Integer id) {
        return configMapper.selectByPrimaryKey(id);
    }
}
