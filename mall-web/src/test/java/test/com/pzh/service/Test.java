package test.com.pzh.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
     @org.junit.Test
     public void createEmp(){
          List<String > sqlList = new ArrayList();
          String sql = "INSERT INTO `test`.`emp`(`emp_no`, `name`, `age`, `mobile`,`gender`,`dept_id`) VALUES (";
          for(int i=0;i<200000;i++){
               String temp = sql;
               String name = "Name"+(i%8);
               int age = 20+ (i%10);
               int gender= 0;
               if(i%2 ==0)
                  gender=1;
               int dept_id = i%10;
               temp+= "'E"+i+"','"+name+"',"+age+",'13582521112',"+gender+","+dept_id+");";
               sqlList.add(temp);
          }
          String path = "D:\\sql\\sql.sql" ;
          createFile(path,sqlList);
     }
     @org.junit.Test
     public void createDept(){
          List<String > sqlList = new ArrayList();
          String sql = "INSERT INTO `test`.`dept`(`detp_name`, `detp_no`) VALUES (";
          for(int i=0;i<10;i++){
               String temp = sql;
               String name = "Name"+(i%8);
               int age = 20+ (i%10);
               int gender= 0;
               if(i%2 ==0)
                    gender=1;
               temp+= "'Dept"+i+"','Dept"+i+"');";
               sqlList.add(temp);
          }
          String path = "D:\\sql\\dept.sql" ;
          createFile(path,sqlList);
     }
     @org.junit.Test
     public void createSalary(){
          List<String > sqlList = new ArrayList();
          String sql = "INSERT INTO `test`.`salary`(`emp_id`, `month`, `salary`) VALUES (";
          for(int i=0;i<200000;i++){
               String temp = sql;
               temp+= "'"+i+"','"+"2017-"+((i%12)+1)+"',"+(5000+i)+");";
               sqlList.add(temp);
          }
          String path = "D:\\sql\\salary.sql" ;
          createFile(path,sqlList);
     }

     public void createFile(String path,List<String> sqlList){
          File file = new File(path);
          PrintWriter pw = null;
          FileOutputStream fos = null;
          try {
               fos = new FileOutputStream(file);
               pw = new PrintWriter(fos);
               for(String str : sqlList){
                    pw.write(str);
               }
          } catch (FileNotFoundException e) {
               e.printStackTrace();
          }finally {
               try {
                    pw.flush();
                    pw.close();
                    fos.flush();
                    fos.close();
               } catch (IOException e) {
                    e.printStackTrace();
               }
          }
     }
}

