-- 支付通道表 --

-- 支持的币种
tb_pay_channel_coin

--  购买（充值） 订单状态：1.匹配中 2.待付款 3.用户待确认 4.承兑商待确认 5.完成
tb_pay_channel_recharge
DROP TABLE IF EXISTS `tb_pay_channel_recharge`;
CREATE TABLE `tb_pay_channel_recharge` (
	`recharge_order_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 编号',
	`recharge_order_token` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 令牌',
	`underwriter_id` BIGINT(11) DEFAULT NULL COMMENT '所属 承兑商编号',

  `status` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '订单状态：1.匹配中 2.待付款 3.用户待确认 4.承兑商待确认 5.完成',

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`recharge_order_id`),
	UNIQUE `idx_order_token` (`recharge_order_token`)
	INDEX `idx_underwriter_id` (`underwriter_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
COMMENT='用户购买表，用户充值表'
AUTO_INCREMENT=1
;

-- 出售（提现） 订单状态：1.匹配中 2.待付款 3.用户待确认 4.承兑商待确认 5.完成
DROP TABLE IF EXISTS `tb_pay_channel_withdraw`;
CREATE TABLE `tb_pay_channel_withdraw` (
	`withdraw_order_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 编号',
	`withdraw_order_token` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 令牌',
	`underwriter_id` BIGINT(11) DEFAULT NULL COMMENT '所属 承兑商编号',

  `status` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '',

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`recharge_order_id`),
	UNIQUE `idx_order_token` (`recharge_order_token`)
	INDEX `idx_underwriter_id` (`underwriter_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
COMMENT='用户购买表，用户充值表'
AUTO_INCREMENT=1
;

-- 订单流水表
DROP TABLE IF EXISTS `tb_pay_journal_account`;
CREATE TABLE `tb_pay_journal_account` (
	`journal_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '流水编号',
	`underwriter_id` BIGINT(11) NOT NULL COMMENT '所属 承兑商编号',
	`order_id` VARCHAR(20) NOT NULL COMMENT '订单编号 G160705000000000001',
	`order_type` VARCHAR(50) NOT NULL COMMENT '订单类型 recharge/withdraw',

	`order_time` TIMESTAMP NOT NULL COMMENT '订单日期',
	`order_day` INT NOT NULL COMMENT '订单日期, 例如 20160112;20160101',
	`order_hour` INT NOT NULL COMMENT '订单日期小时部分, 0~23点',

	`callback_finished` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '回调是否成功 1=是，0=否',
	`callback_retry` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '回调 剩余重试次数 默认3次',
	`callback_data` VARCHAR(255) NOT NULL DEFAULT 0 COMMENT '回调信息记录 json',

	`pay_time` TIMESTAMP NULL COMMENT '支付日期',
	`pay_day` INT NULL COMMENT '支付日期, 例如 20160112;20160101',
	`pay_hour` INT NULL COMMENT '支付日期小时部分, 0~23点',

	`order_amt` DECIMAL(20,4) NOT NULL COMMENT '订单金额',
	`pay_opt` VARCHAR(10) NULL COMMENT '付款方式 微信/支付宝/农行',
	`mark` VARCHAR(255) NULL COMMENT '备注',

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`journal_id`),
	INDEX `idx_underwriter_id` (`underwriter_id`),
	UNIQUE `idx_order_id` (`order_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
COMMENT='流水账, 下完订单就有一条记录'
AUTO_INCREMENT=1
;
