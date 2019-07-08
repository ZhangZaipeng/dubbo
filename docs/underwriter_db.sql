-- 承兑商表 underwriter
DROP TABLE IF EXISTS `tb_underwriter`;
CREATE TABLE `tb_underwriter` (
  `underwriter_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '承兑商 id 编号',
  -- infor
  `nick_name` varchar(20) DEFAULT NULL COMMENT '承兑商 昵称',
  `icon_img_url` varchar(128) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',


  --
  `` '是否听单中',
  `` '成交总数',

  -- auth
  `auth_num` smallint(1) DEFAULT '0' COMMENT '实名次数',
  `auth_level` smallint(1) DEFAULT '0' COMMENT '0 未认证 1 初级认证 2高级认证',
  `security_pwd` varchar(100) DEFAULT NULL COMMENT '资金安全密码',

  `invitees_merchant_id` bigint(11) DEFAULT '0' COMMENT '被邀请人用户ID， 0 没有邀请人',
  `relation_path` '关系路径 9012998/9893843/9731231 ',
  `` '区域',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

  PRIMARY KEY (`underwriter_id`),
  UNIQUE KEY `uniq_mobile` (`mobile`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=98000039 DEFAULT CHARSET=utf8 COMMENT='承兑商表';

-- 承兑商登录表 Underwriter
DROP TABLE IF EXISTS `tb_underwriter_agent`;
CREATE TABLE `tb_merchant_agent` (
  `underwriter_agent_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户 登录编号',
  `underwriter_id` bigint(20) NOT NULL COMMENT '商户 编号',
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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='承兑商登录表';

-- 承兑商 收款方式

-- 承兑商 成交统计
'承兑商 id'
'时间 2019-07-05'
'成交'
'剩余金额'
'剩余金额'


-- 承兑商 用户权限角色
