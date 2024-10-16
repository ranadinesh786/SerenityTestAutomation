package com.dellpoc.stepdefinitions;

import com.dellpoc.utils.CommonUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import net.serenitybdd.core.Serenity;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.junit.Assert.assertTrue;

public class ETLStepDefinitions {

    private SparkSession spark;
    private Dataset<Row> sourceData;
    private Dataset<Row> targetData;
    private Dataset<Row> transformedData;

    @Given("the source data is loaded")
    public void loadSourceData() {
        spark = SparkSession.builder()
                .appName("ETL Testing")
                .master("local")
                .getOrCreate();
        sourceData = spark.read().format("csv").option("header", "true").load("path/to/source.csv");
        Serenity.recordReportData().withTitle("Source Data").andContents(sourceData.showString(10, 0, false));
    }

    @When("the ETL job is executed")
    public void executeETLJob() {
        transformedData = sourceData.filter("some condition");
        transformedData.write().format("csv").option("header", "true").save("path/to/transformed.csv");
        Serenity.recordReportData().withTitle("Transformed Data").andContents(transformedData.showString(10, 0, false));
    }

    @Then("the target data should match the expected results")
    public void validateTargetData() {
        targetData = spark.read().format("csv").option("header", "true").load("path/to/target.csv");
        boolean isValid = transformedData.except(targetData).isEmpty();
        Serenity.recordReportData().withTitle("Target Data Validation").andContents("Validation Result: " + isValid);
        assertTrue(isValid);
    }

    @And("the Spark job is run on the server with command {string} on host {string} with user {string} and password {string}")
    public void runSparkJobOnServer(String command, String host, String user, String password) {
        CommonUtils.runSparkJobOnLinux(command, host, user, password);
    }
}
