-- 版本更新
CREATE TABLE `tb_system_version` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `platform` varchar(45) DEFAULT NULL COMMENT '平台 android或ios',

  `version_no` varchar(45) DEFAULT NULL COMMENT '版本号',
  `version_name` varchar(45) DEFAULT NULL COMMENT '版本名',
  `upd_title` varchar(128) DEFAULT NULL COMMENT '更新标题',
  `upd_content` varchar(255) DEFAULT NULL COMMENT '更新内容描述',

  `force_upgrade` int(1) DEFAULT NULL COMMENT '是否强制升级：0.否，1.是',
  `package_name` varchar(128) DEFAULT NULL COMMENT '安装包名',
  `package_path` varchar(255) DEFAULT NULL COMMENT '安装包路',
  `ios_upd_url` varchar(128) DEFAULT NULL COMMENT 'IOS下载链接',
  `identify` char(1) DEFAULT NULL COMMENT '客户端类型 1商户，2承兑商',
  `file_size` double DEFAULT NULL COMMENT '文件大小',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT  COMMENT='版本更新';

-- 公告
CREATE TABLE `tb_system_notice` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `title` varchar(45) DEFAULT NULL COMMENT '公告标题',,
  `url` varchar(255) DEFAULT NULL COMMENT '公告 链接',
  `content` varchar(255) DEFAULT NULL COMMENT '公告 内容',
  `identify` int(11) DEFAULT NULL COMMENT '公告 自身权限（0是商户，1是承兑商）',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT  COMMENT='公告';

-- 帮助中心
CREATE TABLE `tb_system_help` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `title` varchar(45) DEFAULT NULL COMMENT '帮助中心标题',,
  `content` varchar(255) DEFAULT NULL COMMENT '帮助中心',
  `identify` int(11) DEFAULT NULL COMMENT '帮助中心自身权限（0是商户，1是承兑商）',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT  COMMENT='帮助中心';


-- 邀请码表
CREATE TABLE `tb_system_invitation_code` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',

  `u_id` bigint(11) DEFAULT NULL COMMENT '生成人ID',
  `p_identify` int(11) DEFAULT NULL COMMENT '生成人 （0是商户，1是承兑商）',
  `code` varchar(45) DEFAULT NULL COMMENT '邀请码',
  `c_identify` int(11) DEFAULT NULL COMMENT '使用人 （0是商户，1是承兑商）',

  `status` smallint(1) DEFAULT '0' COMMENT '使用情况： 1 使用 0 未使用 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT  COMMENT='邀请码表';


-- 订单申诉
CREATE TABLE `tb_system_appeal` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `order_id` bigint(11) DEFAULT NULL COMMENT '订单编号',

  `appeal_content` varchar(255) DEFAULT NULL COMMENT '申诉内容',
  `appeal_img_url1` varchar(255) DEFAULT NULL COMMENT '申诉图片1',
  `appeal_img_url2` varchar(255) DEFAULT NULL COMMENT '申诉图片2',
  `appeal_img_url3` varchar(255) DEFAULT NULL COMMENT '申诉图片3',

  `type` smallint(1) NOT NULL COMMENT '申诉类型： 1 收购单 0 购买单 ',
  `status` smallint(1) DEFAULT '0' COMMENT '申诉 订单状态： 0 申诉中 1 协调中 2 结束 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT  COMMENT='订单申诉';


-- 管理系统 用户 - 权限 - 角色
