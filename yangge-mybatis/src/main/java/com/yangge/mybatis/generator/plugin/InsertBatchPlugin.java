package com.yangge.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InsertBatchPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

//    /**
//     * 修改Mapper类
//     */
//    @Override
//    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
//        generatedBatchInsertMethod("insertBatch", interfaze, introspectedTable);
//        return super.clientGenerated(interfaze, introspectedTable);
//    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn primaryKeyColumn = primaryKeyColumns.stream()
                .filter(IntrospectedColumn::isAutoIncrement)
                .findFirst()
                .orElse(null);
        //.orElseThrow(() -> new RuntimeException(String.format("生成文件时发生错误, 表: %s, 无自增主键。", introspectedTable.getFullyQualifiedTableNameAtRuntime())));

        XmlElement insertBatch = new XmlElement("insert");
        insertBatch.addAttribute(new Attribute("id", "insertBatch"));
        insertBatch.addAttribute(new Attribute("parameterType", "java.util.Collection"));
        if (!Objects.isNull(primaryKeyColumn)) {
            insertBatch.addAttribute(new Attribute("useGeneratedKeys", "true"));
            insertBatch.addAttribute(new Attribute("keyColumn", primaryKeyColumn.getActualColumnName()));
            insertBatch.addAttribute(new Attribute("keyProperty", JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false)));
        }

        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        String columns = allColumns.stream().filter(p -> !p.isAutoIncrement())
                .map(IntrospectedColumn::getActualColumnName)
                .collect(Collectors.joining(", "));

        insertBatch.addElement(new TextElement(String.format("insert into %s (%s) values", introspectedTable.getFullyQualifiedTableNameAtRuntime(), columns)));

        List<TextElement> valueColumns = allColumns.stream().filter(p -> !p.isAutoIncrement())
                .map(m -> generateForValue(m, "item."))
                .collect(Collectors.toList());

        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("item", "item"));
        foreach.addAttribute(new Attribute("index", "index"));
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("separator", ","));

        XmlElement trimForValues = new XmlElement("trim");
        trimForValues.addAttribute(new Attribute("prefix", "("));
        trimForValues.addAttribute(new Attribute("suffix", ")"));
        trimForValues.addAttribute(new Attribute("suffixOverrides", ","));
        foreach.addElement(trimForValues);

        valueColumns.forEach(trimForValues::addElement);

        insertBatch.addElement(foreach);

        System.out.println(insertBatch.toString());

        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(insertBatch);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 修改Mapper类
     */
    private void generatedBatchInsertMethod(String methodName, Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method(methodName);
        // 1.设置方法可见性
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        // 2.设置返回值类型
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getIntInstance();

        method.setReturnType(returnType);

        // 4.设置参数列表
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        listType.addTypeArgument(paramType);
        method.addParameter(new Parameter(listType, "list", "@Param(\"list\")"));
        importedTypes.add(listType);

        // 设置需要导入的类
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    private TextElement generateForValue(IntrospectedColumn column, String itemName) {
        String actualColumnName = column.getActualColumnName().replace("is_", "");
        String camelCaseColumnName = JavaBeansUtil.getCamelCaseString(actualColumnName, false);

        return new TextElement(String.format("#{%s%s, jdbcType=%s},", itemName, camelCaseColumnName, column.getJdbcTypeName()));
    }
}