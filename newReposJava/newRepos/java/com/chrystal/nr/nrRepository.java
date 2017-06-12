package com.chrystal.nr;
import java.util.*;
import java.io.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class nrRepository{

    private static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";
    

    //functions to add
    //whereused?

    //add static main function that takes arguments
    //for checkin and checkout from the command line

    public int checkin (String filename, 
			int ancestorID,
			int ancestorRelationshipID,
			String propNames[],
			String propValues[])
    {
	System.out.println("Checkin starting..");
	int ret=-1;
	File f=new File(filename);
	try {
	    FileInputStream fis=new FileInputStream(f);
	    ret=checkin ((InputStream)fis, 
			 ancestorID,
			 ancestorRelationshipID,
			 propNames,
			 propValues);
	} catch (IOException ex) {
	    //error handling in here
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	};

	return ret;
 
    }; 

    public int checkin (InputStream ins, 
			int ancestorID,
			int ancestorRelationshipID,
			String propNames[],
			String propValues[])
    {
	System.out.println("starting checkin parser");
	//do it in here
	XMLReader parser;
	checkinSAXParser sp=new checkinSAXParser();

	try {
	    parser=(XMLReader)Class.forName(DEFAULT_PARSER_NAME).newInstance();
	} catch (java.lang.ClassNotFoundException ex) {
	    //handle exception in here
	    System.out.println("ClassNotFound (XMLReader)");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	} catch (java.lang.InstantiationException ex) {
	    //handle exception in here
	    System.out.println("InstantationException (XMLReader)");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	} catch (java.lang.IllegalAccessException ex) {
	    //handle exception in here
	    System.out.println("IllegalAccessException (XMLReader)");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	};

	System.out.println("setting content handler");
	parser.setContentHandler(sp);

	System.out.println("setting error handler");
	parser.setErrorHandler(sp);

	try {
		System.out.println("setting Features of parser");
	    parser.setFeature( "http://xml.org/sax/features/validation",false);
	    parser.setFeature( "http://xml.org/sax/features/namespaces",true);
	    parser.setFeature( "http://apache.org/xml/features/validation/schema",false);
	    parser.setFeature( "http://apache.org/xml/features/continue-after-fatal-error", true);
	} catch (org.xml.sax.SAXNotRecognizedException ex) {
	    //exception handler in here
	    System.out.println("SAXNotRecognizedException");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	} catch (org.xml.sax.SAXNotSupportedException ex) {
	    //exception handler in here
	    System.out.println("SAXNotSupportedException");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	};

	System.out.println("creating org.xml.sax.InputSource");
	org.xml.sax.InputSource istore;
	istore=new org.xml.sax.InputSource(ins);

	System.out.println("about to parse the inputsource");
	
	try {
	    parser.parse(istore);
	} catch (IOException ex) {
	    //handle exception
	    System.out.println("IOException parsing inputsource");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	} catch (SAXException ex) {
	    //handle exception
	    System.out.println("SAXException parsing inputsource");
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	    return -1;
	};

	System.out.println("About to retrieve node tree");

	//retrieve tree
	nrNode tree=sp.treeRoot;

	//if documentID returned (not -1) then parse properties,
	//insert them into the properties tables
	//and add a row for this document.


	//return is DocumentID, or -1 for error.
	return -1;
    };



    public boolean checkout (int DocumentID, String filename) {
	boolean success=false;
	File f=new File(filename);
       
	try {
	    FileOutputStream outs=new FileOutputStream(f);
	    success=checkout(DocumentID, (OutputStream) outs);
	} catch (IOException ex) {
	    //do error routine
	    System.out.println(ex.toString());
	    ex.printStackTrace(System.out);
	};

	return success;
	
    };


    public boolean checkout (int DocumentID, OutputStream outs) {
	// Document ID found from browsing the tables somehow, perhaps
	//through web page via SQL tag library?
	//return value is success (true or false).
	boolean success=false;

	//do it in here


	return success;
    };


    private static int convertRelationshipIDStringToInt (String s) {

	if (s.compareTo("original")==0) {
	    return 0;
	};

	if (s.compareTo("fork")==0) {
	    return 1;
	};

	if (s.compareTo("translation")==0) {
	    return 2;
	};

	if (s.compareTo("newversion")==0) {
	    return 3;
	};

	if (s.compareTo("transformation")==0) {
	    return 4;
	};

	return -1;

    };

    private static int convertCommandToInt (String s) {
	if (s.compareTo("checkin")==0) {
	    return 0;
	};

	if (s.compareTo("checkout")==0) {
	    return 1;
	};

	return -1;
    };

    private static void printHelp() {
	System.out.println("com.chrystal.nr.nrRepository:main");
	System.out.println("You must specify at least 3 parameters");
	System.out.println("1. command (checkin, checkout)");
	System.out.println("2. filename to check in");
	System.out.println("3. ID of ancestor document (or 0)");
	System.out.println("4. relationship of document with ancestor ('original'|'fork'|'translation'|'newversion'|'transformation'");
	System.out.println("");
	System.out.println("example: <reposcommand> checkin xyz.xml 0 original");
	System.out.println("");
	System.out.println("You can also then pass Properties using -D<propertyname>=<value>");
	System.out.println("");
	System.out.println("example: <reposcommand> checkin xyz.xml 0 original -Dauthor='Davies,Stephen Jeffrey' -Dauthor='Davies, Michael Jonathan'");
	System.out.println("note#1: in the above example, two property entries are generated with property name 'author'");
	System.out.println("note#2: for checkout, use <reposcommand> checkout <filename to checkout to> <id-to-checkout> original");
	System.out.println("      'original' has no purpose in checkout as yet, but it must be included");

    };


    /** Main program entry point. */
    public static void main(String argv[]) {

	if (argv.length<4) {
	    printHelp();
	    return;
	};


	String stringCommand=argv[0];
	int command=convertCommandToInt(stringCommand);
	if (command==-1) { //command not recognised
	    System.out.print("ERROR: Command string not recognised, you entered - ");
	    System.out.println(stringCommand);
	    printHelp();
	    return;
	};

	String filename=argv[1];
	String stringAncestorID=argv[2];
	//convert string to integer.
	int ancestorID=Integer.parseInt(stringAncestorID);

	String stringAncestorRelationshipID=argv[3];
	int ancestorRelationshipID=-1;
	System.out.println("Relationship Entered is ");
	System.out.println(stringAncestorRelationshipID);
	String parsedStringAncestorRelationshipID="";
	for (int ch=0; ch<stringAncestorRelationshipID.length(); ch++) {
		char charStr=stringAncestorRelationshipID.charAt(ch);
		if (charStr==13) {
			//do nothing
		} else { 
			parsedStringAncestorRelationshipID+=stringAncestorRelationshipID.charAt(ch);
		};
	};
			

        ancestorRelationshipID=convertRelationshipIDStringToInt(parsedStringAncestorRelationshipID);
	if (ancestorRelationshipID==-1) { //relationship string not recognised
	    System.out.println("ERROR: Relationship string not recognised, you entered - ");
	    System.out.print(stringAncestorRelationshipID);
	    System.out.println("]");
	    printHelp();
	    return;
	};


	//to do: process properties
	String propNames[]=null;
	String propValues[]=null;
	nrRepository r=new nrRepository();

	switch (command) {
	case 0: { //checkin
	    int result=r.checkin (filename, ancestorID, ancestorRelationshipID, propNames, propValues);
	    if (result!=-1) {
		System.out.println("Checkin Successful:");
		System.out.print("Document ID is: ");
		System.out.println(result);
	    } else {
		System.out.println("Checkin was unsuccessful");
	    };
	}; break;
	case 1: { //checkout
	    boolean success=r.checkout (ancestorID, filename);
	}; break;
	//further commands in here
	};


    };


};















