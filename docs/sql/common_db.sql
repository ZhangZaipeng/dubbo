-- 短息记录
CREATE TABLE `tb_common_sms` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `identify_code` varchar(10) DEFAULT NULL COMMENT '验证码',
  `code_type` int(11) DEFAULT NULL COMMENT '验证码类型 （1:登陆、注册、2：修改密码,3..... ）',
  `status` int(11) DEFAULT NULL COMMENT '状态(1:未使用、2:已使用、3::废弃)',
  `send_type` int(11) DEFAULT NULL COMMENT '发送类别(1：承兑商、2：商户、3.后台)',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='短息记录';


-- push（轮询 redis）

-- 文件上传
CREATE TABLE `tb_common_upload_file` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `full_relative` varchar(100) DEFAULT NULL COMMENT '网络访问路径',

  `file_path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `file_name` varchar(100) DEFAULT NULL COMMENT '原始文件名',
  `file_size` varchar(20) DEFAULT NULL COMMENT '文件大小 单位 10M',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='文件上传';
