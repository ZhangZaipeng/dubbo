-- 商户表 Merchant
DROP TABLE IF EXISTS `tb_merchant`;
CREATE TABLE `tb_merchant` (
  `merchant_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商户 id 编号',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `icon_img_url` varchar(128) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `auth_num` smallint(1) DEFAULT '0' COMMENT '实名次数',
  `auth_level` smallint(1) DEFAULT '0' COMMENT '0 未认证 1 初级认证 2高级认证',

  `security_pwd` varchar(100) DEFAULT NULL COMMENT '资金安全密码',
  '默认给承兑商的提成点',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

  PRIMARY KEY (`merchant_id`),
  UNIQUE KEY `uniq_mobile` (`mobile`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=98000039 DEFAULT CHARSET=utf8 COMMENT='商户表';

-- 商户登录表
DROP TABLE IF EXISTS `tb_merchant_agent`;
CREATE TABLE `tb_merchant_agent` (
  `merchant_agent_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户 登录编号',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户 编号',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',

  `login_pwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `deleted` smallint(1) DEFAULT '0' COMMENT '账号状态： 1 有效 0 无效 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`merchant_agent_id`) USING BTREE,
  UNIQUE KEY `uniq_merchant_id` (`merchant_id`),
  UNIQUE KEY `uniq_telephone` (`telephone`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商户登录表';

-- 商户接口密钥 （一个商户可以申请多个 APID）
