-- 商户表 Merchant
DROP TABLE IF EXISTS `tb_merchant`;
CREATE TABLE `tb_merchant` (
  `merchant_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商户 id 编号',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `icon_img_url` varchar(128) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',

  `google_auth` varchar(20) DEFAULT NULL COMMENT '谷歌验证码',
  `google_auth_url` varchar(255) DEFAULT NULL COMMENT '谷歌验证码链接',
  `google_auth_lock` smallint(1) DEFAULT '0' COMMENT '是否关闭谷歌认证  默认 0关闭状态  1 开启',

  `auth_num` smallint(1) DEFAULT '0' COMMENT '实名次数',
  `auth_level` smallint(1) DEFAULT '0' COMMENT '0 未认证 1 初级认证 2高级认证',
  `security_pwd` varchar(100) DEFAULT NULL COMMENT '资金安全密码',

  `default_udw_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '默认给承兑商的提成点',
  `mobile_notification` smallint(1) DEFAULT '1' '手机通知 是否开启 0否 1是',
  `balance_reminder` DECIMAL(10,5) DEFAULT '500' COMMENT '余额提醒',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`merchant_id`),
  UNIQUE KEY `uniq_mobile` (`mobile`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=98000039 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='商户表';

-- 商户登录表
DROP TABLE IF EXISTS `tb_merchant_agent`;
CREATE TABLE `tb_merchant_agent` (
  `merchant_agent_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商户 登录编号',
  `merchant_id` bigint(11) NOT NULL COMMENT '商户 编号',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',

  `login_pwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `deleted` smallint(1) DEFAULT '0' COMMENT '账号状态： 1 有效 0 无效 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`merchant_agent_id`),
  UNIQUE KEY `uniq_merchant_id` (`merchant_id`),
  UNIQUE KEY `uniq_telephone` (`telephone`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='商户登录表';

-- 商户接口密钥 （一个商户可以申请多个 APPID）
DROP TABLE IF EXISTS `tb_merchant_app_secret`;
CREATE TABLE `tb_merchant_app_secret` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '密钥 编号',
  `merchant_id` bigint(11) NOT NULL COMMENT '商户 编号',

  `app_id` varchar(50) NOT NULL COMMENT 'APP ID',
  `app_name` varchar(20) DEFAULT NULL COMMENT '应用名称',
  `app_img_url` varchar(128) DEFAULT NULL COMMENT '应用 图像 URL',
  `app_secret` varchar(128) DEFAULT NULL COMMENT '应用 密钥',
  `app_callback_url` varchar(128) DEFAULT NULL COMMENT '应用 回调地址 : http://www.baidu.com:8181',

  `deleted` smallint(1) DEFAULT '0' COMMENT '账号状态： 1 有效 0 无效 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `uniq_app_id` (`app_id`),
  INDEX `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='商户接口密钥表';


-- 商户 资产表
CREATE TABLE `tb_merchant_asset` (
  `asset_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `merchant_id` bigint(11) NOT NULL COMMENT '承兑商 标识',

  `crypto_address` varchar(100) DEFAULT NULL COMMENT '资产充值地址',
  `currency` varchar(20) NOT NULL COMMENT '英文名称（大写）',

  `balance` decimal(20,10) DEFAULT '0.0000000000' COMMENT '用户总资产',
  `available` decimal(20,10) DEFAULT '0.0000000000' COMMENT '用户可用资产',
  `withfraw_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（提币）冻结',
  `bond_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（保证金）冻结',
  `rw_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（充值）冻结',

  `status` smallint(1) DEFAULT '1' COMMENT '状态：1-正常，0-删除',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`asset_id`) ,
  UNIQUE KEY `uniq_user_id_coin_id` (`user_id`,`currency`) ,
  UNIQUE KEY `uniq_currency_crypto_address` (`currency`,`crypto_address`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商户 资产信息';

-- 商户资产记录
-- tb_merchant_record


-- 商户认证
CREATE TABLE `tb_underwriter_auth` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` bigint(11) NOT NULL COMMENT '用户编号',

  `area_type` smallint(1) DEFAULT NULL COMMENT '会员地区类别：1-中华人民共和国，2-其它国家或地区',
  `area_name` varchar(256) DEFAULT NULL COMMENT '地区名称',

  `auth_name` varchar(256) DEFAULT NULL COMMENT '认证名称',
  `auth_surname` varchar(256) DEFAULT '0' COMMENT '认证姓氏',

  `card_no` varchar(128) DEFAULT NULL COMMENT '身份证号码',
  `card_home` varchar(128) DEFAULT NULL COMMENT '证件封面',
  `card_back` varchar(128) DEFAULT NULL COMMENT '证件背面',
  `card_hand` varchar(128) DEFAULT NULL COMMENT '手持身份证',
  `card_start_time` varchar(20) DEFAULT NULL COMMENT '身份证起始时间',
  `card_end_time` varchar(20) DEFAULT NULL COMMENT '身份证结束时间',

  `status` smallint(1) DEFAULT '0' COMMENT '认证状态: 0：未认证，1：初级审核通过，-2：高级审核失败，2：高级审核通过，3：高级待审核，9-其它(与leve 表中的ID对应)',
  `note` varchar(512) DEFAULT NULL COMMENT '描述',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商户认证信息表';
