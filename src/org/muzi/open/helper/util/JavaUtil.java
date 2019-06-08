package org.muzi.open.helper.util;

/**
 * @author: muzi
 * @time: 2018-05-17 18:25
 * @description:
 */
public class JavaUtil {

    /**
     * judge clz`s package need to be imported
     *
     * @param clz
     * @return
     */
    public static boolean needImport(Class clz) {
        String name = clz.getName();
        if (name.startsWith("java.lang"))
            return false;
        return true;
    }


    /**
     * parse java package from path
     *
     * @param path
     * @return
     */
    public static String parsePackage(String path) {
        if (StringUtil.isEmpty(path))
            return null;
        String flag;
        String reg;
        if (CmdUtil.SystemType.isWindows(CmdUtil.getSystemType())) {
            flag = "\\src\\main\\java";
            reg = "\\\\";
        } else {
            flag = "/src/main/java";
            reg = "/";
        }
        int pos = path.indexOf(flag);
        if (pos == -1)
            return null;
        pos += flag.length() + 1;
        String s = path.substring(pos);
        return s.replaceAll(reg, ".");
    }
}
