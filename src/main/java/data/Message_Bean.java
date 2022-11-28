package data;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 本应在此处存一些数据
 * 
 * v0.1.0
 * 目前这里有数据，或许还有唯一用途大概就是发送rsa公钥了吧,但路径原因,已弃用
 *
 */
public class Message_Bean extends HttpServlet{
	

	public void init(ServletConfig config) throws ServletException{
		super.init(config);
	}

	/**
	 * 获取公钥
	 */
	public String getRSAPK() {
		String rsapk = null;
		
		return rsapk;
	}
}
