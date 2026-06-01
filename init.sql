-- 创建数据库
DROP DATABASE IF EXISTS classroom_reservation;
CREATE DATABASE classroom_reservation DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE classroom_reservation;

-- 创建教室表
CREATE TABLE classroom (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    classroom_no VARCHAR(50) COMMENT '场地编号',
    classroom_name VARCHAR(100) COMMENT '场地名称',
    campus VARCHAR(50) COMMENT '校区',
    category VARCHAR(50) COMMENT '场地类别',
    seats INT COMMENT '座位数',
    exam_seats INT COMMENT '考试座位数',
    building_no VARCHAR(50) COMMENT '楼号',
    floor_no VARCHAR(20) COMMENT '楼层号',
    borrow_type VARCHAR(50) COMMENT '场地借用类型',
    remark TEXT COMMENT '场地备注信息',
    cdgly VARCHAR(50) COMMENT '场地管理员',
    department VARCHAR(100) COMMENT '使用部门',
    class_grade VARCHAR(100) COMMENT '使用班级',
    sub_category VARCHAR(50) COMMENT '场地二级类别',
    area DOUBLE COMMENT '建筑面积',
    managed_department VARCHAR(100) COMMENT '托管部门',
    available_weeks VARCHAR(200) COMMENT '可用周次（如：1-18）',
    available_days VARCHAR(50) COMMENT '可用星期（如：1,2,3,4,5）',
    available_periods VARCHAR(100) COMMENT '可用节次（如：1-2,3-4,5-6,7-8,9-10）',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教室信息表';

-- 创建预约记录表
CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    classroom_id BIGINT COMMENT '教室 ID',
    semester VARCHAR(50) COMMENT '学年学期',
    campus VARCHAR(50) COMMENT '校区',
    week INT COMMENT '周次',
    day_of_week INT COMMENT '星期',
    period INT COMMENT '节次',
    applicant_name VARCHAR(100) COMMENT '申请人姓名',
    applicant_phone VARCHAR(50) COMMENT '联系电话',
    purpose TEXT COMMENT '预约用途',
    status VARCHAR(20) DEFAULT 'approved' COMMENT '状态：approved 已批准',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (classroom_id) REFERENCES classroom(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约记录表';

-- 创建用户表
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_no VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 插入测试用户数据
INSERT INTO user (student_no, password) VALUES 
('242650086', '123456');
