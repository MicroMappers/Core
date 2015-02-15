package qa.qcri.mm.trainer.pybossa.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.Fusiontables.Query.Sql;
import com.google.api.services.fusiontables.FusiontablesScopes;
import com.google.api.services.fusiontables.model.Column;
import com.google.api.services.fusiontables.model.Table;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/1/14
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class FusionTablesOps {

    private static final String APPLICATION_NAME = "MicroMappers";
    private static final String SERVICE_ACCOUNT_EMAIL = "jikimlucas@gmail.com";


    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/fusion_tables_sample");

    private static FileDataStoreFactory dataStoreFactory;


    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Fusiontables fusiontables;
    // required for only private tables
    private static Credential authorizeBasedOnIP() throws Exception {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountScopes(Collections.singleton(FusiontablesScopes.FUSIONTABLES))
                .setServiceAccountPrivateKeyFromP12File(new File("/Users/jlucas/Downloads/MicroMappers-78d95b5367a5.p12"))
                .build();

        //new InputStreamReader(FusionTablesOps.class.getResourceAsStream("MicroMappers-78d95b5367a5.p12"));

        return credential;
    }

    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(
                FusionTablesOps.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=fusiontables "
                            + "into fusiontables-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(
                dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }


    public static void main(String[] args) {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();


            Credential credential = authorize();

            fusiontables = new Fusiontables.Builder(
                    httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

            String tableId = createTable();
            insertData(tableId);
            showRows(tableId);

            return;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }

    private static void showRows(String tableId) throws IOException {
         Sql sql = fusiontables.query().sql("SELECT Text,Number,Location,Date FROM " + tableId);

        try {
            sql.execute();
        } catch (IllegalArgumentException e) {
        }
    }

    private static String createTable() throws IOException {

        Table table = new Table();
        table.setName(UUID.randomUUID().toString());
        table.setIsExportable(false);
        table.setDescription("Sample1 Table");

        // Set columns for new table
        table.setColumns(Arrays.asList(new Column().setName("Text").setType("STRING"),
                new Column().setName("Number").setType("NUMBER"),
                new Column().setName("Location").setType("LOCATION"),
                new Column().setName("Date").setType("DATETIME")));

        // Adds a new column to the table.
        Fusiontables.Table.Insert t = fusiontables.table().insert(table);
        Table r = t.execute();



        return r.getTableId();
    }

    /** Inserts a row in the newly created table for the authenticated user. */
    private static void insertData(String tableId) throws IOException {
        Sql sql = fusiontables.query().sql("INSERT INTO " + tableId + " (Text,Number,Location,Date) "
                + "VALUES (" + "'Google Inc', " + "1, " + "'1600 Amphitheatre Parkway Mountain View, "
                + "CA 94043, USA','" + new DateTime(new Date()) + "')");

        try {
            sql.execute();
        } catch (IllegalArgumentException e) {

        }
    }

}
