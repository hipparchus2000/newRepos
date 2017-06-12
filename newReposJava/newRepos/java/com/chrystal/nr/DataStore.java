package com.chrystal.nr;
import java.util.*;
import java.sql.*;

public class DataStore{

    public boolean store(int id,
		      String DataValue,
		      String TableName,
		      String DataFieldName) 
    {

	Connection conn;
	PreparedStatement st;
	ResultSet rs;

	id=-2;

	try {
	    //String driverName="sun.jdbc.odbc.JdbcOdbcDriver";
	    String driverName="org.gjt.mm.mysql.Driver";
	    //load driver -driverName
	    try {
		Class.forName(driverName).newInstance();
	    } catch (java.lang.ClassNotFoundException ex) {
	    	System.out.println("Class Not Found for odbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.InstantiationException ex) {
	    	System.out.println("jdbc driver Instantiation failed");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.IllegalAccessException ex) {
	    	System.out.println("IllegalAccess Exception when trying to instantiate jdbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    };


	    //Open connection -url -username -password
	    //String url="jdbc:odbc:test";
	    String url="jdbc:mysql://localhost:3306/test";
	    String username="x";
	    String password="x";
	    conn=DriverManager.getConnection(url);//,username,password);

	    //create statement SELECT id from tablename order by (id * -1)
	    String sql_command="SELECT id FROM "+TableName+" ORDER BY (id * -1);";
 	    System.out.println(sql_command);
	    st=conn.prepareStatement(sql_command);
	    
	    //execute statement
	    rs=st.executeQuery();

	    try {
		//find first record of result set
		rs.next();

		//retrieve number
		id=rs.getInt(1);

		//(0) by default - this will be run once at least
		//close connection
		conn.close();
	    } catch (java.lang.NullPointerException ex) {
		//ignore this, it just means there are no records
		System.out.println("ignore this it means no records but NullPointerException");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
	    } catch (Exception ex) {
		//ignore this too
		System.out.println("ignore this Exception it means no records??");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
	    };

	    //increment id
	    id++;
	    
	    //convert id to string
	    Integer idInteger=new Integer(id);
	    String idString=idInteger.toString();

	    System.out.println("preparing to insert a new line into a table");
	    //open connection
	    Connection conn1;
	    conn1=DriverManager.getConnection(url);

	    //create statement INSERT INTO tablename (id,<data|name>) VALUES (id,<data|name>
	    sql_command="INSERT INTO "+TableName+" (id,"+DataFieldName+") VALUES ("+idString+",'"+DataValue+"');";
	    System.out.println(sql_command);
	    st=conn1.prepareStatement(sql_command);
	    
	    //execute statement
	    st.execute();
	    
	    //close connection
	    conn1.close();

	} catch (SQLException ex) {
		System.out.println("SQLException on Inserting row into table");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
	    return false;
	};

	//if the id hasn't been given a different number, then something has gone wrong
	if (id<0) return false;

	//return true if stored successfully.
	return true;
    };

    public boolean lookup(int id,
			  String DataValue,
			  String TableName,
			  String DataFieldName,
			  boolean error) 
    {
	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	boolean found=false;
	id=-1;

	try {
 	    //String driverName="sun.jdbc.odbc.JdbcOdbcDriver";
	    String driverName="org.gjt.mm.mysql.Driver";
	    //load driver -driverName
	    try {
		Class.forName(driverName).newInstance();
	    } catch (java.lang.ClassNotFoundException ex) {
	    	System.out.println("Class Not Found for odbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.InstantiationException ex) {
	    	System.out.println("jdbc driver Instantiation failed");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.IllegalAccessException ex) {
	    	System.out.println("IllegalAccess Exception when trying to instantiate jdbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    };

   
	    //Open connection -url -username -password
	    //String url="jdbc:odbc:test";
	    String url="jdbc:mysql://localhost:3306/test";
	    String username="x";
	    String password="x";
	    conn=DriverManager.getConnection(url);//,username,password);

	    //create statement SELECT id from tablename where <datafieldname> like '<datafieldvalue>'
	    String sql_command="SELECT id FROM "+TableName+" WHERE ("+DataFieldName+" LIKE '"+DataValue+"');";
	    System.out.println(sql_command);
	    st=conn.prepareStatement(sql_command);
	    
	    //execute statement
	    rs=st.executeQuery();

	    //find first record of result set
	    rs.next();
	    
	    //retrieve number
	    id=rs.getInt(1);
	    
	    //(0) by default - this will be run once at least
	    //close connection
	    conn.close();
	} catch (Exception e) {
		System.out.println("Exception when selecting records from database (looking up)");
	    	System.out.println(e.toString());
		e.printStackTrace(System.out);
	    id=-1;
	};

	//return true if found (id is in "id" passed in to function).
	if (id!=-1) {
	    found=true;
	};
	return found;

    };


    //4 int types:

    public boolean store(int id,
			 int DataValue0,
			 int DataValue1,
			 int DataValue2,
			 String TableName,
			 String DataFieldName0,
			 String DataFieldName1,
			 String DataFieldName2) 
    {

	Connection conn;
	PreparedStatement st;
	ResultSet rs;

	id=-2;

	try {
	    //String driverName="sun.jdbc.odbc.JdbcOdbcDriver";
	    String driverName="org.gjt.mm.mysql.Driver";
	    //load driver -driverName
	    try {
		Class.forName(driverName).newInstance();
	    } catch (java.lang.ClassNotFoundException ex) {
	    	System.out.println("Class Not Found for odbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.InstantiationException ex) {
	    	System.out.println("jdbc driver Instantiation failed");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.IllegalAccessException ex) {
	    	System.out.println("IllegalAccess Exception when trying to instantiate jdbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    };

    	    //Open connection -url -username -password
	    //String url="jdbc:odbc:test";
	    String url="jdbc:mysql://localhost:3306/test";
	    String username="x";
	    String password="x";
	    conn=DriverManager.getConnection(url);//,username,password);

	    //create statement SELECT id from tablename order by id descending
	    String sql_command="SELECT id FROM "+TableName+" ORDER BY (id * -1);";
	    System.out.println(sql_command);
	    st=conn.prepareStatement(sql_command);
	    
	    //execute statement
	    rs=st.executeQuery();

	    try {
		//find first record of result set
		rs.next();
	    
		//retrieve number
		id=rs.getInt(1);

	    } catch (java.lang.NullPointerException ex) {
	    	System.out.println("nullPointer exception - no data yet?");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		//no data yet
	    } catch (Exception ex) {
	    	System.out.println("Exception on selecting record to get id for next record ");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		//same?
	    };

	    //(0) by default - this will be run once at least
	    //close connection
	    conn.close();

	    //increment id
	    id++;
	    
	    //convert id to string
	    Integer idInteger=new Integer(id);
	    String idString=idInteger.toString();

	    //convert int data values to strings
	    Integer d0=new Integer(DataValue0);
	    Integer d1=new Integer(DataValue1);
	    Integer d2=new Integer(DataValue2);
	    String sd0=d0.toString();
	    String sd1=d1.toString();
	    String sd2=d2.toString();


	    System.out.println("preparing to insert a line into a table (2)");
	    //open connection
	    Connection conn1;
	    conn1=DriverManager.getConnection(url);

	    //create statement INSERT INTO tablename (id,<data|name>) VALUES (id,<data|name>
	    sql_command="INSERT INTO "+TableName+" (id,"+DataFieldName0+","+DataFieldName1+","+DataFieldName2+
		") VALUES ("+idString+","+sd0+","+sd1+","+sd2+");";
	    System.out.println(sql_command);
	    st=conn1.prepareStatement(sql_command);
	    
	    //execute statement
	    st.execute();
	    
	    //close connection
	    conn1.close();

	} catch (SQLException ex) {
		System.out.println("SQLException on trying to INSERT a row");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
	    return false;
	};

	//if the id hasn't been given a different number, then something has gone wrong
	if (id<0) return false;

	//return true if stored successfully.
	return true;
    };

    public boolean lookup(int id,
			  int DataValue0,
			  int DataValue1,
			  int DataValue2,
			  String TableName,
			  String DataFieldName0,
			  String DataFieldName1,
			  String DataFieldName2,
			  boolean error) 
    {
	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	boolean found=false;
	id=-1;

	try {
 	    //String driverName="sun.jdbc.odbc.JdbcOdbcDriver";
	    String driverName="org.gjt.mm.mysql.Driver";
	    //load driver -driverName
	    try {
		Class.forName(driverName).newInstance();
	    } catch (java.lang.ClassNotFoundException ex) {
	    	System.out.println("Class Not Found for odbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.InstantiationException ex) {
	    	System.out.println("jdbc driver Instantiation failed");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    } catch (java.lang.IllegalAccessException ex) {
	    	System.out.println("IllegalAccess Exception when trying to instantiate jdbc driver");
	    	System.out.println(ex.toString());
		ex.printStackTrace(System.out);
		return false;
	    };

   
	    //Open connection -url -username -password
	    //String url="jdbc:odbc:test";
	    String url="jdbc:mysql://localhost:3306/test";
	    String username="x";
	    String password="x";
	    conn=DriverManager.getConnection(url);//,username,password);

	    //convert int data values to strings
	    Integer d0=new Integer(DataValue0);
	    Integer d1=new Integer(DataValue1);
	    Integer d2=new Integer(DataValue2);
	    String sd0=d0.toString();
	    String sd1=d1.toString();
	    String sd2=d2.toString();

	    //create statement SELECT id from tablename where <datafieldname> like '<datafieldvalue>'
	    String sql_command="SELECT id FROM "+TableName+" WHERE (("+DataFieldName0+"="+sd0+") AND ("+
		DataFieldName1+"="+sd1+") AND ("+DataFieldName2+"="+sd2+"));";
     	    System.out.println(sql_command);
	    st=conn.prepareStatement(sql_command);
	    
	    //execute statement
	    rs=st.executeQuery();

	    //find first record of result set
	    rs.next();

	    //retrieve number
	    id=rs.getInt(1);

	    //(0) by default - this will be run once at least
	    //close connection
	    conn.close();
	} catch (Exception e) {
		System.out.println("Exception on looking up a record ");
		System.out.println(e.toString());
		e.printStackTrace(System.out);
	    id=-1;
	};

	//return true if found (id is in "id" passed in to function).
	if (id!=-1) {
	    found=true;
	};
	return found;

    };


};









