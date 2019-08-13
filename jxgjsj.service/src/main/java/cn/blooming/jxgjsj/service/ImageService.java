package cn.blooming.jxgjsj.service;

import cn.blooming.jxgjsj.model.entity.Image;

import java.util.List;

public interface ImageService {

    List<Image> getImageBySid(Integer sid);

    List<Image> getImageByDid(Integer did);

}
