package samples

import base.SparkSessionTestWrapper
import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.apache.spark.sql.functions.col
import org.scalatest.FunSpec

class SampleTest extends FunSpec with SparkSessionTestWrapper with DatasetComparer {

    import spark.implicits._

    System.setProperty("hadoop.home.dir", "/path/to/hadoop/winutils")

    it("aliases a DataFrame") {

        val sourceDF = Seq(
            ("jose"),
            ("li"),
            ("luisa")
        ).toDF("name")

        val actualDF = sourceDF.select(col("name").alias("student"))

        val expectedDF = Seq(
            ("jose"),
            ("li"),
            ("luisa")
        ).toDF("student")

        assertSmallDatasetEquality(actualDF, expectedDF)

    }
}