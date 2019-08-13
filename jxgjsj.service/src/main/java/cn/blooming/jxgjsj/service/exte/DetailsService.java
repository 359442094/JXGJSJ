package cn.blooming.jxgjsj.service.exte;

import cn.blooming.jxgjsj.model.entity.DecorationStypeDetails;
import com.github.pagehelper.Page;

public interface DetailsService {

    Page<DecorationStypeDetails> details(Integer index,Integer size,String style,String type,Integer startSize,Integer endSize);

}
