-- 承兑商表 underwriter
DROP TABLE IF EXISTS `tb_underwriter`;
CREATE TABLE `tb_underwriter` (
  `underwriter_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '承兑商 id 编号',
  `merchant_id` bigint(11) DEFAULT NULL COMMENT '所属商户',

  -- infor
  `nick_name` varchar(20) DEFAULT NULL COMMENT '承兑商 昵称',
  `icon_img_url` varchar(128) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',

  `google_auth` varchar(20) DEFAULT NULL COMMENT '谷歌验证码',
  `google_auth_url` varchar(255) DEFAULT NULL COMMENT '谷歌验证码链接',
  `google_auth_lock` smallint(1) DEFAULT '0' COMMENT '是否关闭谷歌认证  默认 0关闭状态  1 开启',

  `is_bond` smallint(1) DEFAULT '0' COMMENT '是否支付保定金 0否 1是',
  `is_listening` smallint(1) DEFAULT '0' COMMENT '是否听单中 0否 1是',
  `mobile_notification` smallint(1) DEFAULT '1' '手机通知 是否开启 0否 1是',

  -- auth
  `auth_num` smallint(1) DEFAULT '0' COMMENT '实名次数',
  `auth_level` smallint(1) DEFAULT '0' COMMENT '0 未认证 1 初级认证 2高级认证',
  `security_pwd` varchar(100) DEFAULT NULL COMMENT '资金安全密码',
  `invitees_merchant_id` bigint(11) DEFAULT '0' COMMENT '被邀请人用户ID， 0 没有邀请人',
  `area` varchar(100) DEFAULT NULL COMMENT '区域 省-市-区',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`underwriter_id`),
  UNIQUE KEY `uniq_merchant_id` (`merchant_id`),
  UNIQUE KEY `uniq_mobile` (`mobile`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=98000039 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='承兑商表';

-- 承兑商登录表 Underwriter
DROP TABLE IF EXISTS `tb_underwriter_agent`;
CREATE TABLE `tb_merchant_agent` (
  `underwriter_agent_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商户 登录编号',
  `underwriter_id` bigint(11) NOT NULL COMMENT '商户 编号',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',

  `login_pwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `deleted` smallint(1) DEFAULT '0' COMMENT '账号状态： 1 有效 0 无效 ',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`underwriter_agent_id`),
  UNIQUE KEY `uniq_underwriter_id` (`underwriter_id`),
  UNIQUE KEY `uniq_telephone` (`telephone`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='承兑商登录表';



-- 承兑商 收款方式
DROP TABLE IF EXISTS `tb_underwriter_receivables`;
CREATE TABLE `tb_underwriter_receivables` (
  `underwriter_id` bigint(11) NOT NULL COMMENT '商户 编号',

  `alipay_is_open` smallint(1) DEFAULT '0' COMMENT '支付宝 打开方式  0：未开启 1：开启',
  `alipay_real_name` varchar(20) DEFAULT NULL COMMENT '支付宝 昵称账号',
  `alipay_account` varchar(20) DEFAULT NULL COMMENT '支付宝 账号',
  `alipay_img_url` varchar(20) DEFAULT NULL COMMENT '支付宝 图片',

  `wx_is_open`smallint(1) DEFAULT '0' COMMENT '微信 打开方式 0：未开启 1：开启',
  `wx_real_name` varchar(20) DEFAULT NULL COMMENT '微信 真实姓名',
  `wx_account` varchar(20) DEFAULT NULL COMMENT '微信 账号',
  `wx_img_url` varchar(128) DEFAULT NULL COMMENT '微信 图片',

  `bank_is_open` smallint(1) DEFAULT '0' COMMENT '银行卡 打开方式 0：未开启 1：开启',
  `bank_real_name` varchar(20) DEFAULT NULL COMMENT '银行卡 真实姓名',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '开户银行',
  `bank_address` varchar(128) DEFAULT NULL COMMENT '银行地址',
  `bank_number` varchar(128) DEFAULT NULL COMMENT '银行卡号',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`underwriter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='承兑商 收款方式';


-- 承兑商 资产表
CREATE TABLE `tb_underwriter_asset` (
  `asset_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `underwriter_id` bigint(11) NOT NULL COMMENT '承兑商 标识',

  `crypto_address` varchar(100) DEFAULT NULL COMMENT '资产充值地址',
  `currency` varchar(20) NOT NULL COMMENT '英文名称（大写）',

  `balance` decimal(20,10) DEFAULT '0.0000000000' COMMENT '用户总资产',
  `available` decimal(20,10) DEFAULT '0.0000000000' COMMENT '用户可用资产',
  `withfraw_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（提币）冻结',
  `bond_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（保证金）冻结',
  `rw_freez` decimal(20,10) DEFAULT '0.0000000000' COMMENT '（用户提现）冻结',

  `status` smallint(1) DEFAULT '1' COMMENT '状态：1-正常，0-删除',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`asset_id`) ,
  UNIQUE KEY `uniq_user_id_coin_id` (`user_id`,`currency`) ,
  UNIQUE KEY `uniq_currency_crypto_address` (`currency`,`crypto_address`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='承兑商资产信息';

-- 商户资产记录
-- tb_underwriter_record


CREATE TABLE `tb_underwriter_auth` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `underwriter_id` bigint(11) NOT NULL COMMENT '用户编号',

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='承兑商认证 信息表';

