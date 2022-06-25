package persistence;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientConfigurationDAO {
    private static final String CLIENT_CONFIGURATION_JSON = "./configuration/client_configuration.json";
    private String serverIP;
    private Integer serverPort;

    public ClientConfigurationDAO() {
        this.readClientConfigurationJSON();
    }

    private void readClientConfigurationJSON() {
        try {
            String content = new String(Files.readAllBytes(Path.of(CLIENT_CONFIGURATION_JSON)));
            JsonObject configuration = JsonParser.parseString(content).getAsJsonObject();

            this.serverIP = configuration.get("server_ip").getAsString();
            this.serverPort = configuration.get("server_port").getAsInt();
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read the client's configuration file.");
        }
    }

    public String getServerIP() {
        return this.serverIP;
    }

    public Integer getServerPort() {
        return this.serverPort;
    }
}
