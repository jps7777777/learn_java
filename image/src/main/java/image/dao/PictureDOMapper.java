package image.dao;

import image.bean.PictureDO;
import org.springframework.stereotype.Service;

@Service
public interface PictureDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    int insert(PictureDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    int insertSelective(PictureDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    PictureDO selectByPrimaryKey(Integer id);

    /**
     * 根据md5值查询图片信息
     * @param md5 md5值
     * @return 返回结果
     */
    PictureDO selectByMD5(String md5);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    int updateByPrimaryKeySelective(PictureDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated Fri Jul 05 14:21:25 CST 2019
     */
    int updateByPrimaryKey(PictureDO record);
}