package image.service;

import image.service.model.PictureModel;
import image.util.FinallyException;

public interface ImageService {

    int saveImageInfo(PictureModel pictureModel) throws FinallyException;

    PictureModel checkImageSaved(String md5);

}
