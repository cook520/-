# 教室预约系统

## 技术栈
- **后端**: Spring Boot 3.2.3 + JPA + MySQL
- **前端**: Vue 3 + Element Plus + Axios + Vite
- **数据库**: MySQL (root / 520521)

## 项目结构
```
热海1/
├── src/
│   └── main/
│       ├── java/com/example/demo/
│       │   ├── ClassroomReservationApplication.java  # 启动类
│       │   ├── entity/
│       │   │   ── Classroom.java                     # 教室实体类
│       │   ├── repository/
│       │   │   └── ClassroomRepository.java           # 数据访问层
│       │   ├── service/
│       │   │   └── ClassroomService.java              # 业务逻辑层
│       │   └── controller/
│       │       └── ClassroomController.java           # 控制器层
│       └── resources/
│           ── application.properties                 # 配置文件
├── frontend/
│   ├── src/
│   │   ├── App.vue                                   # 主页面
│   │   └── main.js                                   # 入口文件
│   ├── index.html                                    # HTML模板
│   ├── package.json                                  # 依赖配置
│   └── vite.config.js                               # Vite配置
├── init.sql                                          # 数据库初始化脚本
└── pom.xml                                           # Maven配置

```

## 快速开始

### 1. 初始化数据库
```bash
# 在MySQL中执行 init.sql 脚本
mysql -u root -p520521 < init.sql
```

# 停止当前服务（Ctrl+C）
mvn clean compile
### 2. 启动后端服务
```bash
# 编译项目
mvn clean install

# 运行Spring Boot应用
mvn spring-boot:run
```
后端服务将在 http://localhost:8080 启动

### 3. 启动前端服务
```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```
前端服务将在 http://localhost:3000 启动

## 功能说明
- ✅ 教室信息查询（按校区、楼号、类别、座位数等多条件筛选）
- ✅ 周次、星期、节次选择
- ✅ 分页显示
- ✅ 响应式布局，按照提供的UI设计

## 数据库配置
- 地址: localhost:3306
- 数据库名: classroom_reservation
- 用户名: root
- 密码: 520521

## API接口
- GET /api/classrooms/search - 查询教室列表
  - 参数: semester, campus, buildingNo, category, subCategory, minSeats, maxSeats, classroomName, page, size


## 结束服务器
Stop-Process -Id 12345 -Force

## 检查是否占用
netstat -ano | findstr ":8080 :3000"