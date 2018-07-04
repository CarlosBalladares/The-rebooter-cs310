package com.cs310.TheRebooter.server;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Parser {
    private String csvFile = "file.csv";
    private String tablename;

    public static void main(String[] args) {

     //   Parser obj = new Parser();
    //    obj.ReadCSV();
    }

    public Parser(String csvFile, String tablename)
    {
    	this.csvFile = csvFile;
    	this.tablename= tablename;
    }
    public List<String> ReadCSV() {
        BufferedReader br = null;
        List<String> output = null;
        String line = null;
        String[] st = null;
        String fields = null;
        String statement;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            output = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                st = line.replace("\"","").split(",");
                output.add("INSERT INTO "+tablename+" "
                                + " VALUES "
                                + "('"
                                + joinStringArray(st, "', '")
                                +  "')"
                                + ";"
                                + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;

    }

    private static int numberOfFields(String csvFile) {
        int count = 0;
        try {
            FileReader fr = new FileReader(csvFile);
            BufferedReader br = new BufferedReader(fr);
              String line = br.readLine();
                String[] parts = line.split(",");
                for (String fields : parts) {
                    count++;
            }
                br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (count);

    }
    public String joinStringArray(String[] strArray, String sep){
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < numberOfFields(csvFile); i++) {
            if (i > 0)
                sb.append(sep);
            sb.append(strArray[i].replaceAll("'", ""));
        }
        return sb.toString();
    }

}
