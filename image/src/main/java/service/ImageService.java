package image.service;

import image.service.model.PictureModel;
import image.exception.FinallyException;

public interface ImageService {

    PictureModel saveImageInfo(PictureModel pictureModel) throws FinallyException;

    PictureModel checkImageSaved(String md5);

}
