package org.muzi.open.helper.ui;

import com.intellij.openapi.project.Project;
import org.muzi.open.helper.config.FileType;
import org.muzi.open.helper.config.TableToJavaConf;
import org.muzi.open.helper.config.db.DBConfig;
import org.muzi.open.helper.config.db.DBOperation;
import org.muzi.open.helper.config.db.DBTypeConfig;
import org.muzi.open.helper.model.KV;
import org.muzi.open.helper.model.PopException;
import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.db.TableMethod;
import org.muzi.open.helper.model.java.*;
import org.muzi.open.helper.service.tplengine.velocity.VelocityTplEngine;
import org.muzi.open.helper.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: muzi
 * @time: 2018-05-21 17:05
 * @description:
 */
public class TableToJava extends BaseUI implements UIResult<Map<String, Set<TableMethod>>>, UIMapConfig<String, String> {
    private static final long serialVersionUID = 3116988251961730584L;
    private JPanel panel;
    private JTextField txtJar;
    private JTextField txtHost;
    private JComboBox cbDBType;
    private JTextField txtPort;
    private JTextField txtUser;
    private JTextField txtPwd;
    private JTextField txtDB;
    private JButton btnTestConnection;
    private JTextField txtTablePrefix;
    private JTextField txtAuthor;
    private JTextField txtJavaBeanSuffix;
    private JTextField txtJavaBeanLocation;
    private JTextField txtMapperSuffix;
    private JTextField txtMapperLocation;
    private JTextField txtXmlLocation;
    private JCheckBox jcbGenBean;
    private JCheckBox jcbGenMapper;
    private JCheckBox jcbGenXml;
    private JTextField txtPreviewTable;
    private JButton btnPreview;
    private JTextArea txtGenTables;
    private JButton btnSavePreference;
    private JButton btnGenerate;
    private JButton btnCancel;
    private JCheckBox jcbOverwrite;
    private JCheckBox jcbUseLombok;
    private JTextField txtSpringServiceLocation;
    private JCheckBox springServiceLocationCheckBox;
    private JCheckBox springControllerCheckBox;
    private JTextField txtSpringControllerLocation;
    private JButton btnMethodNamePrefixSetting;
    private JTextField txtSpringServiceSuffix;
    private JTextField txtSpringControllerSuffix;
    private JButton btnChooseTables;

    private Map<String, List<TableMethod>> methodsConfigMap = new HashMap<>();

    private Map<String, String> methodNameConfig = new HashMap<>();

    public TableToJava() throws HeadlessException {
    }

    public TableToJava(Project project) throws HeadlessException {
        super(project);
    }

    @Override
    protected JPanel panel() {
        return panel;
    }

    @Override
    protected void initUI() {
        super.initUI();
        setTitle("Generate Files From Database Tables");
        loadPreference();
    }

    @Override
    protected void initBind() {
        super.initBind();
        bindDBType();
        bindFileBrowser(txtJar, "Driver Jar", "Choose your driver jar location", FileType.JAR);
        bindFileBrowser(txtJavaBeanLocation, "Java Bean Location", "Choose your java bean location", FileType.FOLDER);
        bindFileBrowser(txtMapperLocation, "Mybatis Mapper Location", "Choose your mybatis mapper location", FileType.FOLDER);
        bindFileBrowser(txtXmlLocation, "Mybatis XML Location", "Choose your mybatis XML location", FileType.FOLDER);
        bindFileBrowser(txtSpringServiceLocation, "Spring Service Bean Location", "Choose your spring service bean location", FileType.FOLDER);
        bindFileBrowser(txtSpringControllerLocation, "Spring Controller Bean Location", "Choose your spring controller bean location", FileType.FOLDER);
        //bindTxtClickFileDialog(txtJar, this, "please choose driver jar location", CmdUtil.getUserHome(), false, "jar");
        //bindTxtClickFileDialog(txtJavaBeanLocation, this, "please choose java bean location", null, true, null);
        //bindTxtClickFileDialog(txtMapperLocation, this, "please choose mybatis mapper location", null, true, null);
        //bindTxtClickFileDialog(txtXmlLocation, this, "please choose mybatis xml location", null, true, null);
        bindTestConnection();
        bindCancel(btnCancel, this);
        bindSavePreference();
        bindPreview();
        bindSelectTables();
        bindGenerate();
    }


