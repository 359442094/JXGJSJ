package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.DecorationStype;

import java.util.List;

public interface DecorationStypeService {

    List<DecorationStype> getDecorationStypeServices();

    DecorationStype getDecorationStypeById(Integer id);

}
