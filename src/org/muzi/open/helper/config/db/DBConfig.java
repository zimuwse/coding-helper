package org.muzi.open.helper.config.db;

import org.muzi.open.helper.config.PersistableConfig;

/**
 * @author: muzi
 * @time: 2018-05-18 13:48
 * @description:
 */
@PersistableConfig
public class DBConfig {
    private String host;
    private String dbName;
    private String user;
    private String pwd;
    private String port;
    private String driverJar;
    private String driverType;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDriverJar() {
        return driverJar;
    }

    public void setDriverJar(String driverJar) {
        this.driverJar = driverJar;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }
}