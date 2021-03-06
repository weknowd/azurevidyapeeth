//StringOperations: Group the internet address(1), Date and corresponding count based on internet address from input 'NASA_Access_Log'
val input = sc.textFile("D:\\GIT\\azurevidyapeeth\\Nov20-Spark_Deployment_Types\\Data\\NASA_Access_Log")
	
val regex = "- ".r
	
val result = input.map(line => regex.replaceAllIn(line,""))

def getDate(line:String) : String = {
var date = line.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)+(?![^\\[]*\\])")(1);
date = date.replace("[", "").split(":")(0);
date = date.replace("/", "-");
return date
 }
	
val group = result.map(line => (line.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)+(?![^\\[]*\\])")(0) + "," + getDate(line),1))
val groupPairs = group.reduceByKey((a, b) => a + b)
groupPairs.collect().take(2).foreach(println)

val csvForm = groupPairs.collect().map(m => m.toString().tail.init)
csvForm.take(2).foreach(println)
sc.parallelize(csvForm).saveAsTextFile("D:\\GIT\\azurevidyapeeth\\Nov20-Spark_Deployment_Types\\Data\\NASA_Access_Log_CsvForm")