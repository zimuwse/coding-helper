package org.muzi.open.helper.config.db;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-17 15:04
 * @description:
 */
public class DebugMysqlOperation extends MysqlOperation {
    public DebugMysqlOperation(DBConfig config) {
        super(config);
    }

    @Override
    public Connection connect() throws Exception {
        return null;
    }

    @Override
    public List<Table> tables(String database) {
        List<Table> list = new ArrayList<>(1);
        for (int i = 0; i < 1; i++)
            list.add(new Table("order", "订单"));
        return list;
    }

    @Override
    public List<TableIndex> indexes(String table) {
        List<TableIndex> indices = new ArrayList<>();
        TableIndex i1 = new TableIndex("PRIMARY", true, "id");
        TableIndex i2 = new TableIndex("k_loan_no", true, "loan_no");
        TableIndex i3 = new TableIndex("k_status", false, "status");
        TableIndex i4 = new TableIndex("k_ins", true, Arrays.asList("ins_id", "sn"));
        TableIndex i5 = new TableIndex("k_push_date", false, "push_date");
        TableIndex i6 = new TableIndex("k_lend_date", false, "lend_date");
        indices.add(i1);
        indices.add(i2);
        indices.add(i3);
        indices.add(i4);
        indices.add(i5);
        indices.add(i6);
        return indices;
    }

    @Override
    public List<TableField> fields(String table) {
        List<TableField> fields = new ArrayList<>();
        String s = "id\tint(11) unsigned\tNO\t\tID\n" +
                "ins_id\tint(11) unsigned\tNO\t0\t渠道ID\n" +
                "sn\tvarchar(64)\tNO\t\t外部订单号\n" +
                "status\ttinyint(4) unsigned\tNO\t0\t订单状态\n" +
                "push_date\tint(8) unsigned\tNO\t0\t推送时间:yyyyMMdd\n" +
                "lend_date\tint(8) unsigned\tNO\t0\t放款日期\n" +
                "lend_time\tbigint(13)\tNO\t0\t放款时间(时间戳)\n" +
                "loan_no\tvarchar(32)\tNO\t\t内部订单号\n" +
                "uid\tint(11) unsigned\tNO\t0\t微贷UID\n" +
                "id_card\tvarchar(20)\tNO\t\t身份证号\n" +
                "name\tvarchar(40)\tNO\t\t姓名\n" +
                "apply_amount\tint(10) unsigned\tNO\t0\t申请金额(分)\n" +
                "audit_amount\tint(10) unsigned\tNO\t0\t审批金额(分)\n" +
                "lend_amount\tint(10) unsigned\tNO\t0\t放款金额/提现金额(分)\n" +
                "pid\ttinyint(4) unsigned\tNO\t0\t产品ID\n" +
                "app_id\ttinyint(4) unsigned\tNO\t10\tappId\n" +
                "audit_term\tvarchar(30)\tNO\t\t审批期限(逗号分隔多个)\n" +
                "term\ttinyint(4) unsigned\tNO\t0\t期限\n" +
                "term_type\ttinyint(1) unsigned\tNO\t0\t期限类型:0=天;1=月\n" +
                "rate_id\tint(10) unsigned\tNO\t0\t费率配置id\n" +
                "audit_interest_rate\tvarchar(100)\tNO\t\t审批利率(逗号分隔多个)\n" +
                "interest_rate\tvarchar(10)\tYES\t0\t利率\n" +
                "interest\tint(10) unsigned\tNO\t0\t利息(分)\n" +
                "audit_service_rate\tvarchar(100)\tNO\t\t审批服务费率(逗号分隔多个)\n" +
                "service_rate\tvarchar(4)\tYES\t0\t服务费率\n" +
                "service_fee\tint(10) unsigned\tNO\t0\t服务费(分)\n" +
                "bid\tint(11) unsigned\tNO\t0\t标ID\n" +
                "bid_type\ttinyint(4) unsigned\tNO\t0\t发标类型:0=存管,1=机构\n" +
                "bid_status\ttinyint(4) unsigned\tNO\t0\t标状态\n" +
                "bank_no\tvarchar(32)\tNO\t\t银行卡号\n" +
                "bank_name\tvarchar(60)\tNO\t\t银行名称\n" +
                "bank_phone\tvarchar(16)\tNO\t\t银行预留手机号\n" +
                "credit_item\tvarchar(60)\tNO\t\t订单授权项\n" +
                "ocr_id\tint(10) unsigned\tNO\t0\tocr记录id\n" +
                "verify_id\tint(10) unsigned\tNO\t0\t人脸识别记录id\n" +
                "withdraw_dead_line\tdatetime\tYES\tCURRENT_TIMESTAMP\t提现截止日期\n" +
                "create_time\tdatetime\tYES\tCURRENT_TIMESTAMP\t新增时间\n" +
                "modify_time\tdatetime\tYES\tCURRENT_TIMESTAMP\t更新时间\n";

        String[] rows = s.split("\n");
        for (String row : rows) {
            String[] line = row.split("\t");
            fields.add(new TableField(line[0], line[1], "YES".equalsIgnoreCase(line[2]), line[4], line[3]));
        }
        return fields;
    }
}
