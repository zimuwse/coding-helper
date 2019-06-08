package org.muzi.open.helper.service.tplengine;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2019-06-07 16:58
 * @description:
 */
public abstract class AbstractTplEngine implements TplEngine {

    public boolean render(String tpl, Object data, File targetFile) throws Exception {
        String result = render(tpl, data);
        if (null == result)
            return false;
        OutputStream os = new FileOutputStream(targetFile);
        IOUtils.write(result, os, "utf-8");
        os.close();
        return true;
    }

    protected Map parseData(Object data) {
        if (null == data)
            return null;
        if (data instanceof Map)
            return (Map) data;
        BeanMap.Generator generator = new BeanMap.Generator();
        generator.setBean(data);
        BeanMap map = generator.create();
        Map m = new HashMap(map);
        return m;
    }
}
