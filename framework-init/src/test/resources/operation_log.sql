CREATE TABLE `operation_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `operation_time` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '操作时间',
  `table_name` varchar(64) DEFAULT NULL COMMENT '操作表名',
  `operation_type` tinyint(3) unsigned DEFAULT '0' COMMENT '操作类型',
  `pk_id` varchar(64) DEFAULT NULL COMMENT '操作的记录唯一键ID',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人的 id',
  `operator_name` varchar(32) DEFAULT NULL COMMENT '操作人账号',
  `result` varchar(16) DEFAULT NULL COMMENT '操作结果。',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注，例如可以存入 sql',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;