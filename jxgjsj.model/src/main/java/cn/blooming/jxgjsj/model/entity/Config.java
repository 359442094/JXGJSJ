package cn.blooming.jxgjsj.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class Config implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.cid
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    private Integer cid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.ctype
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    private String ctype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.cvalue
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    private String cvalue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.cid
     *
     * @return the value of config.cid
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.cid
     *
     * @param cid the value for config.cid
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.ctype
     *
     * @return the value of config.ctype
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public String getCtype() {
        return ctype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.ctype
     *
     * @param ctype the value for config.ctype
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.cvalue
     *
     * @return the value of config.cvalue
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public String getCvalue() {
        return cvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.cvalue
     *
     * @param cvalue the value for config.cvalue
     *
     * @mbggenerated Thu Jul 11 16:17:06 CST 2019
     */
    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }
}