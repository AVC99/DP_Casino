package persistence;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerConfigurationDAO {
    private static final String SERVER_CONFIGURATION_JSON = "./configuration/server_configuration.json";
    private String ip;
    private Integer port;
    private String databaseURL;
    private String databaseUsername;
    private String databasePassword;

    public ServerConfigurationDAO() {
        this.readServerConfigurationJSON();
    }

    private void readServerConfigurationJSON() {
        try {
            String content = new String(Files.readAllBytes(Path.of(SERVER_CONFIGURATION_JSON)));

            JsonObject configuration = JsonParser.parseString(content).getAsJsonObject();

            this.ip = configuration.get("ip").getAsString();
            this.port = configuration.get("port").getAsInt();
            this.databaseURL = configuration.get("databaseURL").getAsString();
            this.databaseUsername = configuration.get("databaseUsername").getAsString();
            this.databasePassword = configuration.get("databasePassword").getAsString();
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read the server's configuration file.");
        }
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
