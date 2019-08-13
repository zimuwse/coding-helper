package org.muzi.open.helper.model.java;

import org.muzi.open.helper.config.PersistableConfig;
import org.muzi.open.helper.config.db.DBConfig;
import org.muzi.open.helper.util.JavaUtil;

import java.util.List;

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
    private boolean lombokData;
    private boolean lombokLog;
    @PersistableConfig(skip = true)
    private String[] tables;
    //method config from table fields.
    @PersistableConfig(skip = true)
    private List<JavaMethodConfig> javaMethodConfigs;
    private String methodPageQuery;
    private String methodQueryList;
    private String methodQueryOne;
    private String methodDelete;
    private String methodUpdate;
    private String methodInsert;
    private String methodInsertBatch;
    //page query config
    private String pqReqClz;
    private String pqResClz;
    private String pqReqSize;
    private String pqReqOffset;
    //spring
    private boolean springService;
    private boolean springController;
    private String springServiceSuffix;
    private String springControllerSuffix;
    private String springServiceLocation;
    private String springControllerLocation;
    @PersistableConfig(skip = true)
    private String createTime;

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
        return lombokData;
    }

    public void setLombok(boolean lombok) {
        this.lombokData = lombok;
    }

    public boolean isLombokLog() {
        return lombokLog;
    }

    public void setLombokLog(boolean lombokLog) {
        this.lombokLog = lombokLog;
    }

    public List<JavaMethodConfig> getJavaMethodConfigs() {
        return javaMethodConfigs;
    }

    public void setJavaMethodConfigs(List<JavaMethodConfig> javaMethodConfigs) {
        this.javaMethodConfigs = javaMethodConfigs;
    }

    public String getMethodPageQuery() {
        return methodPageQuery;
    }

    public void setMethodPageQuery(String methodPageQuery) {
        this.methodPageQuery = methodPageQuery;
    }

    public String getMethodQueryList() {
        return methodQueryList;
    }

    public void setMethodQueryList(String methodQueryList) {
        this.methodQueryList = methodQueryList;
    }

    public String getMethodQueryOne() {
        return methodQueryOne;
    }

    public void setMethodQueryOne(String methodQueryOne) {
        this.methodQueryOne = methodQueryOne;
    }

    public String getMethodDelete() {
        return methodDelete;
    }

    public void setMethodDelete(String methodDelete) {
        this.methodDelete = methodDelete;
    }

    public String getMethodUpdate() {
        return methodUpdate;
    }

    public void setMethodUpdate(String methodUpdate) {
        this.methodUpdate = methodUpdate;
    }

    public String getMethodInsert() {
        return methodInsert;
    }

    public void setMethodInsert(String methodInsert) {
        this.methodInsert = methodInsert;
    }

    public String getMethodInsertBatch() {
        return methodInsertBatch;
    }

    public void setMethodInsertBatch(String methodInsertBatch) {
        this.methodInsertBatch = methodInsertBatch;
    }

    public String getPqReqClz() {
        return pqReqClz;
    }

    public void setPqReqClz(String pqReqClz) {
        this.pqReqClz = pqReqClz;
    }

    public String getPqResClz() {
        return pqResClz;
    }

    public void setPqResClz(String pqResClz) {
        this.pqResClz = pqResClz;
    }

    public String getPqReqSize() {
        return pqReqSize;
    }

    public void setPqReqSize(String pqReqSize) {
        this.pqReqSize = pqReqSize;
    }

    public String getPqReqOffset() {
        return pqReqOffset;
    }

    public void setPqReqOffset(String pqReqOffset) {
        this.pqReqOffset = pqReqOffset;
    }

    public String getSpringServiceSuffix() {
        return springServiceSuffix;
    }

    public void setSpringServiceSuffix(String springServiceSuffix) {
        this.springServiceSuffix = springServiceSuffix;
    }

    public String getSpringControllerSuffix() {
        return springControllerSuffix;
    }

    public void setSpringControllerSuffix(String springControllerSuffix) {
        this.springControllerSuffix = springControllerSuffix;
    }

    public String getSpringServiceLocation() {
        return springServiceLocation;
    }

    public void setSpringServiceLocation(String springServiceLocation) {
        this.springServiceLocation = springServiceLocation;
    }

    public String getSpringControllerLocation() {
        return springControllerLocation;
    }

    public void setSpringControllerLocation(String springControllerLocation) {
        this.springControllerLocation = springControllerLocation;
    }

    public boolean isSpringService() {
        return springService;
    }

    public void setSpringService(boolean springService) {
        this.springService = springService;
    }

    public boolean isSpringController() {
        return springController;
    }

    public void setSpringController(boolean springController) {
        this.springController = springController;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
