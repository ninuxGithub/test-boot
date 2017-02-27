package com.example;

import java.util.StringTokenizer;

public class MyTestStringTokenizer {

	public static void main(String[] args) {

		String s = "|struts.xml,1451210087873.xml,.xml,#OA数据库脚本.txt,1451210087857.txt,.txt,";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 100; i++) {
			sb.append(s);
		}

		String temp = null;
		Long current = System.currentTimeMillis();
		StringTokenizer tokenizer = new StringTokenizer(sb.toString(), "|#");
		while (tokenizer.hasMoreTokens()) {
			temp = tokenizer.nextToken();
			StringTokenizer tokenizer1 = new StringTokenizer(temp, ",");
			if (tokenizer1.hasMoreTokens()) {
				System.out.println(tokenizer1.nextToken());
			}
		}

		System.out.println("spend:" + (System.currentTimeMillis() - current));
	}

}
