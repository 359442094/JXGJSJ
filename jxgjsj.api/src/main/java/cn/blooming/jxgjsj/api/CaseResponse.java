package cn.blooming.jxgjsj.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class CaseResponse implements Serializable {

    private List<CaseImage> caseImageList;

}
