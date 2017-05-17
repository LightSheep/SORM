package com.mingyang.sorm.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.bean.ColumnInfo;
import com.mingyang.sorm.bean.JavaFieldGetSet;
import com.mingyang.sorm.bean.TableInfo;
import com.mingyang.sorm.common.configuration.FrameConfig;
import com.mingyang.sorm.core.MySQLTypeConvertor;
import com.mingyang.sorm.core.TableContext;
import com.mingyang.sorm.core.TypeConvertor;

/**
 * 
 * <h1>JavaFileUtils</h1>
 * <h1>封装了生成Java文件（源代码）操作</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class JavaFileUtils {
	
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(JavaFileUtils.class);
	
	/**
	 * 根据字段信息生成Java属性及getset方法
	 * @param column 字段信息
	 * @param convertor 类型转换器
	 * @return
	 */
	private static JavaFieldGetSet createJavaFieldGetSetSrc(ColumnInfo column, TypeConvertor convertor) {
		
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		String javaFieldType = convertor.dbType2JavaType(column.getDataType());
		String fieldName = column.getName();
		/*
		 * 拼接属性声明源码
		 * 例如：private String name;
		 * 拼接时候注意排版
		 */
		jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + fieldName + ";\n");
		
		/*
		 * 拼接get方法源码
		 * 例如：
		 * public String getName() {
		 * 	return name;
		 * }
		 */
		StringBuffer sbGet = new StringBuffer();
		sbGet.append("\tpublic " + javaFieldType + " get" + StringUtils.capitalize(fieldName) + "() {\n");
		sbGet.append("\t\treturn " + fieldName +";\n");
		sbGet.append("\t}");
		jfgs.setGetInfo(sbGet.toString());
		
		/*
		 * 拼接set方法源码
		 * 例如：
		 * public void setName() {
		 * 	this.name = name;
		 * }
		 */
		StringBuffer sbSet = new StringBuffer();
		sbSet.append("\tpublic void set" + StringUtils.capitalize(fieldName) + "(" + javaFieldType + " " + fieldName +") {\n");
		sbSet.append("\t\tthis." + fieldName + " = " + fieldName + ";\n");
		sbSet.append("\t}");
		jfgs.setSetInfo(sbSet.toString());
		
		return jfgs;
		
	}
	
	/**
	 * 根据表信息生成Java类的源代码
	 * @param tableInfo 表信息
	 * @param convertor 类型转换器
	 * @return Java类源代码
	 */
	private static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
		
		Map<String, ColumnInfo> columns = tableInfo.getColumns();
		List<JavaFieldGetSet> javaFields = new ArrayList<JavaFieldGetSet>();
		
		for(ColumnInfo ci : columns.values()) {
			javaFields.add(createJavaFieldGetSetSrc(ci, convertor));
		}
		
		StringBuffer src = new StringBuffer();
		FrameConfig conf = new FrameConfig();
		
		//生成package语句
		src.append("package " + conf.getVal("frame.poPackage") + ";\n\n");
		//生成import语句
		//src.append("import java.sql.*;\n");
		//src.append("import java.util.*;\n\n");
		//生成类声明语句
		src.append("public class " + StringUtils.capitalize(tableInfo.getName()) + " {\n\n");
		//生成属性列表
		for (JavaFieldGetSet temp : javaFields) {
			src.append(temp.getFieldInfo() + "\n");
		}
		src.append("\n");
		//生成无参构造器
		src.append("\tpublic " + StringUtils.capitalize(tableInfo.getName()) + "() {\n");
		src.append("\t}\n\n");
		//生成get方法语句
		for (JavaFieldGetSet temp : javaFields) {
			src.append(temp.getGetInfo() + "\n");
		}
		src.append("\n");
		//生成set方法语句
		for (JavaFieldGetSet temp : javaFields) {
			src.append(temp.getSetInfo() + "\n");
		}
		src.append("\n");
		//结束语句
		src.append("}\n");
		return src.toString();
	}
	
	/**
	 * 
	 * @param tableInfo 表信息
	 * @param convertor 类型转换器
	 */
	public static void createJavaPOFile(TableInfo tableInfo, TypeConvertor convertor) {
		
		FrameConfig conf = new FrameConfig();
		
		String src = createJavaSrc(tableInfo, convertor);
		String srcPath = conf.getVal("frame.srcPath");
		String packagePath = conf.getVal("frame.poPackage").replaceAll("\\.", "\\\\");
		
		String poPath = srcPath + "\\" + packagePath + "\\" + StringUtils.capitalize(tableInfo.getName()) + ".java";
		
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(poPath));
			bw.write(src);
		} catch (IOException e) {
			log.error("创建po类{}失败", StringUtils.capitalize(tableInfo.getName()), e);
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e2) {
				log.error("", e2);
			}
		}
	}
	
	/**
	 * Just for test
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, TableInfo> map = TableContext.getTableInfos();
		for (TableInfo ti : map.values()) {
			createJavaPOFile(ti, new MySQLTypeConvertor());
		}
	}
	
}
