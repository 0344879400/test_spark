package com.vnpay
import com.vnpay.job.SampleJob
import com.vnpay.job.anhnt31.etl_flims

object App {

    def main(args: Array[String]) {
        if (args.length > 2) {
            val command = args(0)
            val configPath = args(1)
            val dataPath = args(2)

            println(s"command = $command")
            println(s"configPath = $configPath")
            println(s"dataPath = $dataPath")

            command match {
                case "SampleJob" => new SampleJob(configPath=configPath, dataPath=dataPath).run()

                case "Metric_2" => new etl_flims(configPath = configPath, dataPath = dataPath, taskName = "ETL_JOBS").run()
                case _ => println("Wrong command!")
            }

        }
        }

    }

