package cn.blooming.jxgjsj.api;

import cn.blooming.jxgjsj.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class CaseImage implements Serializable {

    private Integer did;

    private String title;

    private String path;

    private User user;

}
