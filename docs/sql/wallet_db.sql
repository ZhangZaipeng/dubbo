-- 钱包表 --

-- 币种表
CREATE TABLE `tb_coin` (
  `coin_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `wallet_id` bigint(11) NOT NULL COMMENT '钱包ID',
  `icon_path` varchar(256) NOT NULL COMMENT '图标地址',
  `coin_name_cn` varchar(20) NOT NULL COMMENT '交易币种 中文名称',
  `coin_name_en` varchar(20) NOT NULL COMMENT '交易币种 英文名称（大写）',
  `sort` smallint(1) DEFAULT '0' COMMENT '排序',
  `status` smallint(1) DEFAULT '1' COMMENT '状态 0 关闭 1 开启',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`coin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='币种';

-- 币种关联配置
CREATE TABLE `tb_coin_item` (
  `item_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `coin_id` bigint(11) DEFAULT NULL COMMENT '交易币种ID',
  `can_withdraw` smallint(3) DEFAULT '1' COMMENT '是否可以提现：0-关闭；1-开启',
  `can_recharge` smallint(3) DEFAULT '1' COMMENT '是否可以充值：0-关闭；1-开启',
  `is_concentrate` smallint(3) DEFAULT '0' COMMENT '是否开启资金归集：0-关闭；1-开启',
  `concentrate_fee` decimal(50,8) DEFAULT '0.00000000' COMMENT '资金归集费率；',
  `main_coin` smallint(1) DEFAULT NULL COMMENT '1:主币;0:代币;',
  `scale` smallint(5) DEFAULT '8' COMMENT '小数位',
  `recharge_block` smallint(5) DEFAULT '3' COMMENT '充值 区块确认数',
  `withdraw_block` smallint(5) DEFAULT '15' COMMENT '提现 区块确认数',
  `hot_to_cold_percent` decimal(4,2) DEFAULT '0.00' COMMENT '热钱包余额转入冷钱包百分比',
  `hot_to_cold_threshold` decimal(50,8) DEFAULT '5.00000000' COMMENT '热钱包余额转入冷钱包阀值',
  `hot_to_cold_cold_fee` decimal(50,8) DEFAULT '0.00000000' COMMENT '热转冷旷工费',
  `cold_address` varchar(255) DEFAULT '' COMMENT '冷钱包地址',
  `threshold_hot` decimal(40,8) DEFAULT '0.00000000' COMMENT '单账户集中到热钱包阀值',
  `min_recharge` decimal(50,18) DEFAULT '0.000000000000000000' COMMENT '最小充值数量',
  `min_withdraw` decimal(50,18) DEFAULT '0.000000000000000000' COMMENT '最小转账数量',
  `withdraw_scale` smallint(5) DEFAULT '8' COMMENT '转账最大小数位长度',
  `withdraw_fee` decimal(50,8) DEFAULT '0.00000000' COMMENT '提现手续费(用户扣除的)',
  `withdraw_block_fee` decimal(50,8) DEFAULT '0.00000000' COMMENT '实际支付的旷工费(区块实际扣除的)',
  `withdraw_fee_coin_id` bigint(11) DEFAULT '0' COMMENT '手续费扣除的币种ID(为0则扣除自身)',
  `withdraw_internal` smallint(1) DEFAULT '0' COMMENT '是否开启内部转账0=关闭；1=开启',
  `withdraw_internal_fee` decimal(50,8) DEFAULT '0.00000000' COMMENT '开启内部转账的手续费',
  `token_to_fee` decimal(50,18) DEFAULT '0.000000000000000000' COMMENT 'ETH代币不足时转入的ETH数量',
  `contract_address` varchar(100) DEFAULT '' COMMENT '合约地址',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`item_id`) USING BTREE,
  UNIQUE KEY `uniq_tcoin_id` (`coin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='币种关联配置';

-- 充币记录
CREATE TABLE `tb_coin_recharge` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `record_id` bigint(11) DEFAULT NULL COMMENT '流水记录ID',
  `recharge_type` smallint(1) NOT NULL DEFAULT '0' COMMENT '充值类型；0=普通转账；1=内部转账',
  `coin_id` bigint(11) NOT NULL COMMENT '虚拟币id',
  `tx_token` varchar(20) NOT NULL COMMENT '资产TOKEN',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `asset_id` bigint(11) NOT NULL COMMENT '资产地址ID',
  `tx_hash` varchar(100) DEFAULT NULL COMMENT '事物ID',
  `out_index` int(3) DEFAULT '0' COMMENT 'vout',
  `asset_address` varchar(100) DEFAULT NULL COMMENT '接收充值的地址',
  `recharge_address` varchar(100) DEFAULT NULL COMMENT '充值地址',
  `recharge_value` decimal(40,18) DEFAULT NULL COMMENT '充值数量',
  `block_number` int(10) DEFAULT NULL COMMENT '区块高度',
  `confirmation_status` smallint(1) NOT NULL DEFAULT '-1' COMMENT '状态(-1:待检测 0=失败1:已确认,且已入账)',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `block_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '接收时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_tx_hash_vout` (`tx_hash`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='充币记录';

-- 币种转账记录
CREATE TABLE `tb_coin_withdraw` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `record_id` bigint(11) DEFAULT NULL COMMENT '流水ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `asset_address` varchar(100) DEFAULT NULL COMMENT '接收资产地址',
  `tx_apply_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易申请时间',
  `tx_handling_time` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  `tx_payment_date` timestamp NULL DEFAULT NULL COMMENT '到账时间',
  `tx_apply_status` smallint(1) DEFAULT '0' COMMENT '交易申请状态0:待审核 1:审核通过  -1:审核拒绝',
  `tx_apply_value` decimal(50,18) DEFAULT NULL COMMENT '提现数量',
  `tx_transfer_address` varchar(100) DEFAULT NULL COMMENT '转账地址；',
  `tx_hash` varchar(100) DEFAULT '' COMMENT '交易hash',
  `tx_token` varchar(20) DEFAULT NULL COMMENT '交易申请token（大写）',
  `tx_receipt` varchar(2000) DEFAULT '' COMMENT '交易凭据',
  `tx_gas_price` decimal(50,18) DEFAULT '0.000000000000000000' COMMENT 'ETH手续费价格',
  `tx_amount_fee` decimal(50,18) DEFAULT '0.000000000000000000' COMMENT '实际支付旷工费',
  `withdraw_fee` decimal(50,18) DEFAULT NULL COMMENT '提现手续费',
  `withdraw_status` smallint(1) DEFAULT '0' COMMENT '提现状态 -1:失败 0:待处理  1:处理中 5:处理完毕(等待区块确认中) 2:提现成功',
  `withdraw_msg` varchar(255) DEFAULT '' COMMENT '提现消息',
  `withdraw_fee_coin_id` bigint(11) DEFAULT NULL COMMENT '手续费扣除的币种ID',
  `is_internal` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否是内部转账',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `deleted` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='币种转账记录';

-- 资金归集
CREATE TABLE `tb_gather_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `record_type` smallint(3) DEFAULT '0' COMMENT '记录类型 0,资金归集；1,热钱包转冷钱包；2,转账手续费',
  `from_address` varchar(100) DEFAULT NULL COMMENT '发送地址',
  `to_address` varchar(100) DEFAULT NULL COMMENT '接收地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `amount` decimal(30,8) DEFAULT NULL COMMENT '数量',
  `actual_amount` decimal(30,8) DEFAULT NULL COMMENT '实际数量',
  `gather_status` tinyint(3) DEFAULT '2' COMMENT '归集状态(0失败1成功2归集中)',
  `coin_token` varchar(20) DEFAULT NULL COMMENT '币种',
  `tx_hash` varchar(255) DEFAULT NULL COMMENT '交易凭证ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='资金归集';

-- 钱包地址库
CREATE TABLE `tb_address_lib` (
  `addr_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `is_use` smallint(1) DEFAULT NULL COMMENT '是否被使用1：已使用；0：未使用',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `coin_type` varchar(10) DEFAULT NULL COMMENT '类别',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `pwd` varchar(255) DEFAULT NULL COMMENT '密码',
  `iv` varchar(255) DEFAULT NULL COMMENT 'iv向量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`addr_id`) USING BTREE,
  UNIQUE KEY `idx_user_id_coin_type` (`user_id`,`coin_type`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_address` (`address`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2003 DEFAULT CHARSET=utf8 ROW_FORMAT=DEFAULT COMMENT='钱包地址库';
