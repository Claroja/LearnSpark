package sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSqlJoin2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkSqlJoinDemo")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    //import org.apache.spark.sql.functions._
    //spark.sql.autoBroadcastJoinThreshold=-1
    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)
    //spark.conf.set("spark.sql.join.preferSortMergeJoin", true)
    //println(spark.conf.get("spark.sql.autoBroadcastJoinThreshold"))

    val df1 = Seq(
      (0, "playing"),
      (1, "with"),
      (2, "join")
    ).toDF("id", "token")

    val df2 = Seq(
      (0, "P"),
      (1, "W"),
      (2, "S")
    ).toDF("aid", "atoken")

    df2.repartition()

    //df1.cache().count()

    val result: DataFrame = df1.join(df2, $"id" === $"aid")
    result.explain()//查看执行计划
    result.show()
    spark.stop()
  }
}
