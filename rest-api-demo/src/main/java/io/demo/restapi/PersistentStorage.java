package io.demo.restapi;

import java.util.*;
import java.io.*;
import com.google.gson.*;

class PersistentStorage {

	static HashMap<Long,Task> read() {

		HashMap<Long,Task> mapInFile=new HashMap<Long,Task>();

		try {
			File toRead=new File("data.txt");
			FileInputStream fis=new FileInputStream(toRead);

			Scanner sc=new Scanner(fis);

			//read data from file line by line:
			String currentLine;
			while(sc.hasNextLine()) {
				currentLine=sc.nextLine();
				//now tokenize the currentLine:
				StringTokenizer st=new StringTokenizer(currentLine,"=",false);
				//put tokens ot currentLine in map
				long id = Long.parseLong((st.nextToken()));
				String json = st.nextToken();
				JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
				Task task = new Task(jsonObject.get("id").getAsLong(), jsonObject.get("content").getAsString(),jsonObject.get("done").getAsString());
				mapInFile.put(id,task);
			}
			fis.close();
			return mapInFile;
		}catch(Exception e) {}
		return mapInFile;
	}

	static void write(HashMap<Long, Task> map) {

		try {
			File fileTwo=new File("data.txt");
			FileOutputStream fos=new FileOutputStream(fileTwo);
			PrintWriter pw=new PrintWriter(fos);

			for(Map.Entry<Long, Task> m :map.entrySet()){
				pw.println(m.getKey()+"="+m.getValue());
			}

			pw.flush();
			pw.close();
			fos.close();
		} catch(Exception e) {}

		//read from file
		try {
			File toRead=new File("data.txt");
			FileInputStream fis=new FileInputStream(toRead);

			Scanner sc=new Scanner(fis);

			HashMap<String,String> mapInFile=new HashMap<String,String>();

			//read data from file line by line:
			String currentLine;
			while(sc.hasNextLine()) {
				currentLine=sc.nextLine();
				//now tokenize the currentLine:
				StringTokenizer st=new StringTokenizer(currentLine,"=",false);
				//put tokens ot currentLine in map
				mapInFile.put(st.nextToken(),st.nextToken());
			}
			fis.close();

			//print All data in MAP
			for(Map.Entry<String,String> m :mapInFile.entrySet()) {
				System.out.println(m.getKey()+" : "+m.getValue());
			}
		}catch(Exception e) {}
	}
}
