package _apache;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsExample {

	public static void main(String[] args) {
		//null或value.length()=0
		boolean empty = StringUtils.isEmpty("");
		System.out.println("isEmpty(\"\")=" + empty);
		empty = StringUtils.isEmpty(null);
		System.out.println("isEmpty(null)=" + empty);
		empty = StringUtils.isEmpty(" ");
		System.out.println("isEmpty(\" \")=" + empty);
		empty = StringUtils.isEmpty("\r\n");
		System.out.println("isEmpty(\"\\r\\n\")=" + empty);
		
		//判断某字符串是否为空或长度为0或由空白符(whitespace)构成
		boolean blank = StringUtils.isBlank(" ");
		System.out.println("isBlank(\" \")=" + blank);
		blank = StringUtils.isBlank(null);
		System.out.println("isBlank(null)=" + blank);
		blank = StringUtils.isBlank("\r\n");
		System.out.println("isBlank(\"\\r\\n\")=" + blank);
		blank = StringUtils.isBlank("\b");
		System.out.println("isBlank(\"\\b\")=" + blank);
	}

}
