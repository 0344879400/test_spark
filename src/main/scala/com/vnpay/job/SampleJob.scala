package com.vnpay.job

import com.vnpay.job.base.BaseJob
import org.apache.log4j.{Level, LogManager, Logger}

class SampleJob(configPath: String, dataPath: String) extends BaseJob {

    System.setProperty("hadoop.home.dir", "/path/to/hadoop/winutils")

    @transient lazy val log: Logger = LogManager.getLogger(getClass.getName)
    log.setLevel(Level.DEBUG)

    val jobName = "SampleJob"
    loadConfig(configPath, jobName)
    import spark.implicits._

    override def run(): Unit = {
        val df = Seq(1, 2, 3).toDF()
        df.show()
    }
}

object SampleJob {
    def main(args: Array[String]): Unit = {
        new SampleJob("", "").run()
    }
}