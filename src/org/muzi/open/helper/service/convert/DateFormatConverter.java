package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class DateFormatConverter extends AbstractConverter {

    private boolean toTimestamp;

    public DateFormatConverter(boolean toTimestamp) {
        this.toTimestamp = toTimestamp;
    }

    @Override
    public String[] getOptionalParams() {
        return new String[]{"format"};
    }

    @Override
    public String[] getOptionalParamsValues() {
        return new String[]{"yyyy-MM-dd HH:mm:ss"};
    }

    @Override
    public int checkInput(String input, String[] params) {
        return CHECK_PASS;
    }

    @Override
    public String getOutput(String input, String[] params) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(getParams(params, 0));
        if (toTimestamp) {
            if (StringUtil.isEmpty(input))
                return String.valueOf(System.currentTimeMillis());
            return String.valueOf(sdf.parse(input).getTime());
        } else {
            long time = System.currentTimeMillis();
            if (!StringUtil.isEmpty(input))
                time = Long.parseLong(input);
            return sdf.format(new Date(time));
        }
    }
}
