package com.example.aditya.breakoutgame;

/**
 * Created by aditya on 11/25/15.
 */


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by aditya on 11/1/15.
 */
public class FileHandler {

    Context fileContext;

    FileHandler(Context fileContext){
        this.fileContext=fileContext;
    }


    public void setDataObject(ArrayList<DataModel> inf){
        clearContents();
        try {

            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" );
            File foo=new File(dir,"temp.txt");

            PrintStream pr = new PrintStream(fileContext.openFileOutput(foo.getName(), Context.MODE_PRIVATE));

            for(DataModel temp:inf){
                String str;

                str=temp.getName();
                str="(name:"+str+")";
                pr.print(str);

                str=temp.getScore();
                str="(score:"+str+")";
                pr.print(str);

                str=temp.getTime();
                str="(time:"+str+")";
                pr.print(str);

                str=temp.getId();
                str="(id:"+str+")";
                pr.print(str);

                pr.println("");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<DataModel> getDataObject(){
        ArrayList<DataModel> inf=new ArrayList<DataModel>();
        String str;

        final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Download" );
        File foo=new File(dir,"temp.txt");

        if(!foo.exists()) {
            try {
                foo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            FileInputStream fis=fileContext.openFileInput(foo.getName());
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            while((str=br.readLine())!=null)
            {
                inf.add(convertStringtoObject(str));
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        Collections.sort(inf);
        return inf;
    }


    private DataModel convertStringtoObject(String s){
        HashMap<String,String> temp=convertStringtoMap(s);
        DataModel inf=new DataModel();
        for(Map.Entry<String, String> e : temp.entrySet()){


            if(e.getKey().equals("name")){
                inf.setName(e.getValue());
            }

            if(e.getKey().equals("score")){
                inf.setScore(e.getValue());
            }

            if(e.getKey().equals("time")){
                inf.setTime(e.getValue());
            }

            if(e.getKey().equals("id")){
                inf.setId(e.getValue());
            }

        }
        return inf;

    }


    private HashMap<String,String> convertStringtoMap(String s){
        HashMap<String,String> temp=new HashMap<String,String>();
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(s);
        while(m.find()) {
            String [] str=m.group(1).split(":",2);
            temp.put(str[0],str[1]);
        }
        return temp;
    }


    private void clearContents(){
        final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" );
        File foo=new File(dir,"temp.txt");
        try{
            FileOutputStream writer = fileContext.openFileOutput(foo.getName(),Context.MODE_PRIVATE);

            writer.write(("").getBytes());

            writer.close();
        }catch(FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  String getMaxId() {
        DataModel temp;
        String str;
        int maxid=0;
        final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Download" );
        File foo=new File(dir,"temp.txt");
        try{
            FileInputStream fis=fileContext.openFileInput(foo.getName());
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            while((str=br.readLine())!=null)
            {
                temp=convertStringtoObject(str);
                if(Integer.parseInt(temp.getId())>maxid){
                    maxid=Integer.parseInt(temp.getId());
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return String.valueOf(maxid);
    }

    public int isInTopTen(DataModel obj){
        ArrayList<DataModel> arr,finalArr;
        arr=getDataObject();
        if(arr.size() < 10){
            return 1;
        }
        int compareScore,compareTime;
        for(DataModel temp : arr){
            compareScore=obj.getScore().compareTo(temp.getScore());
            compareTime=obj.getTime().compareTo(temp.getTime());
            if(compareScore==1||(compareScore==0 && compareTime==1)){
                return 1;
            }
        }
        return 0;
    }

}