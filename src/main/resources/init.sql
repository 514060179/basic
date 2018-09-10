/*==============================================================*/
/* Table: account                                               */
/*==============================================================*/
CREATE TABLE account
(
   account_id           BIGINT NOT NULL AUTO_INCREMENT,
   username             VARCHAR(50) NOT NULL COMMENT '用户名',
   password             VARCHAR(100) BINARY NOT NULL COMMENT '密码',
   type                 CHAR(2) NOT NULL DEFAULT '0' COMMENT '类型（-1超级管理员0管理员1学生2教师）',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (account_id)
);

ALTER TABLE account COMMENT '登录账户（类型有学生、教师、管理员、超级管理员）';

/*==============================================================*/
/* Table: class_course                                          */
/*==============================================================*/
CREATE TABLE class_course
(
   course_id            BIGINT NOT NULL AUTO_INCREMENT,
   seat_id              BIGINT NOT NULL COMMENT '座位结构id',
   user_id              BIGINT COMMENT '主键',
   course_name          VARCHAR(50) NOT NULL COMMENT '课程名字',
   course_cost          DOUBLE(10,2) NOT NULL COMMENT '课程费用',
   course_start_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '课程开始时间',
   course_end_time      DATETIME NOT NULL COMMENT '结束时间',
   course_status        CHAR(2) NOT NULL COMMENT '状态（-1取消0新建未发布1已发布2进行中3结束）',
   course_abstract      TEXT COMMENT '课程介绍',
   course_remark        TEXT COMMENT '备注',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (course_id)
);

/*==============================================================*/
/* Table: course_order                                          */
/*==============================================================*/
CREATE TABLE course_order
(
   order_id             BIGINT NOT NULL AUTO_INCREMENT,
   order_no             VARCHAR(200) COMMENT '订单编号',
   course_id            BIGINT NOT NULL COMMENT '课程id',
   user_id              BIGINT COMMENT '主键',
   order_status         CHAR(2) NOT NULL DEFAULT '0' COMMENT '状态(0未支付1成功)',
   order_cost           DECIMAL(12,2) NOT NULL COMMENT '支付金额',
   order_pay_status     CHAR(2) COMMENT '支付状态',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (order_id),
   UNIQUE KEY AK_order_no_unique (order_no)
);

/*==============================================================*/
/* Table: course_roster                                         */
/*==============================================================*/
CREATE TABLE course_roster
(
   roster_id            BIGINT NOT NULL AUTO_INCREMENT,
   course_id            BIGINT NOT NULL COMMENT '课程id',
   seat_id              BIGINT NOT NULL COMMENT '座位结构id',
   user_id              BIGINT COMMENT '主键',
   roster_name          VARCHAR(100) COMMENT '名单名称',
   roster_course_count_rest INT NOT NULL COMMENT '剩余课程总数',
   roster_seat_x        INT NOT NULL COMMENT '座位坐标x',
   roster_seat_y        INT NOT NULL COMMENT '座位坐标y',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (roster_id)
);

ALTER TABLE course_roster COMMENT '记录课程的名单以及学生课时';

/*==============================================================*/
/* Index: roster_keys                                           */
/*==============================================================*/
CREATE UNIQUE INDEX roster_keys ON course_roster
(
   course_id,
   seat_id
);

/*==============================================================*/
/* Table: jurisdiction                                          */
/*==============================================================*/
CREATE TABLE jurisdiction
(
   jn_id                BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   jn_name              VARCHAR(50) NOT NULL COMMENT '权限名称',
   jn_url               VARCHAR(500) NOT NULL COMMENT '具体操作权限',
   jn_pid               bigint not null default 0 comment '父级id',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (jn_id)
);

ALTER TABLE jurisdiction COMMENT '角色权限表';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
CREATE TABLE role
(
   role_id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   role_name            VARCHAR(50) COMMENT '角色名称',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (role_id)
);

ALTER TABLE role COMMENT '用户角色表';

/*==============================================================*/
/* Table: role_jn                                               */
/*==============================================================*/
CREATE TABLE role_jn
(
   role_jn_id           BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   role_id              BIGINT NOT NULL  COMMENT '角色id',
   jn_id                BIGINT NOT NULL COMMENT '权限id',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (role_jn_id)
);

ALTER TABLE role_jn COMMENT '角色权限关系';

