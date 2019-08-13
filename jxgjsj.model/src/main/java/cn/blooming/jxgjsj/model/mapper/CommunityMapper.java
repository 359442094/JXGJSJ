package cn.blooming.jxgjsj.model.mapper;

import cn.blooming.jxgjsj.model.entity.Community;
import cn.blooming.jxgjsj.model.entity.CommunityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommunityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int countByExample(CommunityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int deleteByExample(CommunityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int insert(Community record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int insertSelective(Community record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    List<Community> selectByExample(CommunityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    Community selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int updateByExampleSelective(@Param("record") Community record, @Param("example") CommunityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int updateByExample(@Param("record") Community record, @Param("example") CommunityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int updateByPrimaryKeySelective(Community record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community
     *
     * @mbggenerated Fri Jul 12 16:33:09 CST 2019
     */
    int updateByPrimaryKey(Community record);
}