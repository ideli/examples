package _xml.dom4j;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class SimpleReader {

	public void demo() {
		
		String strPath = System.getProperty("user.dir") + "\\files\\config.xml";
		
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(strPath));
			Node main = doc.selectSingleNode("//root/config[@type='main']");
			if (main != null) {
				String runType = main.valueOf("application");
				System.out.println("runType=" + runType);
				Node tcp = doc.selectSingleNode("//root/config[@type='tcp']");
				String serverIp = tcp.valueOf("ip");
				String serverPort = tcp.valueOf("port");
				System.out.println("server=" + serverIp + ":" + serverPort);
			}

		} catch (DocumentException e) {
			System.out.println(e.getMessage() + e.getStackTrace());
		}
    }
	
	public static void main(String[] args) {
		new SimpleReader().demo();
	}
}
