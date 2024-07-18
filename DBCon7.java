package Test;


import java.sql.*;
import java.util.*;


public class DBCon7 
{
 public static void main(String[] args) 
{
 Scanner s = new Scanner(System.in);
 try(s;)
 {
	 try 
	 {
		 Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","system","Qazmlp01");
		 System.out.println("Status of AutoCommit :"+con.getAutoCommit());
		 con.setAutoCommit(false);
		 System.out.println("Status of AutoCommit :"+con.getAutoCommit());
		 
		 PreparedStatement ps1 = con.prepareStatement("select * from Bank52 where ACCNO=?");
		 PreparedStatement ps2 = con.prepareStatement("update Bank52 set bal=bal+? where ACCNO=?");
		 
		 Savepoint sp = con.setSavepoint();
		 System.out.println("Enter the Home ACCNO:=?");
		 
		 long hACCNO = s.nextLong();
		 ps1.setLong(1, hACCNO);
		 ResultSet rs1 = ps1.executeQuery();
		 
		 if(rs1.next())
		 {
			 float bl = rs1.getFloat(3);
			 System.out.println("Enter The Beneficiery ACCNO:");
			 long bACCNO = s.nextLong();
			 ps1.setLong(1, bACCNO);
			 ResultSet rs2 = ps1.executeQuery();
			 
			 if(rs2.next())
			 {
				 System.out.println("Enter The Amount To Be Transfered");
				 float amt = s.nextFloat();
				 if (amt<=bl)
				 {
					 ps2.setFloat(1, -amt);
					 ps2.setLong(2, hACCNO);
					 int i = ps2.executeUpdate();
					 
					 ps2.setFloat(1, amt);
					 ps2.setLong(2, bACCNO);
					 int j =ps2.executeUpdate();
					 
					 if(i==1 && j==1)
					 {
						 System.out.println("Transaction Succesfull");
						 con.commit();                                 //Update The DataBase
					 }
					 else
					 {
						 System.out.println("Transaction failed");
						 con.rollback(sp);
					 }
				   } else
					 {
						 System.out.println("Insufficient Funds");
					 }
			       }else
			        {
				         System.out.println("Invalid bACCNO");
			        } 
			
			       }else
					 {
						 System.out.println("Invalid homeACCNO");
						 
					 }
		 
	 }

	 catch(Exception e) {e.printStackTrace();}
 
 
        }
 
	 
    }
	 
	 
}
	

