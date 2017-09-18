package com.github.chamainekruger.gooface.subscriber.bigdata;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.TableId;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import lombok.extern.java.Log;

/**
 * BigData Subscriber
 *
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Log
public class BigQueryPersist {

    private static final String ACCOUNT_ID = "account.id";
    private static final String PROJECT_ID = "project.id";
    private static final String DATASET_ID = "dataset.id";
    private static final String TABLE_ID = "table.id";

    
    private void createBigQueryEntry(String leadId, String firstName, String lastName, String phoneNumber, String email, String source, String medium, String campaign, String product) {
        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
        String projectId = System.getProperty(PROJECT_ID);
        String accountId = System.getProperty(ACCOUNT_ID);
        String datasetId = System.getProperty(DATASET_ID);
        String tableId = System.getProperty(TABLE_ID);

        long currentDateTime = System.currentTimeMillis();
        Date currentDate = new Date(currentDateTime);
        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");

        TableId table = TableId.of(datasetId, tableId);
        
        Map<String, Object> row = new HashMap<>();
        
        row.put("insertId", df.format(currentDate));
        row.put("lead_id", leadId);
        row.put("first_name", firstName);
        row.put("last_name", lastName);
        row.put("phone_number", phoneNumber);
        row.put("email", email);
        row.put("source", source);
        row.put("medium", medium);
        row.put("campaign", campaign);
        row.put("product", product);
        
        // Create an insert request
        InsertAllRequest insertRequest
                = InsertAllRequest.newBuilder(table).addRow(row).build();
        
        // Insert rows
        InsertAllResponse insertResponse = bigquery.insertAll(insertRequest);
        log.log(Level.INFO, "-------------insertResponse {0}", insertResponse.toString());
        // Check if errors occurred
        if (insertResponse.hasErrors()) {
            log.severe("Errors occurred while inserting rows");
            
        }

    }

}
