package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.DecorationStypeDetails;
import com.github.pagehelper.Page;

import java.util.List;

public interface DecorationStypeDetailsService {

    DecorationStypeDetails getDecorationStypeDetailsById(Integer id);

    List<DecorationStypeDetails> getDecorationStypeDetailsBySid(Integer sid);

    Page<DecorationStypeDetails> getDecorationStypeDetailsPage(Integer pageIndex, Integer pageSize);

    List<DecorationStypeDetails> getDecorationStypeDetails();

}
