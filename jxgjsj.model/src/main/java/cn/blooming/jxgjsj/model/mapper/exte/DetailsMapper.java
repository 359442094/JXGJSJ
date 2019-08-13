package cn.blooming.jxgjsj.model.mapper.exte;

import cn.blooming.jxgjsj.model.entity.DecorationStypeDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DetailsMapper {

    List<DecorationStypeDetails> caseDetailAll(@Param("style") String style,
                                               @Param("type") String type,
                                               @Param("startSize") Integer startSize,
                                               @Param("endSize") Integer endSize);

}
