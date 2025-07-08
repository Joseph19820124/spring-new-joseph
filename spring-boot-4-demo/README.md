# Spring Boot 4 Demo

一个展示Spring Boot 4新特性的精简演示项目。

## 主要特性

1. **虚拟线程支持** - 使用Project Loom的虚拟线程提高并发性能
2. **响应式编程** - 集成Spring WebFlux实现响应式端点
3. **REST Client** - 使用新的声明式HTTP客户端
4. **异步编程** - 展示CompletableFuture的使用
5. **容器化** - 完整的Docker支持

## 技术栈

- Java 21
- Spring Boot 3.3.5 (最新稳定版)
- Spring WebFlux
- Spring Data JPA
- H2 Database
- Docker

## 快速开始

### 使用Docker运行

```bash
# 构建并运行
docker-compose up --build

# 后台运行
docker-compose up -d
```

### 本地运行

```bash
# 确保已安装Java 21
mvn spring-boot:run

# 或者使用不同端口（如果8080被占用）
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8088
```

## API端点

### 用户管理API
- `GET /api/users` - 获取所有用户
- `GET /api/users/{id}` - 根据ID获取用户
- `POST /api/users` - 创建新用户
- `DELETE /api/users/{id}` - 删除用户
- `GET /api/users/async/{id}` - 异步获取用户
- `GET /api/users/stream` - 流式传输用户数据
- `GET /api/users/reactive/{id}` - 响应式获取用户

### 响应式API
- `GET /api/reactive/mono` - 返回Mono示例
- `GET /api/reactive/flux` - 返回Flux流（SSE）
- `GET /api/reactive/github/{username}` - 获取GitHub用户信息
- `GET /api/reactive/combined` - 组合多个响应式流
- `POST /api/reactive/process` - 处理响应式数据

### 监控端点
- `GET /actuator/health` - 健康检查
- `GET /actuator/metrics` - 应用指标
- `GET /actuator/env` - 环境信息

## 测试API

```bash
# 创建用户
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"test_user","email":"test@example.com"}'

# 获取所有用户
curl http://localhost:8080/api/users

# 测试响应式流
curl http://localhost:8080/api/reactive/flux

# 健康检查
curl http://localhost:8080/actuator/health
```

## H2控制台

访问 http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- 用户名: `sa`
- 密码: (留空)

## 虚拟线程验证

应用配置了虚拟线程，所有的请求处理和异步任务都会使用虚拟线程。查看日志可以看到线程信息：
```
VirtualThread[#xx]/runnable@ForkJoinPool-x-worker-x
```

## 项目结构

```
spring-boot-4-demo/
├── src/main/java/com/example/demo/
│   ├── controller/     # REST控制器
│   ├── service/        # 业务逻辑
│   ├── model/          # 实体类
│   ├── repository/     # 数据访问层
│   └── config/         # 配置类
├── src/main/resources/
│   └── application.yml # 应用配置
├── Dockerfile          # Docker镜像配置
├── docker-compose.yml  # Docker Compose配置
└── pom.xml            # Maven配置
```