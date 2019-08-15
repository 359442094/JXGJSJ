package cn.blooming.jxgjsj.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {

    @ApiModelProperty(
            value = "返回信息",
            notes = "返回信息",
            required = true
    )
    String message;

}
