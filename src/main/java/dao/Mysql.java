package dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;


import util.Randomtool;

/**
 *  建立数据库连接
 * 
 * setOneWord(String, String);
 * 	传入：一段文字,阅读限时
 * 	传出：在数据库中 的id
 * 
 * getOneWord(String);
 * 	传入：在数据库中的id
 * 	传出：字符数组[一段文字,阅读限时,创建时间]
 * 
 */
public class Mysql {

	Connection con;
	
	private String SQL_URL;
	private String SQL_PORT;
	private String SQL_USER;
	private String SQL_PWD;
	
	private final int RANDOM_LEN = 5;
	
	private final String conFileFomt = "sql_url: \"\";\nport: \"\";\nuser: \"\";\npwd: \"\";";

	public Mysql(){
		upConInfo(); // 在配置文件中检查并获取基本连接信息
	}
	
	public String setOneWord(String word, String time) {
		String id = null;
		con_Sql();
		if(time.indexOf("-")!=-1) {
			time.replace("-", "");
		}
		double timeli_d;
		try {
			timeli_d = Double.parseDouble(time);
		} catch (NumberFormatException e) {
			timeli_d = 2;
		}
		String timeli_i = (int)(timeli_d*60)+"";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datef = sdf.format(date);
		String Lettern = Randomtool.getLettern(RANDOM_LEN);
		String insertsql = "insert into messagedata value('"+Lettern+"','"+word+"','"+datef+"','"+timeli_i+"');";
//		System.out.println("拼接命令:"+insertsql);
		Statement sql_con;
		int ok = 0;
		try {
			sql_con = con.createStatement();
			ok = sql_con.executeUpdate(insertsql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ok != 0)
			id = Lettern;
		return id;
	}
	
	public String[] getOneWord(String id) {
		String data[] = new String[3];
		con_Sql();
		if(id.length() != RANDOM_LEN) {
			return data;
		}
		String searchsql = "select * from messagedata where id='" + id + "';";
		String delsql = "delete from messagedata where id='" + id + "';";
		Statement sql_con;
		ResultSet rs;
		try {
			sql_con = con.createStatement();
			rs = sql_con.executeQuery(searchsql);
			if(rs.next()) {
//				System.out.println("有记录");
				data[0] = rs.getString("word");
				data[1] = rs.getDate("date")+"";
				data[2] = rs.getInt("timelimit")+"";
				sql_con.executeUpdate(delsql);
			}else {
				data[0] = "文本不存在";
				data[1] = "";
				data[2] = "0";
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	

	private void upConInfo() {
		File file = new File("config_sql");
		if(!file.exists()) { // 配置文件不存在时创建文件
			try {
				file.createNewFile();
				addDefaultConf(file);
			} catch (IOException e) {
				System.out.println("无法创建config_sql,请在项目所在目录下创建该文件");
				e.printStackTrace();
			}
			System.out.println("config_sql 文件位置: "+file.getAbsolutePath());
			System.out.println("请在config_sql文件里添加你的sql的基本信息");
		} else { // 配置文件存在时读取文件
			try {
				FileReader fr = new FileReader(file);
				StringBuffer context = new StringBuffer(""); // 在配置文件中读取到的信息
				char[] cbuf = new char[1024];
				int hasread = -1; // 初始化已读取的字符数
				while ((hasread = fr.read(cbuf)) != -1) {
					context.append(new String(cbuf, 0, hasread));
				}
				fr.close();
				boolean conferr = false; // 检查配置文件是否有效
				if(context.length() <= conFileFomt.length()) { // 如果没有在配置文件中填入新的信息
					conferr = true;
					addDefaultConf(file);
				}
				String conflist[] = context.toString().split(";");
				if(conflist.length != 5) {
					conferr = true;
				}else {
					SQL_URL = conflist[0].substring(conflist[0].indexOf("\"")+1, conflist[0].lastIndexOf("\""));
					SQL_PORT = conflist[1].substring(conflist[1].indexOf("\"")+1, conflist[1].lastIndexOf("\""));
					SQL_USER = conflist[2].substring(conflist[2].indexOf("\"")+1, conflist[2].lastIndexOf("\""));
					SQL_PWD = conflist[3].substring(conflist[3].indexOf("\"")+1, conflist[3].lastIndexOf("\""));					
					System.out.println("========== sql_info ==========\nSQL_URL:"+SQL_URL + "\nSQL_PORT:"+SQL_PORT + "\nSQL_USER:"+SQL_USER + "\nSQL_PWD:"+SQL_PWD+ "\n========== sql_info ==========");
				}
				if(conferr) {
					System.out.println("config_sql 文件位置: "+file.getAbsolutePath());
					System.out.println("数据库的配置文件中存在错误，请检查config_sql");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addDefaultConf(File file) { // 向配置文件中写入默认模板
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(conFileFomt);
			fw.close();	
		} catch (IOException e) {
			System.out.println("初始化数据库配置文件模板失败，请创建config_sql文件并添加如下配置：\n"+conFileFomt);
			e.printStackTrace();
		}
	}


	private void con_Sql() {
		String url = "jdbc:mysql://" + SQL_URL + ":" + SQL_PORT + "/oneoffword?useSSL=false&characterEncoding=utf-8";
		try {
			con = DriverManager.getConnection(url, SQL_USER, SQL_PWD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("无法连接数据库");
		}
	}
}
