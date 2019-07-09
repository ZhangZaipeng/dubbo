-- 商户 绑定 代理商（一个代理商 只能绑定 一个商户）
DROP TABLE IF EXISTS `tb_merchant_proxy`;
CREATE TABLE `tb_merchant_proxy` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `merchant_id` bigint(11) NOT NULL COMMENT '商户 编号',
  `proxy_underwriter_id` BIGINT(11) NOT NULL COMMENT '代理 承兑商编号',

  `curr_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '当前商户 给代理的提成点',
  `default_next_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '代理给组长的默认提成点',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_underwriter_id` (`proxy_underwriter_id`),
  UNIQUE KEY `uniq_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='商户 绑定 承兑商';


-- 代理商 与 小组长
CREATE TABLE `tb_proxy_group` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',

  `proxy_underwriter_id` BIGINT(11) NOT NULL COMMENT '代理 承兑商编号',
  `group_underwriter_id` BIGINT(11) NOT NULL COMMENT '组长 承兑商编号',

  `curr_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '当前代理商 给组长的提成点',
  `default_next_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '组长给承兑商的默认提成点',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代理商 与 小组长';

-- 小组长 与 承兑商
CREATE TABLE `tb_group_underwriter` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',

  `group_underwriter_id` BIGINT(11) NOT NULL COMMENT '代理 承兑商编号',
  `underwriter_id` BIGINT(11) NOT NULL COMMENT '承兑商编号',

  `curr_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '当前组长 给承兑商的提成点',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='小组长 与 承兑商';


-- 承兑商角色
CREATE TABLE `tb_underwriter_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `status` smallint(1) DEFAULT '1' COMMENT '删除标识 0已删除，1正常',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='承兑商角色';

INSERT INTO `tb_underwriter_role`(`role_id`, `role_name`, `remark`) VALUES (1, '代理商', '承兑商 代理商角色');
INSERT INTO `tb_underwriter_role`(`role_id`, `role_name`, `remark`) VALUES (2, '组长', '承兑商 组长角色');
INSERT INTO `tb_underwriter_role`(`role_id`, `role_name`, `remark`) VALUES (3, '承兑商', '承兑商 组长角色');


DROP TABLE IF EXISTS `tb_underwriter_proxy`;
CREATE TABLE `tb_underwriter_proxy` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `merchant_id` bigint(11) NOT NULL COMMENT '商户 编号',

  `up_underwriter_id` BIGINT(11) NOT NULL COMMENT '上级承兑商ID',
  `curr_underwriter_id` BIGINT(11) NOT NULL COMMENT '当前承兑商ID',
  `curr_underwriter_role` int(11) NOT NULL COMMENT '当前承兑商 角色ID',

  `curr_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '当前商户 给代理的提成点',
  `default_next_commission` DECIMAL(10,5) DEFAULT NULL COMMENT '给下级的默认提成点',

  `type` smallint(1) DEFAULT '1' COMMENT '状态：1 ：商户绑定代理商,2：代理商与小组长, 3, 小组长与承兑商',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_merchant_id` (`merchant_id`),
  INDEX `idx_up_underwriter_id` (`up_underwriter_id`),
  UNIQUE KEY `uniq_curr_underwriter_id` (`curr_underwriter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='商户 绑定 承兑商';
