package org.muzi.open.helper.ui;

import org.muzi.open.helper.config.db.DBOperation;
import org.muzi.open.helper.config.db.DBTypeConfig;
import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.java.*;
import org.muzi.open.helper.service.tplengine.velocity.VelocityTplEngine;
import org.muzi.open.helper.util.PopUtil;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-24 15:56
 * @description:
 */
public class TableToJavaPreview extends BaseUI {
    private static final long serialVersionUID = 1634339135473477693L;
    private JPanel panel;
    private JTextArea txtBean;
    private JScrollPane jspBean;
    private JTextArea txtMapper;
    private JTextArea txtXml;
    private JScrollPane jspMapper;
    private JScrollPane jspXml;
    private JButton btnCopyBean;
    private JButton btnCopyMapper;
    private JButton btnCopyXml;

    private TableToJavaPreference preference;

    @Override
    protected JPanel panel() {
        return panel;
    }

    public TableToJavaPreview(TableToJavaPreference preference) {
        this.preference = preference;
    }

    @Override
    protected void initUI() {
        super.initUI();
        reset(jspBean);
        reset(jspMapper);
        reset(jspXml);
        velocity();
    }

    @Override
    protected void initBind() {
        bindCopy(btnCopyBean, txtBean);
        bindCopy(btnCopyMapper, txtMapper);
        bindCopy(btnCopyXml, txtXml);
    }

    private void reset(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setValue(0);
        scrollPane.updateUI();
    }

    private void velocity() {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            DBOperation operation = DBTypeConfig.getInstance(preference);
            operation.connect();
            Table table = operation.table(preference.getDbName(), preference.getPreviewTable());
            List<TableField> fields = operation.fields(preference.getPreviewTable());
            List<TableIndex> indices = operation.indexes(preference.getPreviewTable());
            List<JavaField> javaFields = operation.toJavaFields(fields);
            JavaBean javaBean = new JavaBean(table, preference.getBeanPackage(), preference.getTablePrefix(), preference.getBeanSuffix(), javaFields, preference.getAuthor(), time);
            javaBean.setUseLombok(preference.isLombok());
            if (preference.isLombok()) {
                javaBean.getImports().add("lombok.Data");
            }
            JavaMapper javaMapper = new JavaMapper(javaBean, preference.getMapperPackage(), preference.getAuthor(), time, preference.getTablePrefix(), preference.getMapperSuffix(), indices);
            JavaXml javaXml = new JavaXml(javaMapper);
            VelocityTplEngine vte = new VelocityTplEngine();
            txtBean.setText(vte.render("/templates/vm/JavaBean.vm", javaBean));
            txtMapper.setText(vte.render("/templates/vm/MybatisMapper.vm", javaMapper));
            txtXml.setText(vte.render("/templates/vm/MybatisXml.vm", javaXml));
        } catch (Exception e) {
            copy(PopUtil.getStackErrMsg(e, 10));
            PopUtil.err(panel, e);
            //txtBean.setText(PopUtil.getStackErrMsg(e, 10));
        }

    }
}
