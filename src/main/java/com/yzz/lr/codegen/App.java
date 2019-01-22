package com.yzz.lr.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Hello world!
 *
 */
public class App 
{
	private void writeUTFFile(String path,String content) throws Exception{
		File file=new File(path);
		File dir=file.getParentFile();
		if(!dir.exists()) dir.mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(content);
        osw.close();
        fos.close();
	}
	public void genFile(String templateName,HashMap context,String path) throws Exception{
		File file=new File(path);
		Configuration configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(App.class, "/com/yzz/lr/codegen"));
        Template template = configuration.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        
        template.process(context, writer);
        writeUTFFile(path,writer.toString());
	}
	/**
	 * 将name中_去掉，并将后面的第一个字母大写
	 * @param Name
	 * @return
	 */
	public String convert(String name){
		String str=name.toLowerCase().trim();
		String[] array=str.split("_");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<array.length;i++){
			if(i==0){
				sb.append(array[i]);
			}else{
				String s=array[i];
				sb.append(firstUpper(s));
			}
		}
		return sb.toString();
	}
	public String firstUpper(String s){
		StringBuffer sb=new StringBuffer();
		sb.append(Character.toUpperCase(s.charAt(0)));
		if(s.length()>1) sb.append(s.substring(1));
		return sb.toString();
	}
	public static HashMap<String,String> mapJavaType=new HashMap<String,String>();
	static {
		mapJavaType.put("VARCHAR", "String");
		mapJavaType.put("DATETIME", "Date");
		mapJavaType.put("DATE", "Date");
		mapJavaType.put("DOUBLE", "Double");
		mapJavaType.put("TINYINT", "Integer");
		mapJavaType.put("INT", "Integer");
		mapJavaType.put("BIGINT", "BigInteger");
		mapJavaType.put("CHAR", "String");
		mapJavaType.put("FLOAT", "Float");
		mapJavaType.put("DECIMAL", "Decimal");
		mapJavaType.put("BLOB", "byte[]");
	}
	public static HashMap<String,String> mapJdbcType=new HashMap<String,String>();
	static {
		mapJdbcType.put("VARCHAR", "VARCHAR");
		mapJdbcType.put("DATETIME", "TIMESTAMP");
		mapJdbcType.put("DATE", "DATE");
		mapJdbcType.put("DOUBLE", "DOUBLE");
		mapJdbcType.put("TINYINT", "INTEGER");
		mapJdbcType.put("INT", "INTEGER");
		mapJdbcType.put("BIGINT", "BIGINT");
		mapJdbcType.put("CHAR", "String");
		mapJdbcType.put("FLOAT", "Float");
		mapJdbcType.put("DECIMAL", "Decimal");
		mapJdbcType.put("BLOB", "byte[]");
	}

	public void produceCode(String codepackage,String codePath,String tableName,String projectPath) throws Exception{
		HashMap context=new HashMap();
    	String objNameFirstLower=convert(tableName);
    	String objName=firstUpper(objNameFirstLower);
    	
    	List<FiledEntity> list=new ArrayList<FiledEntity>();
    	String pkColumn="";//主键字段
    	Class.forName("com.mysql.jdbc.Driver") ;   
    	
    	Connection con = DriverManager.getConnection("jdbc:mysql://rm-uf6sfl7rf9312z2uoo.mysql.rds.aliyuncs.com:3306/weixin_sale?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true" , "julianfish" , "CodeflagRDS2017" ) ;    
    	//Connection con = DriverManager.getConnection("jdbc:mysql://115.29.245.253:3306/hfkmdb?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true" , "hfkm" , "hfkm" ) ;    
    	PreparedStatement pstmt = con.prepareStatement("select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY from information_schema.COLUMNS"
    			+ " where TABLE_SCHEMA=? AND TABLE_NAME=? "
    			+ " ORDER BY ORDINAL_POSITION") ;  
    	pstmt.setString(1, "weixin_sale");
    	pstmt.setString(2, tableName);
    	ResultSet rs=pstmt.executeQuery();
    	int a = 0;
    	while(rs.next()){
    		int index=1;
    		FiledEntity filedEntity=new FiledEntity();
    		String column=rs.getString(index++);
    		String data_type=rs.getString(index++);
    		
    		System.out.println(data_type+a++ +column);
    		
    		String comment=rs.getString(index++);
    		String key=rs.getString(index++);
    		
    		filedEntity.setColumn(column);
    		filedEntity.setProperty(convert(column));
    		filedEntity.setPropertyJavaType(mapJavaType.get(data_type.toUpperCase()));
    		filedEntity.setJdbcType(mapJdbcType.get(data_type.toUpperCase()));
    		filedEntity.setComment(comment);
    		filedEntity.setGetMethod("	public "+filedEntity.getPropertyJavaType()+" get"+firstUpper(filedEntity.getProperty())+"() {"
    				+"\n		return "+filedEntity.getProperty()+";"
    				+"\n	}"
    				);
    		filedEntity.setSetMethod("	public void set"+firstUpper(filedEntity.getProperty())+"("+filedEntity.getPropertyJavaType()+" "+filedEntity.getProperty()+") {"
    				+"\n		this."+filedEntity.getProperty()+"="+filedEntity.getProperty()+";"
    				+"\n	}"
    				);
    		
    		if("PRI".equals(key)){ //主键
    			pkColumn=filedEntity.getColumn();
    		}
    		list.add(filedEntity);
    		
    	}
    	
    	context.put("list", list);//字段结果
    	context.put("objName", objName);//首字母大写对象，如"ApexMerchant"
    	context.put("objNameFirstLower", objNameFirstLower); //首字母小写对象,如"apexMerchant"
    	context.put("pkg", codepackage);//包名，如"com.hfkm.merchant"
    	context.put("tableName", tableName); //表名,如"APEX_MERCHANT"
    	context.put("pkColumn",pkColumn);//主键字段,如"MERCHANT_ID"
    	context.put("pkProperty",convert(pkColumn));//主键字段,如"merchantId"
    	context.put("requestMapping", "/"+codePath+"/"+objName.toLowerCase());// requestMapping，如"/common/sysdictitem"
    	
    	String basePath=projectPath+"src/main/java/"+codepackage.replace('.', '/');
    	String mapperPath=projectPath+"src/main/resources/mapper/"+objName+"Mapper.xml";
    	genFile("Controller.ftl",context,basePath+"/controller/"+objName+"Controller.java");
    	genFile("IService.ftl",context,basePath+"/service/"+objName+"Service.java");
    	genFile("ServiceImpl.ftl",context,basePath+"/service/impl/"+objName+"ServiceImpl.java");
    	genFile("IDao.ftl",context,basePath+"/dao/"+objName+"Mapper.java");
    	//genFile("DaoImpl.ftl",context,basePath+"/dao/impl/"+objName+"Dao.java");
    	genFile("Domain.ftl",context,basePath+"/model/"+objName+".java");
//    	genFile("Facade.ftl",context,basePath+"/common/facade/service/"+objName+"Facade.java");
//    	genFile("FacadeImpl.ftl",context,basePath+"/biz/ws/"+objName+"FacadeImpl.java");
    	genFile("Mapping.ftl",context,mapperPath);
	}
    public static void main( String[] args ) throws Exception{
    	String codepackage="com.yzz.lr"; //所在包
    	String codePath="yll"; //用于生成Mapper.xml所在的目录,如common/abframe
    	String tableName="weixin_message";

    	String projectPath=System.getProperty("user.dir");
//    	System.out.println(projectPath);
    	
    	App app=new App();
//
    	app.produceCode(codepackage, codePath, tableName.trim(),projectPath+"/");

    	
       
    }
}
