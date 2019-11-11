package memory;

public class Remember {

	private static Remember instance = new Remember();

	public static Remember getInstance() {
		return instance;
	}

	public String data1;
	public String data2;
	public String data3;

	public void setData(Object a, Object a2, Object a3) {
		data1 = a + "";
		data2 = a2 + "";
		data3 = a3 + "";
	}

}
