package image.service.imple;

import image.bean.PictureDO;
import image.dao.PictureDOMapper;
import image.service.ImageService;
import image.service.model.PictureModel;
import image.exception.EnumException;
import image.exception.FinallyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private PictureDOMapper pictureDOMapper;

    @Override
    public PictureModel saveImageInfo(PictureModel pictureModel) throws FinallyException {
        if(pictureModel == null){
            throw new FinallyException(EnumException.DATA_ERROR.setErrMsg("图片信息不存在"));
        }
        PictureDO pictureDO = convertByModel(pictureModel);
        int pid = pictureDOMapper.insertSelective(pictureDO);
        if(pid == 0){
            throw new FinallyException(EnumException.DATABASE_ERROR.setErrMsg("图片保存失败"));
        }
        return convertByDO(pictureDO);
    }

    @Override
    public PictureModel checkImageSaved(String md5) {
        if(md5 == null || md5.equals("")){
            return null;
        }
        PictureDO pictureDO = pictureDOMapper.selectByMD5(md5);
        if(pictureDO == null){
            return null;
        }
        return convertByDO(pictureDO);
    }

    private PictureDO convertByModel(PictureModel pictureModel){
        if(pictureModel == null){
            return null;
        }
        PictureDO pictureDO = new PictureDO();
        BeanUtils.copyProperties(pictureModel,pictureDO);
        return pictureDO;
    }

    private PictureModel convertByDO(PictureDO pictureDO){
        if(pictureDO == null){
            return null;
        }
        PictureModel pictureModel = new PictureModel();
        BeanUtils.copyProperties(pictureDO,pictureModel);
        return pictureModel;
    }
}
