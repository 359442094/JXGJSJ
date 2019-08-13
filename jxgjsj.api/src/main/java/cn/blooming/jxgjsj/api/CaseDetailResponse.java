package cn.blooming.jxgjsj.api;

import cn.blooming.jxgjsj.model.entity.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class CaseDetailResponse implements Serializable {

    private DecorationStypeDetails detail;

    private DecorationStype stype;

    private User user;

    private List<Image> images;

    private House house;

    private HouseType houseType;

    private Community community;

    private List<CaseDetailImage> imageLists;

}
