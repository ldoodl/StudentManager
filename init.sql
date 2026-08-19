-- 创建 student 表
CREATE TABLE IF NOT EXISTS `student` (
                                         `id` VARCHAR(20) PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    `age` INT,
    `score` DOUBLE,
    `created_by` VARCHAR(50) DEFAULT 'admin'
    );

-- 创建 user 表
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `username` varchar(50) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `role` varchar(20) DEFAULT 'USER',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
    );

-- 插入默认管理员（密码: 123456，BCrypt 加密）
INSERT INTO `user` (username, password, role)
SELECT * FROM (SELECT 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM0lTZ8Wp5xYgZq9zY6y', 'ADMIN') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE username = 'admin');