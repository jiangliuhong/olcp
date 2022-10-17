package top.jiangliuhong.olcp.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;

/**
 * dom4j xml util class
 */
public class XmlUtil {
    public static Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(url);
    }

    public static Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(file);
    }

    public static Integer attributeValueInt(Element element, String name) throws NumberFormatException {
        String string = element.attributeValue(name);
        if (StringUtils.isBlank(string)) {
            return null;
        }
        return Integer.parseInt(string);
    }
}
