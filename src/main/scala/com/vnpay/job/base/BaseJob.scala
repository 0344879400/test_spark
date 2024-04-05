package com.vnpay.job.base

import com.vnpay.config.Config
import com.vnpay.jobs.base.SparkSessionWrapper
import com.vnpay.utils.FileUtil.readConfig

abstract class BaseJob extends SparkSessionWrapper {

	protected def loadConfig(configPath: String, jobName: String): Unit = {
		val prop = readConfig(configPath)

		Config.production += ("jobName" -> jobName)
		Config.production += ("jobName" -> jobName)

		Config.production += ("cephUri" -> prop.getProperty("ceph.uri"))
		Config.production += ("cephEtlAccess" -> prop.getProperty("ceph.etl.access"))
		Config.production += ("cephEtlSecret" -> prop.getProperty("ceph.etl.secret"))
		Config.production += ("cephCdpAccess" -> prop.getProperty("ceph.cdp.access"))
		Config.production += ("cephCdpSecret" -> prop.getProperty("ceph.cdp.secret"))

		Config.production += ("mongodbUri" -> prop.getProperty("mongodb.uri"))
		Config.production += ("mongodbAuthSource" -> prop.getProperty("mongodb.authSource"))
		Config.production += ("mongodbDatabase" -> prop.getProperty("mongodb.database"))
		Config.production += ("mongodbConnect" -> (prop.getProperty("mongodb.uri") + "/?authSource=" + prop.getProperty("mongodb.authSource")))

		Config.production += ("oracleIpaddress" -> prop.getProperty("oracle.ipaddress"))
		Config.production += ("oracleAicndlUser" -> prop.getProperty("oracle.aicndl.user"))
		Config.production += ("oracleAicndlPassword" -> prop.getProperty("oracle.aicndl.password"))
		Config.production += ("oracleDatacdpUser" -> prop.getProperty("oracle.datacdp.user"))
		Config.production += ("oracleDatacdpPassword" -> prop.getProperty("oracle.datacdp.password"))
		Config.production += ("oracleMetadataUser" -> prop.getProperty("oracle.metadata.user"))
		Config.production += ("oracleMetadataPassword" -> prop.getProperty("oracle.metadata.password"))

		Config.production += ("bankCodeMappingPath" -> "/data/raw/bank_code/bank-code-mapping.txt")

		Config.production += ("mssqlDatamartIpaddress" -> prop.getProperty("mssql.datamart.ipaddress"))
		Config.production += ("mssqlDatamartUser" -> prop.getProperty("mssql.datamart.user"))
		Config.production += ("mssqlDatamartPassword" -> prop.getProperty("mssql.datamart.password"))

		Config.production += ("oracleAicndlService" -> prop.getProperty("oracle.aicndl.servicename"))
		Config.production += ("oracleDatamartUser" -> prop.getProperty("oracle.datamart.user"))
		Config.production += ("oracleDatamartPassword" -> prop.getProperty("oracle.datamart.password"))

		Config.production += ("oracleThanhbt1User" -> prop.getProperty("oracle.thanhbt1.user"))
		Config.production += ("oracleThanhbt1Password" -> prop.getProperty("oracle.thanhbt1.password"))

		Config.production += ("oracleCesThanhbt1User" -> prop.getProperty("oracle.ces.thanhbt1.user"))
		Config.production += ("oracleCesThanhbt1Password" -> prop.getProperty("oracle.ces.thanhbt1.password"))

		Config.production += ("oracleReportUser" -> prop.getProperty("oracle.report.user"))
		Config.production += ("oracleReportPassword" -> prop.getProperty("oracle.report.password"))

		Config.production += ("oracleDetailUser" -> prop.getProperty("oracle.detail.user"))
		Config.production += ("oracleDetailPassword" -> prop.getProperty("oracle.detail.password"))

		Config.production += ("oracleBidvUser" -> prop.getProperty("oracle.bidv.user"))
		Config.production += ("oracleBidvPassword" -> prop.getProperty("oracle.bidv.password"))

	}

	protected def shutDown(): Unit = {
		spark.stop()
	}

	protected def shutDownAndClose(): Unit = {
		shutDown()
	}

	def run(): Unit
}
