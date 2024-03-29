package cn.blooming.jxgjsj.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class Image implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.id
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.path
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.hid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private Integer hid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.sid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private Integer sid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.did
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private Integer did;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column image.uid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    private Integer uid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.id
     *
     * @return the value of image.id
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.id
     *
     * @param id the value for image.id
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.path
     *
     * @return the value of image.path
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.path
     *
     * @param path the value for image.path
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.hid
     *
     * @return the value of image.hid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public Integer getHid() {
        return hid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.hid
     *
     * @param hid the value for image.hid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setHid(Integer hid) {
        this.hid = hid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.sid
     *
     * @return the value of image.sid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.sid
     *
     * @param sid the value for image.sid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.did
     *
     * @return the value of image.did
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public Integer getDid() {
        return did;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.did
     *
     * @param did the value for image.did
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column image.uid
     *
     * @return the value of image.uid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column image.uid
     *
     * @param uid the value for image.uid
     *
     * @mbggenerated Thu Aug 15 09:31:24 CST 2019
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }
}