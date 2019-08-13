package cn.blooming.jxgjsj.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class AboutUsAdminResponse implements Serializable {

    private String logo;

    private String text;

    private String addr;

}