    private void bindGenerate() {
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    TableToJavaPreference preference = getPreference(OperatorType.GENERATE);
                    new Thread(new GenerateTask(preference)).start();
                } catch (Exception e) {
                    PopUtil.pop(e);
                }
            }
        });
    }


    private void bindSelectTables() {
        btnMethodNamePrefixSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MethodPrefixConfig(TableToJava.this).show(400, 320);
                } catch (HeadlessException e1) {
                    PopUtil.err(btnMethodNamePrefixSetting, e1);
                }
            }
        });
        btnChooseTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new DatabaseTableList(getPreference(OperatorType.PREVIEW), TableToJava.this).show(800, 600);
                } catch (HeadlessException e1) {
                    PopUtil.err(txtGenTables, e1);
                }
            }
        });
        /*txtGenTables.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //new MultiSelectCheckBox<>(self).show(400, 400);
                    try {
                        new DatabaseTableList(getPreference(OperatorType.PREVIEW), TableToJava.this).show(800, 600);
                    } catch (HeadlessException e1) {
                        PopUtil.err(txtGenTables, e1);
                    }
                }
            }
        });*/
    }

    private void bindPreview() {
        btnPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    new TableToJavaPreview(getPreference(OperatorType.PREVIEW)).show(1200, 750);
                } catch (Exception e) {
                    PopUtil.pop(e);
                }
            }
        });
    }

    private void bindSavePreference() {
        btnSavePreference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    new TableToJavaConf().saveTableToJavaPreference(getPreference(OperatorType.SAVE_PREFERENCE));
                    PopUtil.info("Saved");
                } catch (Exception e) {
                    PopUtil.pop(e);
                }
            }
        });
    }

    private void bindDBType() {
        List<KV> list = Collections.singletonList(new KV() {
            @Override
            public String key() {
                return "MYSQL";
            }

            @Override
            public String val() {
                return key();
            }
        });
        bindSelect(cbDBType, list, false);
    }

    private void bindTestConnection() {
        btnTestConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    DBConfig config = getPreference(OperatorType.TEST_CONNECTION);
                    testConnection(config);
                    PopUtil.info(btnTestConnection, "Connect Success");
                } catch (Exception e) {
                    PopUtil.pop(e);
                }
            }
        });
    }


    private void loadPreference() {
        TableToJavaPreference preference = new TableToJavaConf().getTableToJavaPreference();
        txtJar.setText(preference.getDriverJar());
        txtHost.setText(preference.getHost());
        txtPort.setText(preference.getPort());
        txtUser.setText(preference.getUser());
        txtPwd.setText(preference.getPwd());
        txtJavaBeanSuffix.setText(preference.getBeanSuffix());
        txtJavaBeanLocation.setText(preference.getBeanLocation());
        jcbGenBean.setSelected(preference.isBeanGen());
        txtMapperSuffix.setText(preference.getMapperSuffix());
        txtMapperLocation.setText(preference.getMapperLocation());
        jcbGenMapper.setSelected(preference.isMapperGen());
        txtXmlLocation.setText(preference.getXmlLocation());
        jcbGenXml.setSelected(preference.isXmlGen());
        txtPreviewTable.setText(preference.getPreviewTable());
        txtDB.setText(preference.getDbName());
        txtAuthor.setText(preference.getAuthor());
        if (StringUtil.isEmpty(preference.getAuthor()))
            txtAuthor.setText(CmdUtil.getUserName());
        txtTablePrefix.setText(preference.getTablePrefix());
        jcbOverwrite.setSelected(preference.isOverwrite());
        //method prefix
        /*txtDelMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.DELETE));
        txtInsertMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.INSERT));
        txtInsertBatchMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.INSERT_BATCH));
        txtPageQueryMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.PAGE_QUERY));
        txtUpdateMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.UPDATE));
        txtSelectOneMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.SELECT_ONE));
        txtSelectListMethodPrefix.setText(JavaMethodUtil.getMethodPrefix(preference, JavaMethodType.SELECT_LIST));*/
        //spring preference
        txtSpringControllerSuffix.setText(StringUtil.isEmpty(preference.getSpringControllerSuffix()) ? "Controller" : preference.getSpringControllerSuffix());
        txtSpringServiceSuffix.setText(StringUtil.isEmpty(preference.getSpringServiceSuffix()) ? "Service" : preference.getSpringServiceSuffix());
        txtSpringControllerLocation.setText(preference.getSpringControllerLocation());
        txtSpringServiceLocation.setText(preference.getSpringServiceLocation());
        //load java method name config
        for (JavaMethodType javaMethodType : JavaMethodType.values()) {
            this.methodNameConfig.put(javaMethodType.name(), JavaMethodUtil.getMethodPrefix(preference, javaMethodType));
        }

    }

    private TableToJavaPreference getPreference(int operation) {
        boolean isPreview = OperatorType.isPreview(operation);
        boolean isGenerate = OperatorType.isGenerate(operation);
        boolean isSavePreference = OperatorType.isSavePreference(operation);

        String jar = txtJar.getText();
        String host = txtHost.getText();
        String port = txtPort.getText();
        String user = txtUser.getText();
        String pwd = txtPwd.getText();
        String db = txtDB.getText();
        String previewTable = txtPreviewTable.getText();
        String beanSuffix = txtJavaBeanSuffix.getText();
        String beanLocation = txtJavaBeanLocation.getText();
        String mapperSuffix = txtMapperSuffix.getText();
        String mapperLocation = txtMapperLocation.getText();
        String xmlLocation = txtXmlLocation.getText();

        if (!isSavePreference) {
            if (StringUtil.isEmpty(jar))
                throw new PopException(txtJar, "driver jar can't be empty");
            if (StringUtil.isEmpty(host))
                throw new PopException(txtHost, "host can't be empty");
            if (!NetUtil.ping(host))
                throw new PopException(txtHost, "ping host can't receive any response");
            if (!NetUtil.isPort(port))
                throw new PopException(txtPort, "port is not illegal");
            if (StringUtil.isEmpty(user))
                throw new PopException(txtUser, "user can't be empty");
            if (StringUtil.isEmpty(pwd))
                throw new PopException(txtPwd, "pwd can't be empty");
            if (StringUtil.isEmpty(db))
                throw new PopException(txtDB, "database can't be empty");
        }
        if (isPreview && StringUtil.isEmpty(previewTable))
            throw new PopException(txtPreviewTable, "preview table can't be empty");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(beanSuffix))
            throw new PopException(txtJavaBeanSuffix, "bean suffix can't be empty");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(mapperSuffix))
            throw new PopException(txtMapperSuffix, "mapper suffix can't be empty");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(beanLocation))
            throw new PopException(txtJavaBeanLocation, "bean location can't be empty");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(JavaUtil.parsePackage(beanLocation)))
            throw new PopException(txtJavaBeanLocation, "can't parse bean package from bean location");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(mapperLocation))
            throw new PopException(txtMapperLocation, "mapper location can't be empty");

        if ((isGenerate || isPreview) && StringUtil.isEmpty(JavaUtil.parsePackage(mapperLocation)))
            throw new PopException(txtJavaBeanLocation, "can't parse mapper package from mapper location");

        if (isGenerate && StringUtil.isEmpty(xmlLocation))
            throw new PopException(txtXmlLocation, "xml location can't be empty");

        boolean beanGen = jcbGenBean.isSelected();
        boolean mapperGen = jcbGenMapper.isSelected();
        boolean xmlGen = jcbGenXml.isSelected();
        boolean springService = springServiceLocationCheckBox.isSelected();
        boolean springController = springControllerCheckBox.isSelected();
        boolean gen = beanGen || mapperGen || xmlGen;
        if (isGenerate && !gen)
            throw new PopException(jcbGenBean, "must choose at least one type to generate");

        String tables = txtGenTables.getText();
        if (isGenerate && (null == tables || tables.trim().length() == 0)) {
            throw new PopException(txtGenTables, "must choose at least one table to generate");
        }
        if (springService && StringUtil.isEmpty(JavaUtil.parsePackage(txtSpringServiceLocation.getText())))
            throw new PopException(txtGenTables, "must choose spring service bean location");
        if (springController && StringUtil.isEmpty(JavaUtil.parsePackage(txtSpringControllerLocation.getText())))
            throw new PopException(txtGenTables, "must choose spring controller bean location");

        TableToJavaPreference config = new TableToJavaPreference();
        config.setDriverJar(jar);
        config.setDriverType(cbDBType.getSelectedItem().toString());
        config.setHost(host);
        if (!isSavePreference) {
            config.setPort(port);
        }
        config.setPwd(pwd);
        config.setDbName(db);
        config.setUser(user);
        config.setPreviewTable(previewTable);
        config.setTablePrefix(txtTablePrefix.getText());
        config.setAuthor(txtAuthor.getText());
        config.setBeanSuffix(beanSuffix);
        config.setBeanLocation(beanLocation);
        config.setBeanGen(beanGen);
        config.setMapperSuffix(mapperSuffix);
        config.setMapperLocation(mapperLocation);
        config.setMapperGen(mapperGen);
        config.setXmlLocation(xmlLocation);
        config.setXmlGen(xmlGen);
        config.setOverwrite(jcbOverwrite.isSelected());
        config.setLombok(jcbUseLombok.isSelected());
        config.setTables(tables.split(","));
        //method prefix
        config.setMethodInsert(getMethodNameConfig(JavaMethodType.INSERT));
        config.setMethodInsertBatch(getMethodNameConfig(JavaMethodType.INSERT_BATCH));
        config.setMethodQueryList(getMethodNameConfig(JavaMethodType.SELECT_LIST));
        config.setMethodQueryOne(getMethodNameConfig(JavaMethodType.SELECT_ONE));
        config.setMethodPageQuery(getMethodNameConfig(JavaMethodType.PAGE_QUERY));
        config.setMethodDelete(getMethodNameConfig(JavaMethodType.DELETE));
        config.setMethodUpdate(getMethodNameConfig(JavaMethodType.UPDATE));
        //spring
        config.setSpringController(springController);
        config.setSpringService(springService);
        config.setSpringServiceSuffix(txtSpringServiceSuffix.getText());
        config.setSpringControllerSuffix(txtSpringControllerSuffix.getText());
        config.setSpringServiceLocation(txtSpringServiceLocation.getText());
        config.setSpringControllerLocation(txtSpringControllerLocation.getText());
        return config;
    }

    private String getMethodNameConfig(JavaMethodType type) {
        return this.methodNameConfig.get(type.name());
    }

    private void testConnection(DBConfig config) throws Exception {
        try {
            DBOperation operation = DBTypeConfig.getInstance(config);
            operation.connect();
            operation.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static class OperatorType {
        static int TEST_CONNECTION = 1;
        static int PREVIEW = 2;
        static int GENERATE = 4;
        static int SAVE_PREFERENCE = 8;

        static boolean isTestConnection(int operatorType) {
            return (operatorType & TEST_CONNECTION) == TEST_CONNECTION;
        }

        static boolean isPreview(int operatorType) {
            return (operatorType & PREVIEW) == PREVIEW;
        }

        static boolean isGenerate(int operatorType) {
            return (operatorType & GENERATE) == GENERATE;
        }

        static boolean isSavePreference(int operatorType) {
            return (operatorType & SAVE_PREFERENCE) == SAVE_PREFERENCE;
        }
    }

    @Override
    public void setUIResult(Map<String, Set<TableMethod>> result) {
        Set<String> keys = result.keySet();
        txtGenTables.setText(StringUtil.join(keys.toArray(new String[0]), ","));
        System.out.println(StringUtil.toJson(result));
    }

    @Override
    public void onUIMapConfigFinish(Map<String, String> map) {
        this.methodNameConfig.putAll(map);
    }

    @Override
    public Map<String, String> getDefaultConfig() {
        return this.methodNameConfig;
    }

    /*

    @Override
    public String getMultiSelectTitle() {
        return "please choose tables to generate files";
    }

    @Override
    public List<Table> getMultiSelectOptions() {
        try {
            TableToJavaPreference preference = getPreference(OperatorType.TEST_CONNECTION);
            DBOperation operation = DBTypeConfig.getInstance(preference);
            operation.connect();
            List<Table> list = operation.tables(preference.getDbName());
            operation.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMultiSelectResult(List<Table> list) {
        StringBuilder builder = new StringBuilder();
        for (Table table : list) {
            builder.append(table.getName()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        txtGenTables.setText(builder.toString());
    }

    @Override
    public String getMultiSelectKey(Table table) {
        return table.getName();
    }

    @Override
    public String getMultiSelectValue(Table table) {
        return table.getComment();
    }

    @Override
    public Table build(JCheckBox checkBox) {
        return new Table(checkBox.getText(), checkBox.getToolTipText());
    }

    @Override
    public Map<String, List<TableMethod>> getResultMap() {
        return this.methodsConfigMap;
    }*/

    class GenerateTask implements Runnable {
        private TableToJavaPreference preference;

        private DBOperation operation;

        private Progress progress;

        private VelocityTplEngine engine;

        public GenerateTask(TableToJavaPreference preference) throws Exception {
            this.preference = preference;
            this.operation = DBTypeConfig.getInstance(preference);
            this.progress = new Progress("FILE GENERATING TASK ON DB[" + preference.getDbName() + "] IS RUNNING...");
            engine = new VelocityTplEngine();
        }


        @Override
        public void run() {
            try {
                progress.show(800, 400);
                operation.connect();
                List<Table> tableList = null;
                if (null == preference.getTables()) {
                    tableList = operation.tables(preference.getDbName());
                } else {
                    tableList = new ArrayList<>();
                    for (String tb : preference.getTables()) {
                        if (!StringUtil.isEmpty(tb)) {
                            Table table = operation.table(preference.getDbName(), tb);
                            if (null != table)
                                tableList.add(table);
                        }
                    }
                }
                if (null == tableList || tableList.isEmpty()) {
                    progress.append("[PROCESS-ERR:NO SUITABLE TABLES FOUND IN DATABASE " + preference.getDbName());
                    operation.close();
                    return;
                }
                int total = tableList.size();
                BigDecimal totalValue = new BigDecimal(total);
                int threadCount = Runtime.getRuntime().availableProcessors();
                final Queue<Table> queue = new LinkedBlockingQueue<>(tableList);
                final String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                final long start = System.currentTimeMillis();
                final AtomicInteger counter = new AtomicInteger(total);
                for (int i = 0; i < threadCount; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Table t = null;
                            while ((t = queue.poll()) != null) {
                                try {
                                    process(t, preference.isBeanGen(), preference.isMapperGen(), preference.isXmlGen(), preference.isOverwrite(), time);
                                } catch (Exception e) {
                                    progress.append(e.toString());
                                }
                                counter.decrementAndGet();
                            }

                        }
                    }).start();
                }
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (0 == counter.get()) {
                            progress.updateProgress(100, "100.00%");
                            operation.close();
                            progress.finish(System.currentTimeMillis() - start);
                            progress.changeTitle("FILE GENERATING TASK ON DB[" + preference.getDbName() + "] HAS BEEN FINISHED");
                            timer.cancel();
                        } else {
                            int remain = queue.size();
                            float per = new java.math.BigDecimal((total - remain) * 100).divide(totalValue, 2, BigDecimal.ROUND_DOWN).floatValue();
                            progress.updateProgress((int) per, per + "%");
                        }
                    }
                }, 0, 5);
            } catch (Exception e) {
                PopUtil.err(txtGenTables, e);
            }
        }

        /**
         * @param table
         * @param bean
         * @param mapper
         * @param xml
         * @param overwrite
         * @param time
         * @throws Exception
         */
        private void process(Table table, boolean bean, boolean mapper, boolean xml, boolean overwrite, String time) throws Exception {
            progress.append("[PROCESS-START:" + table.getName() + "(" + table.getComment() + ")]");
            List<TableField> fields = operation.fields(table.getName());
            List<JavaField> javaFields = operation.toJavaFields(fields);
            List<TableIndex> indices = operation.indexes(table.getName());
            JavaBean javaBean = new JavaBean(table, preference.getBeanPackage(), preference.getTablePrefix(), preference.getBeanSuffix(), javaFields, preference.getAuthor(), time);
            javaBean.setUseLombok(preference.isLombok());
            if (preference.isLombok()) {
                javaBean.getImports().add("lombok.Data");
            }
            JavaMapper javaMapper = new JavaMapper(javaBean, preference.getMapperPackage(), preference.getAuthor(), time, preference.getTablePrefix(), preference.getMapperSuffix(), indices);
            JavaXml javaXml = new JavaXml(javaMapper);
            if (bean) {
                String path = preference.getBeanLocation() + File.separator + javaBean.getClzName() + ".java";
                File file = new File(path);
                boolean gen = true;
                if (file.exists()) {
                    if (overwrite) {
                        progress.append("OVERWRITE BEAN => " + path);
                    } else {
                        gen = false;
                    }
                } else {
                    progress.append("NEW BEAN => " + path);
                }
                if (gen) {
                    engine.render("/templates/vm/JavaBean.vm", javaBean, file);
                } else {
                    progress.append("SKIP BEAN => " + path);
                }
            }
            if (mapper) {
                String path = preference.getMapperLocation() + File.separator + javaMapper.getClzName() + ".java";
                File file = new File(path);
                boolean gen = true;
                if (file.exists()) {
                    if (overwrite) {
                        progress.append("OVERWRITE MAPPER => " + path);
                    } else {
                        gen = false;
                    }
                } else {
                    progress.append("NEW MAPPER => " + path);
                }
                if (gen) {
                    engine.render("/templates/vm/MybatisMapper.vm", javaMapper, file);
                } else {
                    progress.append("SKIP MAPPER => " + path);
                }
            }
            if (xml) {
                String path = preference.getXmlLocation() + File.separator + javaMapper.getClzName() + ".xml";
                File file = new File(path);
                boolean gen = true;
                if (file.exists()) {
                    if (overwrite) {
                        progress.append("OVERWRITE XML=> " + path);
                    } else {
                        gen = false;
                    }
                } else {
                    progress.append("NEW XML => " + path);
                }
                if (gen) {
                    engine.render("/templates/vm/MybatisXml.vm", javaXml, file);
                } else {
                    progress.append("SKIP XML => " + path);
                }
            }
            progress.append("[END-PROCESS:" + table.getName() + "(" + table.getComment() + ")]");
        }
    }
}
