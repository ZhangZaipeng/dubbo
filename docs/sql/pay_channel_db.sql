-- 支付通道表 --

-- 支持的币种
DROP TABLE IF EXISTS `tb_pay_channel_coin`;
CREATE TABLE `tb_pay_channel_coin` (
  `symbol` varchar(50) NOT NULL COMMENT '货币对',
  `coin_id` bigint(11) NOT NULL COMMENT '币种ID',
  `coin_name` varchar(50) NOT NULL COMMENT '购买/充值 币种名称',
  `unit_id` bigint(11) NOT NULL COMMENT '币种ID',
  `unit_name` varchar(50) NOT NULL COMMENT '交易币种名称',
  `rate` decimal(12,5) DEFAULT NULL COMMENT '兑率',
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
COMMENT='支持的币种'
AUTO_INCREMENT=1
;


--  商户购买（用户充值） 订单状态：1.匹配中 2.待付款 3.用户待确认 4.承兑商待确认 5.完成
tb_pay_channel_recharge
DROP TABLE IF EXISTS `tb_pay_channel_recharge`;
CREATE TABLE `tb_pay_channel_recharge` (
	`recharge_order_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 编号',
	`recharge_order_token` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 令牌',
	`underwriter_id` BIGINT(11) DEFAULT NULL COMMENT '所属 承兑商编号',

  `coin_id` bigint(11) NOT NULL COMMENT '币种ID',
  `coin_name` varchar(50) NOT NULL COMMENT '购买/充值 币种名称',
  `unit_id` bigint(11) NOT NULL COMMENT '币种ID',
  `unit_name` varchar(50) NOT NULL COMMENT '交易币种名称',
  `recharge_amt` DECIMAL(20,8) DEFAULT NULL COMMENT '购买/充值金额，',

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
COMMENT='商户购买表，用户充值表'
AUTO_INCREMENT=1
;

-- 商户出售（用户提现） 订单状态：1.抢单中 2.待付款 3.用户待确认 4.承兑商待确认 5.完成
DROP TABLE IF EXISTS `tb_pay_channel_withdraw`;
CREATE TABLE `tb_pay_channel_withdraw` (
	`withdraw_order_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 编号',
	`withdraw_order_token` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '订单 令牌',
	`underwriter_id` BIGINT(11) DEFAULT NULL COMMENT '所属 承兑商编号',

  `coin_id` bigint(11) NOT NULL COMMENT '币种ID',
  `coin_name` varchar(50) NOT NULL COMMENT '出售/提现 币种名称',
  `unit_id` bigint(11) NOT NULL COMMENT '币种ID',
  `unit_name` varchar(50) NOT NULL COMMENT '交易币种名称',

  `withdraw_amt` DECIMAL(20,8) DEFAULT NULL COMMENT '出售/提现 金额，',
  `withdraw_type`
  `withdraw_account` varchar(50) DEFAULT NULL COMMENT '出售/提现 账户：',

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
COMMENT='商户出售，用户提现表'
AUTO_INCREMENT=1
;

-- 订单流水表
DROP TABLE IF EXISTS `tb_pay_journal_account`;
CREATE TABLE `tb_pay_journal_account` (
	`journal_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '流水编号',
	`underwriter_id` BIGINT(11) NOT NULL COMMENT '所属 承兑商编号',
	`order_id` VARCHAR(20) NOT NULL COMMENT '订单编号 G160705000000000001',
	`order_type` VARCHAR(50) NOT NULL COMMENT '订单类型 recharge/withdraw',

  `order_amt` DECIMAL(20,4) NOT NULL COMMENT '订单金额',
	`order_time` TIMESTAMP NOT NULL COMMENT '订单日期',
	`order_day` INT NOT NULL COMMENT '订单日期, 例如 20160112;20160101',
	`order_hour` INT NOT NULL COMMENT '订单日期小时部分, 0~23点',

	`callback_finished` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '回调是否成功 1=是，0=否',
	`callback_retry` SMALLINT(1) NOT NULL DEFAULT 0 COMMENT '回调 剩余重试次数 默认3次',
	`callback_data` VARCHAR(255) NOT NULL DEFAULT 0 COMMENT '回调信息记录 json',

	`pay_time` TIMESTAMP NULL COMMENT '支付日期',
	`pay_day` INT NULL COMMENT '支付日期, 例如 20160112;20160101',
	`pay_hour` INT NULL COMMENT '支付日期小时部分, 0~23点',
	`pay_name` VARCHAR(255) NULL COMMENT '支付人的真实姓名',
	`pay_opt` VARCHAR(10) NULL COMMENT '付款方式 微信/支付宝/农行/数字货币',
	`pay_mark` VARCHAR(255) NULL COMMENT '备注',

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
