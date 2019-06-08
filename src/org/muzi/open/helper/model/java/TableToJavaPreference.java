package org.muzi.open.helper.model.java;

import org.muzi.open.helper.config.PersistableConfig;
import org.muzi.open.helper.config.db.DBConfig;
import org.muzi.open.helper.util.JavaUtil;

/**
 * @author: muzi
 * @time: 2018-05-24 10:14
 * @description:
 */
@PersistableConfig
public class TableToJavaPreference extends DBConfig {
    private String author;
    private String tablePrefix;
    private String beanSuffix;
    private String beanLocation;
    private boolean beanGen;
    private String mapperSuffix;
    private String mapperLocation;
    private boolean mapperGen;
    private String xmlLocation;
    private boolean xmlGen;
    private String previewTable;
    private boolean overwrite;
    private boolean lombok;
    @PersistableConfig(skip = true)
    private String[] tables;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getBeanSuffix() {
        return beanSuffix;
    }

    public void setBeanSuffix(String beanSuffix) {
        this.beanSuffix = beanSuffix;
    }

    public String getBeanLocation() {
        return beanLocation;
    }

    public void setBeanLocation(String beanLocation) {
        this.beanLocation = beanLocation;
    }

    public boolean isBeanGen() {
        return beanGen;
    }

    public void setBeanGen(boolean beanGen) {
        this.beanGen = beanGen;
    }

    public String getMapperSuffix() {
        return mapperSuffix;
    }

    public void setMapperSuffix(String mapperSuffix) {
        this.mapperSuffix = mapperSuffix;
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public boolean isMapperGen() {
        return mapperGen;
    }

    public void setMapperGen(boolean mapperGen) {
        this.mapperGen = mapperGen;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public boolean isXmlGen() {
        return xmlGen;
    }

    public void setXmlGen(boolean xmlGen) {
        this.xmlGen = xmlGen;
    }

    public String getPreviewTable() {
        return previewTable;
    }

    public void setPreviewTable(String previewTable) {
        this.previewTable = previewTable;
    }

    public String getBeanPackage() {
        return JavaUtil.parsePackage(beanLocation);
    }

    public String getMapperPackage() {
        return JavaUtil.parsePackage(mapperLocation);
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean isLombok() {
        return lombok;
    }

    public void setLombok(boolean lombok) {
        this.lombok = lombok;
    }
}
