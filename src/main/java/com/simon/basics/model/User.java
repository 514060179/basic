package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class User extends Account {
    @ApiModelProperty("用户信息id")
    private Long userId;
    @ApiModelProperty("用户名字")
    private String name;
    @ApiModelProperty("学校")
    private String schoolName;
    @ApiModelProperty("年级")
    private String gradeName;
    @ApiModelProperty("学生家长名字")
    private String parentName;
    @ApiModelProperty("用户性别")
    private String sex;
    @ApiModelProperty("用户年龄")
    private Integer age;
    @ApiModelProperty("用户手机号码")
    private String phone;
    @ApiModelProperty("用户身份证号")
    private String cardNum;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty(value = "教师收费类型:1按时2按提成",allowableValues = "range[1,2]")
    private String teacherChargeType;
    @ApiModelProperty(value = "收费类型为1时（必填）：按【averageHour】小时收费")
    private BigDecimal averageHour;
    @ApiModelProperty(value = "提成点")
    private BigDecimal percentage;
    @ApiModelProperty(value = "收费类型为1时：超过【exceedNum】人数提成【percentage】;收费类型为2时：每节课提成点")
    private Integer exceedNum;
    @ApiModelProperty(value = "收费类型为1时（必填）按【averageHour】小时收费【averageHourCost】")
    private BigDecimal averageHourCost;
    @ApiModelProperty(value = "备注")
    private String remark;
    @JsonIgnore
    @ApiModelProperty(value = "角色集合",hidden = true)
    private Set<String> roleSet;
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTeacherChargeType() {
        return teacherChargeType;
    }

    public void setTeacherChargeType(String teacherChargeType) {
        this.teacherChargeType = teacherChargeType;
    }

    public BigDecimal getAverageHour() {
        return averageHour;
    }

    public void setAverageHour(BigDecimal averageHour) {
        this.averageHour = averageHour;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getExceedNum() {
        return exceedNum;
    }

    public void setExceedNum(Integer exceedNum) {
        this.exceedNum = exceedNum;
    }

    public BigDecimal getAverageHourCost() {
        return averageHourCost;
    }

    public void setAverageHourCost(BigDecimal averageHourCost) {
        this.averageHourCost = averageHourCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }
}