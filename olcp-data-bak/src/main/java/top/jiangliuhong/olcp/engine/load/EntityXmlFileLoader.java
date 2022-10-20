package top.jiangliuhong.olcp.engine.load;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import top.jiangliuhong.olcp.engine.load.bean.EntityInfo;
import top.jiangliuhong.olcp.engine.load.bean.FieldInfo;
import top.jiangliuhong.olcp.engine.type.FieldType;
import top.jiangliuhong.olcp.common.utils.XmlUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * entity xml file loader
 */
@Log4j2
public class EntityXmlFileLoader implements ILoader<EntityInfo> {

    private final File file;

    public EntityXmlFileLoader(File file) {
        this.file = file;
    }

    @Override
    public EntityInfo load() {
        try {
            Document document = XmlUtil.parse(this.file);
            Element root = document.getRootElement();
            if (!root.getName().equals("entity")) {
                log.warn("the [" + this.file.getAbsolutePath() + "]file root name is not 'entity'");
                return null;
            }
            String name = this.file.getName();
            // remove .xml
            name = name.substring(0, name.length() - 4);
            EntityInfo entityInfo = new EntityInfo();
            entityInfo.setName(name);
            entityInfo.setTitle(root.attributeValue("title"));
            entityInfo.setParent(root.attributeValue("parent"));
            // read short description
            entityInfo.setShortDescription(root.attributeValue("short-description"));
            Element shortDescriptionElement = root.element("short-description");
            if (shortDescriptionElement != null && StringUtils.isNotBlank(shortDescriptionElement.getStringValue())) {
                entityInfo.setShortDescription(shortDescriptionElement.getStringValue());
            }
            entityInfo.setFieldInfos(new ArrayList<>());
            readeEntityField(entityInfo, root);
            return entityInfo;
        } catch (Exception e) {
            throw new RuntimeException("load entity error", e);
        }
    }

    private void readeEntityField(EntityInfo entityInfo, Element root) {
        // each field
        Iterator<Element> fieldIterator = root.elementIterator("field");
        while (fieldIterator.hasNext()) {
            Element fieldElement = fieldIterator.next();
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setName(fieldElement.attributeValue("name"));
            fieldInfo.setTitle(fieldElement.attributeValue("title"));
            try {
                fieldInfo.setLength(XmlUtil.attributeValueInt(fieldElement, "length"));
            } catch (NumberFormatException numberFormatException) {
                throw new RuntimeException("entity [" + entityInfo.getName() + "] filed [" + fieldInfo.getName() + "] length must be int");
            }
            try {
                fieldInfo.setPrecision(XmlUtil.attributeValueInt(fieldElement, "precision"));
            } catch (NumberFormatException numberFormatException) {
                throw new RuntimeException("entity [" + entityInfo.getName() + "] filed [" + fieldInfo.getName() + "] length must be int");
            }
            fieldInfo.setDefaultValue(fieldElement.attributeValue("default-value"));
            Element defaultValueElement = fieldElement.element("default-value");
            if (defaultValueElement != null && StringUtils.isNotBlank(defaultValueElement.getStringValue())) {
                fieldInfo.setDefaultValue(defaultValueElement.getStringValue());
            }
            fieldInfo.setReference(fieldElement.attributeValue("reference"));
            boolean required = StringUtils.equals(fieldElement.attributeValue("required"), "true") ||
                    StringUtils.equals(fieldElement.attributeValue("required"), "1");
            fieldInfo.setRequired(required);
            try {
                fieldInfo.setType(FieldType.valueOf(fieldElement.attributeValue("type")));
            } catch (Exception e) {
                throw new RuntimeException("entity [" + entityInfo.getName() + "] filed [" + fieldInfo.getName() + "] type is wrong");
            }
            entityInfo.getFieldInfos().add(fieldInfo);
        }
    }

}
