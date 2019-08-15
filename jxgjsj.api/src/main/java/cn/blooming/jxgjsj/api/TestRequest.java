package cn.blooming.jxgjsj.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestRequest {

    @ApiModelProperty(
            value = "编号",
            notes = "编号",
            required = false
    )
    Integer id;

}
