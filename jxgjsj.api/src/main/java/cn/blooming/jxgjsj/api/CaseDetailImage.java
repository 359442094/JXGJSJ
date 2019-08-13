package cn.blooming.jxgjsj.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 案例详情图片列表
 * */
@ToString
@Getter
@Setter
public class CaseDetailImage implements Serializable {

    private Integer did;

    private String title;

    private String path;

}
