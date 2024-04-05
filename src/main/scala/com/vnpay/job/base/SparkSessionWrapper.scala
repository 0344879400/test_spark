package com.vnpay.jobs.base

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.{ReadConfig, WriteConfig}
import com.vnpay.config.Config
//import com.vnpay.constants.VNConstants
import org.apache.hadoop.fs.FileSystem
import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark._

trait SparkSessionWrapper extends Serializable {
	Logger.getLogger("org").setLevel(Level.OFF)
	Logger.getLogger("akka").setLevel(Level.OFF)
	Logger.getLogger("com.amazonaws").setLevel(Level.OFF)
	Logger.getLogger("io.netty").setLevel(Level.OFF)

	lazy val spark: SparkSession = {
		val sparkConf = new SparkConf()
			.setAppName(Config.get("jobName"))
			.set("spark.ui.showConsoleProgress", "true")

		val cephUri = Config.get("cephUri")
		if (cephUri != null) {
			sparkConf.set("spark.hadoop.fs.s3a.endpoint", cephUri)
				.set("spark.hadoop.fs.s3a.access.key", Config.get("cephEtlAccess"))
				.set("spark.hadoop.fs.s3a.secret.key", Config.get("cephEtlSecret"))
				.set("spark.hadoop.fs.s3a.path.style.access", "true")
				.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
				.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
				.set("spark.kryoserializer.buffer.max", "256m")
				.set("spark.shuffle.file.buffer", "1M")
		}

		SparkSession
			.builder
			.config(sparkConf)
			.getOrCreate()
	}


	// Only run if not running on YARN cluster
//	def initLog4jConfiguration(): Unit = {
//		val console = new ConsoleAppender
//		console.setLayout(new PatternLayout(VNConstants.LOG_PATTERN))
//		console.activateOptions()
//		BasicConfigurator.configure(console)
//	}

	def readMongodbCollection(mongodbCollection: String): DataFrame = {
		val uri = s"${Config.get("mongodbUri")}/${Config.get("mongodbDatabase")}.$mongodbCollection?authSource=${Config.get("mongodbAuthSource")}"
		val readConfig = ReadConfig(Map("spark.mongodb.input.uri" -> uri))
		MongoSpark.loadAndInferSchema(spark, readConfig)
	}

	def readMongodbCollectionTestV2(mongodbCollection: String): DataFrame = {
		val uri = s"${Config.get("mongodbUri")}/${Config.get("mongodbDatabase")}.$mongodbCollection?authSource=${Config.get("mongodbAuthSource")}"
		val readConfig = ReadConfig(Map("spark.mongodb.input.uri" -> uri))
		MongoSpark.loadAndInferSchema(spark, readConfig)
	}

	def readMongodbCollectionTest(mongodbCollection: String): DataFrame = {
		val uri = s"${Config.get("mongodbUri")}/${Config.get("mongodbDatabase")}.$mongodbCollection?authSource=analytics_db"
		val readConfig = ReadConfig(Map("spark.mongodb.input.uri" -> uri))
		MongoSpark.loadAndInferSchema(spark, readConfig)
	}

	def readMongodbCollectionStag(mongodbCollection: String): DataFrame = {
		val uri = s"${Config.get("mongodbUri")}/${Config.get("mongodbDatabase")}.$mongodbCollection?authSource=${Config.get("mongodbAuthSource")}"
		val readConfig = ReadConfig(Map("spark.mongodb.input.uri" -> uri))
		MongoSpark.loadAndInferSchema(spark, readConfig)
	}

	def readMongodbCollectionBE(mongodbCollection: String): DataFrame = {
		val uri = s"${Config.get("mongodbUriBE")}/${Config.get("mongodbDatabaseBE")}.$mongodbCollection"
		val readConfig = ReadConfig(Map("spark.mongodb.input.uri" -> uri))
		MongoSpark.loadAndInferSchema(spark, readConfig)
	}


	def writeMongodbCollection(mongodbCollection: String)
	                          (saveMode: SaveMode = SaveMode.Append)
	                          (df: DataFrame): Unit = {
		val collectionUri = s"${Config.get("mongodbUri")}/${Config.get("mongodbDatabase")}.$mongodbCollection?authSource=${Config.get("mongodbAuthSource")}"
		val writeConfig = WriteConfig(Map("spark.mongodb.output.uri" -> collectionUri, "spark.mongodb.output.ordered" -> "false"))
		MongoSpark.save(df.write.mode(saveMode), writeConfig)
	}

