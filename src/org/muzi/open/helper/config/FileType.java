package org.muzi.open.helper.config;

/**
 * @author: li.rui
 * @time: 2019-07-26 15:49
 * @description:
 */
public enum FileType {
    FOLDER(1),
    JAR(2),
    FILE(4),;
    private int val;

    FileType(int val) {
        this.val = val;
    }

    public int value() {
        return val;
    }

    private static boolean eq(int val, FileType type) {
        return (val & type.val) == type.val;
    }

    public static boolean isFolder(int val) {
        return eq(val, FOLDER);
    }

    public static boolean isJar(int val) {
        return eq(val, JAR);
    }

    public static boolean isFile(int val) {
        return eq(val, FILE);
    }
}
