package test;

public class test {
	public static void main(String[] args) {
		String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
		 String[] str1 = {"aaa@","aa.b@qq.com","1123@163.com","113fe$@11.com","han. @sohu.com.cn","han.c@sohu.com.cn.cm.cm"};
		 for (String str:str1){
		     System.out.println(str+" \\\\. "+str.matches(regex));
		 }
	
	
	}
}