	def readMongoDB(database: String, uri: String, collection: String): DataFrame = {
		spark
			.read
			.format("mongo")
			.option("database", database)
			.option("spark.mongodb.input.uri", s"${uri}")
			.option("collection", collection)
			.option("encoding", "UTF-8")
			.load()
	}

	def writeMongoDB(df: DataFrame, database: String, uri: String, collection: String): Unit = {
		df
			.write
			.format("mongo")
			.option("database", database)
			.option("spark.mongodb.output.uri", s"${uri}")
			.option("collection", collection)
			.mode("append")
			.save()
	}

	def readPostgreDB(url: String, schema: String, tablename: String, username: String, password: String): DataFrame = {
		spark
			.read
			.format("jdbc")
			.option("url", s"${url}")
			.option("dbtable", tablename)
			.option("user", username)
			.option("password", password)
			.option("schema", s"${schema}")
			.option("driver", "org.postgresql.Driver")
			.load()
	}

	def writePostgreDB(df: DataFrame,
	                   url: String,
	                   schema: String,
	                   tablename: String,
	                   username: String,
	                   password: String,
	                   numPartitions: Int = 4,
	                   mode: SaveMode = SaveMode.Append): Unit = {
		df
			.repartition(numPartitions)
			.write
			.format("jdbc")
			.option("truncate", "true")
			.option("url", s"${url}")
			.option("dbtable", tablename)
			.option("user", username)
			.option("password", password)
			.option("schema", s"${schema}")
			.option("driver", "org.postgresql.Driver")
			.mode(mode)
			.save()
	}

	def readOracleTable(ipAddress: String,
	                    service: String,
	                    table: String,
	                    user: String,
	                    password: String,
	                    port: String = "1521"): DataFrame = {
		spark
			.read
			.format("jdbc")
			.option("url", s"jdbc:oracle:thin:@$ipAddress:$port/$service")
			.option("dbtable", table)
			.option("user", user)
			.option("password", password)
			.option("driver", "oracle.jdbc.driver.OracleDriver")
			.option("fetchsize", "10000")
			.load()
	}

	def readOracleTableStage(table: String, user: String, password: String): DataFrame = {
		spark
			.read
			.format("jdbc")
			.option("url", s"jdbc:oracle:thin:@${Config.get("oracleIpaddress")}:1521/AICNDL")
			.option("dbtable", table)
			.option("user", user)
			.option("password", password)
			.option("driver", "oracle.jdbc.driver.OracleDriver")
			.option("fetchsize", "10000")
			.load()
	}

	def writeOracleTable(ipAddress: String,
	                     service: String,
	                     table: String,
	                     user: String,
	                     password: String,
	                     df: DataFrame,
	                     port: String = "1521",
	                     mode: SaveMode = SaveMode.Append): Unit = {
		df
			.write
			.format("jdbc")
			.option("url", s"jdbc:oracle:thin:@$ipAddress:$port/$service")
			.option("dbtable", table)
			.option("user", user)
			.option("password", password)
			.option("driver", "oracle.jdbc.driver.OracleDriver")
			.option("batchsize", "10000")
			.option("truncate", "true")
			.mode(mode)
			.save()
	}

	def writeOracle(df: DataFrame, url: String, table: String, user: String, password: String): Unit = {
		df
			.write
			.format("jdbc")
			.option("url", s"${url}")
			.option("dbtable", table)
			.option("user", user)
			.option("password", password)
			.option("driver", "oracle.jdbc.driver.OracleDriver")
			.option("fetchsize", "10000")
			.mode(SaveMode.Append)
			.save()
	}

	def countApproxDf(df: DataFrame): Double = {
		df
			.rdd
			.countApprox(1000L)
			.initialValue
			.mean
	}

	lazy val repartitionNumber: Int => Int = (repartitionFactor: Int) => {
		try {
			val executorInstances = spark
				.conf
				.get("spark.executor.instances")
				.toInt

			val executorCores = spark
				.conf
				.get("spark.executor.cores")
				.toInt

			executorInstances * executorCores * repartitionFactor
		} catch {
			case _: Exception => 200
		}
	}

	lazy val hdfsFileSystem: FileSystem = FileSystem.get(spark.sparkContext.hadoopConfiguration)
}
