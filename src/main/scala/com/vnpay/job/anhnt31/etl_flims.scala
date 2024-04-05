package com.vnpay.job.anhnt31
//import java.io.PrintWriter
import com.vnpay.config.Config
import com.vnpay.job.base.BaseJob
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, lit}

//import org.apache.spark.sql.expressions.Window
//import org.apache.spark.sql.functions.{coalesce, col, count, countDistinct, date_format, date_trunc, lit, row_number, sum, to_date, when}

class etl_flims (configPath: String, dataPath: String, taskName: String) extends BaseJob {

  @transient lazy val log: Logger = Logger.getLogger(getClass.getName)
  log.setLevel(Level.DEBUG)

  val jobName = "etl_jobs"
  loadConfig(configPath, jobName)

  val dataDate: String = dataPath.split("/").last

  override def run(): Unit = {

    taskName match {
      case "ETL_JOBS" => etl_jobs()
      case _ => log.error("Wrong task name!")
    }
  }
  def etl_jobs(): Unit ={
    val readFlimsDf: DataFrame = {
      //Connect to VNFILM_FILM_QUERY
      val film_query_Df = readOracleTable(
        Config.get("oracleIpaddress"),
        Config.get("oracleAicndlService"),
        "AICNDL.VNFILM_FILM_QUERY",
        Config.get("oracleAicndlUser"),
        Config.get("oracleAicndlPassword")
      ).alias("rp")
      film_query_Df
    }

    // Number of Customers requesting/initiating transactions
    val NUM_BOOKING_CUS = readFlimsDf.select("APP_MOBILE_HASH").distinct().count()

    // Number of requests/initiated transactions
    val QUANTITY_BOOKING = readFlimsDf.select("QUERY_ID").distinct().count()

    // Number of customers who successfully paid
    val NUM_PAID_CUS = readFlimsDf.filter("STATUS = '05'").select("APP_MOBILE_HASH").distinct().count()

    // Number of successful transactions
    val QUANTITY_PAYMENT = readFlimsDf.filter("STATUS = '05'").select("QUERY_ID").distinct().count()

    // Number of customers who successfully paid using the assigned code
    val NUM_USED_VOUCHER_CUS = readFlimsDf.filter("STATUS = '05' AND PROMOTION_CODE IS NOT NULL ")
      .select("APP_MOBILE_HASH").distinct().count()

    // Number of successful transactions using the code
    val QUANTITY_PAYMENT_VOUCHER = readFlimsDf.filter("STATUS = '05' AND QUERY_ID IS NOT NULL")
      .select("QUERY_ID").distinct().count()

    println(s"Number of Customers requesting/initiating transactions: $NUM_BOOKING_CUS")
    println(s"Number of requests/initiated transactions: $QUANTITY_BOOKING")
    println(s"Number of customers who successfully paid: $NUM_PAID_CUS")
    println(s"Number of successful transactions: $QUANTITY_PAYMENT")
    println(s"Number of customers who successfully paid using the assigned code: $NUM_USED_VOUCHER_CUS")
    println(s"Number of successful transactions using the code: $QUANTITY_PAYMENT_VOUCHER")

    // Tạo DataFrame với việc chuyển đổi kiểu dữ liệu
    val metricsDF = spark.createDataFrame(Seq(
        ("Number of Customers requesting/initiating transactions", NUM_BOOKING_CUS),
        ("Number of requests/initiated transactions", QUANTITY_BOOKING),
        ("Number of customers who successfully paid", NUM_PAID_CUS),
        ("Number of successful transactions", QUANTITY_PAYMENT),
        ("Number of customers who successfully paid using the assigned code", NUM_USED_VOUCHER_CUS),
        ("Number of successful transactions using the code", QUANTITY_PAYMENT_VOUCHER),
      )).toDF("Metric", "Value")
      .withColumn("Value", col("Value").cast("string"))

    //    // Lưu các metrics vào PostgreSQL
//    val jdbcUrl = "jdbc:postgresql://10.22.19.106:5432/<database>"
//    val connectionProperties = new java.util.Properties()
//    connectionProperties.setProperty("user", "<username>")
//    connectionProperties.setProperty("password", "<password>")
//
//    metricsDF.write.mode("overwrite").jdbc(jdbcUrl, "metrics", connectionProperties)


  }

}
