package sms;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Martin on 23/01/2016.
 */

public class EnvSettings {
    private String apiKey;
    private String apiUsername;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private int dbPort;

    private EnvSettings() {
        try {
            String path = System.getProperty("user.dir");
            File file = new File(path + System.getProperty("file.separator") + "config.conf");
            Properties props = new Properties();
            props.load(new FileReader(file));
            String _apiKey = props.getProperty("api-key");
            String _apiUseranme = props.getProperty("api-username");
            String _dbName = props.getProperty("db-name");
            String _dbUsername = props.getProperty("db-username");
            String _dbPassword = props.getProperty("db-password");
            String _dbPort = props.getProperty("db-port");

            if (_apiKey != null && !_apiKey.isEmpty()) {
                setApiKey(_apiKey);
            } else {
                throw new Exception("API Key is required");
            }
            if (_apiUseranme != null && !_apiUseranme.isEmpty()) {
                setApiUsername(_apiUseranme);
            } else {
                throw new Exception("API Username is required");
            }
            if (_dbName != null && !_dbName.isEmpty()) {
                setDbName(_dbName);
            } else {
                throw new Exception("DB name is required");
            }
            if (_dbPassword != null && !_dbPassword.isEmpty()) {
                setDbPassword(_apiKey);
            } else {
                throw new Exception("DB password is required");
            }
            if (_dbUsername != null && !_dbUsername.isEmpty()) {
                setDbUsername(_dbUsername);
            } else {
                throw new Exception("DB username is required");
            }
            if (_dbPort != null && !_dbPort.isEmpty()) {
                setDbPort(Integer.parseInt(_dbPort));
            } else {
                throw new Exception("DB port is required");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        private static final EnvSettings instance = new EnvSettings();
    }

    public static EnvSettings getInstance() {
        return LazyHolder.instance;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public void setApiUsername(String apiUsername) {
        this.apiUsername = apiUsername;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }
}
