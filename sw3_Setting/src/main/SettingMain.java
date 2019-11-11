package main;

import jdbc.Update;

public class SettingMain {
	public static void main(String[] args) {
		for(String sql : Config.CREATE_DATABASE){
			new Update(sql);
		}
		for(String sql : Config.CREATE_TABLE){
			new Update(sql);
		}
		for(String sql : Config.TABLE_NAME){
			String dir = System.getProperty("user.dir")+"\\DataFiles\\"+sql+".txt";
			sql = String.format(Config.IMPORT, dir.replace("\\", "/"),sql);
			new Update(sql);
		}
		new Update(Config.GRANT);
	}
}
