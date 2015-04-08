This is a simple wrapper implementation of [Hivemall](https://github.com/myui/hivemall/) for Spark.
This can make various state-of-the-art machine learning algorithms available in DataFrame (under development).

Installation
--------------------

```
git clone https://github.com/maropu/hivemall-spark.git

cd hivemall-spark

./bin/sbt assembly

<your spark>/bin/spark-shell --jars hivemall-spark-assembly-0.0.1.jar
```

Hivemall in DataFrame
--------------------

```
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.hive.HivemallOps._

import sqlContext.implicits._

case class TrainData(label: Float, feature: Seq[String])

val trainTable = sc.parallelize(
  TrainData(0.0f, "1:0.8" :: "2:0.2" :: Nil) ::
  TrainData(1.0f, "2:0.7" :: Nil) ::
  TrainData(0.0f, "1:0.9" :: Nil) :: Nil)

sqlContext.createDataFrame(trainTable)
  .train_logregr(add_bias($"feature"), $"label")
```

Hivemall in HiveContext
--------------------

```
:load <hivemall-spark>/scripts/define-all.sh

sqlContext.sql("SELECT train_logregr(add_bias(feature)) FROM trainTable")
```

Current Status
--------------------
The current implementation of Spark cannot correctly handle UDF/UDAF/UDTF in Hive.
Related issues for hivemall-spark are as follows;

* SPARK-6747 Support List<> as a return type in Hive UDF ([#5395](https://github.com/apache/spark/pull/5395))

* SPARK-6734 Add UDTF.close support in Generate ([#5383](https://github.com/apache/spark/pull/5383))

* SPARK-4233 WIP:Simplify the UDAF API (Interface) ([#3247](https://github.com/apache/spark/pull/3247))

System requirements
--------------------

* Spark 1.3

* Hive 0.13

TODO
--------------------

* Support the other functions of Hivemall in DataFrame

* ...