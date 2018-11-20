package com.simon.basics.model;

/**
 * @author fengtianying
 * @date 2018/9/19 8:55
 */
public class EnumCode {

    /**
     * 用户类型
     */
    public enum UserType {

        TYPE_SUPER("-1"),// 超级管理员
        TYPE_MANAGER("0"),// 管理员
        TYPE_STUDENT("1"),// 学生
        TYPE_TEACHER("2");// 老师

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private UserType(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * 课程状态
     */
    public enum CourseStatus {
        //-1取消0新建未发布1已发布2进行中3结束
        COURSE_CANCEL("-1"),// 取消
        COURSE_INIT("0"),// 新建,未发布
        COURSE_ACTION("1"),// 开始
        COURSE_IN("2"),// 进行中
        COURSE_END("3");// 结束

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private CourseStatus(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
    /**
     * 上下课状态
     */
    public enum ClassStatus {
        //-1新建 0上课1下课
        CLASS_ENDING("-1"),
        CLASS_OVER("1"),
        CLASS_BEGINS("0");

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private ClassStatus(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
    /**
     * 课程订单状态
     */
    public enum OrderStatus {

        // 0未支付1成功2申请退款3退款
        ORDER_NOPAY("0"),
        ORDER_PAID("1"),
        ORDER_APPLY_REBACK("2"),
        ORDER_REBACK("3");

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private OrderStatus(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
    /**
     * 收费类型
     */
    public enum TeacherChargeType {

        // 老师收费类型(1按时2按提成)
        CHARGE_TYPE_TIME("1"),
        CHARGE_TYPE_PERCENTAGE("2");

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private TeacherChargeType(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
    /**
     * 签到类型
     */
    public enum AttendType {

        // 老师收费类型(1按时2按提成)
        ATTEND_TYPE_STUDENT("1"),
        ATTEND_TYPE_TEACHER("2"),
        ATTEND_TYPE_ADDITIONAL("3");

        private String value;

        /**
         * 构造函数
         * @param value
         */
        private AttendType(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
}
