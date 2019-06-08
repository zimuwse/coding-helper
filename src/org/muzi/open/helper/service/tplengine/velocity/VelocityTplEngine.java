package org.muzi.open.helper.service.tplengine.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.muzi.open.helper.service.tplengine.AbstractTplEngine;
import org.muzi.open.helper.util.ResourceUtil;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2019-06-07 17:04
 * @description:
 */
public class VelocityTplEngine extends AbstractTplEngine {

    private VelocityEngine engine;
    private static Class[] defaultUserFuncClz = {CapFirstFunc.class, NewLineFunc.class};

    private static char newLine = '\n';

    /**
     * default resource loader: classpath
     */
    public VelocityTplEngine() {
        this("classpath", "classpath.resource.loader.class", ClasspathResourceLoader.class, defaultUserFuncClz);
    }

    public VelocityTplEngine(int i) {
        this("string", "string.resource.loader.class", StringResourceLoader.class, defaultUserFuncClz);
    }

    public VelocityTplEngine(String resourceLoader, String resourceLoaderClzKey, Class resourceLoaderClz, Class<? extends Directive>[] userDirectives) {
        //cannot load these classes
        //String userDirective = getUserDirective(userDirectives);
        VelocityEngine ve = new VelocityEngine();
        //ve.setProperty(RuntimeConstants.RESOURCE_LOADER, resourceLoader);
        //ve.setProperty(resourceLoaderClzKey, resourceLoaderClz.getName());
        //ve.addProperty("userdirective", userDirective);
        //close velocity.log
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        ve.init();
        engine = ve;
    }

    private String getUserDirective(Class<? extends Directive>[] userDirectives) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        StringBuilder builder = new StringBuilder();
        for (Class clz : userDirectives) {
            builder.append(clz.getName()).append(',');
            try {
                classLoader.loadClass(clz.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                try {
                    Class.forName(clz.getName());
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    @Override
    public String render(String tpl, Object data) throws Exception {
        Map dataMap = parseData(data);
        if (null == dataMap)
            return "";
        String tplContent = ResourceUtil.getResourceAsString(tpl);
        VelocityContext ctx = new VelocityContext(dataMap);
        ctx.put("new_line", newLine);
        StringWriter writer = new StringWriter(1024);
        /*Template template = engine.getTemplate(tpl, "utf-8");
        template.merge(ctx, writer);*/
        engine.evaluate(ctx, writer, "", tplContent);
        return writer.toString();
    }
}
