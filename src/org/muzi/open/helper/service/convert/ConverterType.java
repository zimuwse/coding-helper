package org.muzi.open.helper.service.convert;

/**
 * @author: muzi
 * @time: 2019-06-04 23:05
 * @description:
 */
public enum ConverterType {
    MD5("MD5", "MD5 input string", new MD5Converter()),
    JSON_FORMAT("JSON FORMAT", "Format input json string", new JsonFormatConverter()),
    TIMESTAMP_TO_DATE("TIMESTAMP TO DATE", "Format timestamp with input format", new DateFormatConverter(false)),
    DATE_TO_TIMESTAMP("DATE TO TIMESTAMP", "Parse date string to timestamp by input format", new DateFormatConverter(true)),
    BASE64_ENCODE("BASE64 ENCODE", "Encode input string by base64", new Base64Converter(true)),
    BASE64_DECODE("BASE64 DECODE", "Decode input string from base64", new Base64Converter(false)),
    UNICODE_ENCODE("UNICODE ENCODE", "Encode input string by unicode", new UnicodeConverter(true)),
    UNICODE_DECODE("UNICODE DECODE", "Decode input string from unicode", new UnicodeConverter(false)),
    UTL_ENCODE("URL ENCODE", "URL encode input string", new URLConverter(true)),
    URL_DECODE("URL DECODE", "URL decode input string", new URLConverter(false)),
    STRING_JOIN("STRING JOIN", "Split input string and join with connector", new StringJoinConverter()),
    CAMEL_CASE("CAMEL CASE", "Change fields to camel case", new CamelCaseConverter(false)),
    UN_CAMEL_CASE("UNCAMEL CASE", "Remove camel case from fields", new CamelCaseConverter(true)),
    SORT_WORDS("SORT WORDS", "Sort words", new SortConverter()),;
    private String name;
    private String desc;
    private IConverter converter;

    ConverterType(String name, String desc, IConverter converter) {
        this.name = name;
        this.desc = desc;
        this.converter = converter;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public IConverter getConverter() {
        return converter;
    }

    public static ConverterType byName(String name) {
        ConverterType[] types = ConverterType.values();
        for (ConverterType type : types) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }
}
