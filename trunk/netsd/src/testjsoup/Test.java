package testjsoup;

public class Test {

	public static void main(String[] args) {
		String string = "/3517775795_e849:-<>eea42a.jpg?v=0";
		if(string.indexOf("?")>-1){
			string = string.substring(0,string.indexOf("?"));
		}
		String filename = string.substring(string.lastIndexOf("/") + 1);
		filename = filename.replaceAll("[\\-\\:£º\\<>|\\?]", "_").replaceAll("\\s", "");   
		
		System.out.println(filename);
	}
}
