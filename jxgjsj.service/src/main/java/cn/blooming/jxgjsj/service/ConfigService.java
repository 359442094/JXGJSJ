package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.Config;

import java.util.List;

public interface ConfigService {

    List<Config> getConfigs(String type);

    boolean configInsert(Config config);

    boolean configUpdate(Config config);

    boolean configDelete(Integer id);

    Config getConfigById(Integer id);

}
