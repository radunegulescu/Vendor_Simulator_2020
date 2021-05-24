package project.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class AuditService {
    private static AuditService single_instance = null;
    private AuditService() throws IOException {
        File csvFile = new File("src/project/audits/audit.csv");
        if (csvFile.isFile()) {
            FileWriter csvWriter = new FileWriter("src/project/audits/audit.csv", false);
            csvWriter.append("");
        }
    }
    public static AuditService getInstance() throws IOException {
        if (single_instance == null)
            single_instance = new AuditService();
        return single_instance;
    }


    public void write(String action_name) throws IOException {
        File csvFile = new File("src/project/audits/audit.csv");
        if (csvFile.isFile()) {
            FileWriter csvWriter = new FileWriter("src/project/audits/audit.csv", true);
            if (csvFile.length() == 0){
                csvWriter.append("Action Name");
                csvWriter.append(",");
                csvWriter.append("TimeStamp");
                csvWriter.append("\n");
            }
            csvWriter.append(action_name);
            csvWriter.append(",");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            csvWriter.append(timestamp.toString());
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        }
    }
}