/*==============================================================*/
/* Table: roster_attendance                                     */
/*==============================================================*/
CREATE TABLE roster_attendance
(
   attendance_id        INT NOT NULL AUTO_INCREMENT COMMENT '主键',
   course_id            BIGINT NOT NULL COMMENT '课程id',
   user_id              BIGINT COMMENT '主键',
   attend_name          VARCHAR(100) NOT NULL COMMENT '考勤名称',
   attend_section_num   INT NOT NULL DEFAULT 1 COMMENT '第几课时、课程章节数',
   attend_remark        VARCHAR(200) COMMENT '备注',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (attendance_id)
);

ALTER TABLE roster_attendance COMMENT '课程名单考勤记录';

/*==============================================================*/
/* Index: attendance_keys                                       */
/*==============================================================*/
CREATE UNIQUE INDEX attendance_keys ON roster_attendance
(
   course_id,
   attend_section_num
);

/*==============================================================*/
/* Table: seat_layout                                           */
/*==============================================================*/
CREATE TABLE seat_layout
(
   seat_id              BIGINT NOT NULL AUTO_INCREMENT,
   seat_left            INT(12) NOT NULL COMMENT '左边区域数量',
   seat_mid             INT(12) NOT NULL COMMENT '中间区域数量',
   seat_right           INT(12) NOT NULL COMMENT '右边区域数量',
   seat_rows            INT(12) NOT NULL COMMENT '总行数',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (seat_id)
);

ALTER TABLE seat_layout COMMENT '座位结构、布局';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
CREATE TABLE `user`
(
   user_id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   account_id           BIGINT NOT NULL COMMENT '登录账户id',
   name                 VARCHAR(50) NOT NULL COMMENT '姓名',
   sex                  CHAR(2) NOT NULL DEFAULT '0' COMMENT '性别（0男1女）',
   age                  INT NOT NULL COMMENT '年龄',
   phone                VARCHAR(11) NOT NULL COMMENT '手机号',
   card_num             VARCHAR(20) NOT NULL COMMENT '身份证',
   address              VARCHAR(500) COMMENT '住址',
   type                 CHAR(2) NOT NULL DEFAULT '1' COMMENT '类型(1学生2老师)',
   remark               VARCHAR(1000) COMMENT '备注',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME COMMENT '修改时间',
   PRIMARY KEY (user_id),
   UNIQUE KEY AK_account_id_unique (account_id)
);

ALTER TABLE USER COMMENT '用户类型有教师、学生';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
CREATE TABLE user_role
(
   user_role_id         BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   role_id              BIGINT NOT NULL COMMENT '角色id',
   user_id              BIGINT COMMENT '主键',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (user_role_id)
);

ALTER TABLE user_role COMMENT '用户角色表';

ALTER TABLE class_course ADD CONSTRAINT FK_Reference_2 FOREIGN KEY (user_id)
      REFERENCES USER (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE class_course ADD CONSTRAINT FK_Reference_3 FOREIGN KEY (seat_id)
      REFERENCES seat_layout (seat_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE course_order ADD CONSTRAINT FK_Reference_10 FOREIGN KEY (user_id)
      REFERENCES USER (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE course_order ADD CONSTRAINT FK_Reference_4 FOREIGN KEY (course_id)
      REFERENCES class_course (course_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE course_roster ADD CONSTRAINT FK_Reference_5 FOREIGN KEY (course_id)
      REFERENCES class_course (course_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE course_roster ADD CONSTRAINT FK_Reference_6 FOREIGN KEY (user_id)
      REFERENCES USER (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE course_roster ADD CONSTRAINT FK_Reference_7 FOREIGN KEY (seat_id)
      REFERENCES seat_layout (seat_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE role_jn ADD CONSTRAINT FK_Reference_13 FOREIGN KEY (jn_id)
      REFERENCES jurisdiction (jn_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE role_jn ADD CONSTRAINT FK_Reference_14 FOREIGN KEY (role_id)
      REFERENCES role (role_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE roster_attendance ADD CONSTRAINT FK_Reference_8 FOREIGN KEY (course_id)
      REFERENCES class_course (course_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE roster_attendance ADD CONSTRAINT FK_Reference_9 FOREIGN KEY (user_id)
      REFERENCES USER (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USER ADD CONSTRAINT FK_Reference_1 FOREIGN KEY (account_id)
      REFERENCES account (account_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_role ADD CONSTRAINT FK_Reference_11 FOREIGN KEY (role_id)
      REFERENCES role (role_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_role ADD CONSTRAINT FK_Reference_12 FOREIGN KEY (user_id)
      REFERENCES USER (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

