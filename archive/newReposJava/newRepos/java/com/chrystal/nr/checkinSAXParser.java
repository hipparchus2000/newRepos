package com.chrystal.nr;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Takes source xml file, converts into a node tree, storing new words, attributes and elements
 * as it goes.
 *
 * author: Jeff Davies
 * version: 1.0
 */

public class checkinSAXParser extends DefaultHandler {

    public nrNode treeRoot=new nrNode();
    private boolean rootNode=false;
    private nrNode n=null; //n is the current node


    public void startDocument() {
	//set root node here
	System.out.println("checkinSAXParser:startDocument setting rootNode to true"); 
	rootNode=true;
    };

    public void startElement(String uri, String local, String raw, Attributes attrs) {

	System.out.print("checkinSAXParser:startElement ");
	System.out.print(uri);
	System.out.print(" ");
	System.out.println(local);

	//make a new node to represent this Element
	n=new nrNode();
	if (rootNode) {
	    n=treeRoot;
	    rootNode=false;
	} else {
	    n=new nrNode();
	};

	n.setNodeTypeID(nrNode.TypeElementInstance);

	nrNameSpace ns=new nrNameSpace();
	ns.setDataValue(uri);
	ns.findOrStore();
	n.setUriID(ns.id);
	
	nrElementName el=new nrElementName();
	el.setDataValue(local);
	el.findOrStore();
	n.setNameID(el.id);
             
	System.out.print("Processing ");
	System.out.print(attrs.getLength());
	System.out.println(" attributes on this element");
	
	for(int attrnum=0;attrnum<attrs.getLength();attrnum++) {
	    //com.chrystal.nr.Attribute att;
	    String localname=attrs.getLocalName(attrnum);
	    String auri=attrs.getURI(attrnum);
	    String attrval=attrs.getValue(attrnum);

	    //TO DO :
	    //create child nodes
	    //get uri from each attribute
	    nrAttributeInstance ai=new nrAttributeInstance();

	    nrAttributeName an=new nrAttributeName();
	    an.setDataValue(localname);
	    an.findOrStore();
	    ai.setAttributeNameID(an.id);

	    nrNameSpace ans=new nrNameSpace();
	    ans.setDataValue(auri);
	    ans.findOrStore();
	    ai.setNamespaceID(ans.id);

	    nrAttributeValue av=new nrAttributeValue();
	    av.setDataValue(attrval);
	    av.findOrStore();
	    ai.setAttributeValueID(av.id);

	    ai.findOrStore();
	    
	    nrNode anode=new nrNode();
	    anode.setNodeTypeID(nrNode.TypeAttributeInstance);
	    anode.setUriID(ans.id);
	    anode.setNameID(an.id);
	    anode.setValueID(av.id);

	    anode.parent=n;
	    n.children.add(anode);

	};

		
    }

    public void characters(char ch[], int start, int length) {

	System.out.print(length);
	System.out.println(" characters in the current element");

	//must break this into whitespace and word lumps, and create nodes for each.


	int mode=0; //1 is whitespace 2 is word
	int lastmode=0;
	String whitespace="";
	String word="";

	for (int i=start;i<length;i++) {
	    char c[]=new char[1];
	    c[0]=ch[i];
	    String cs=new String(c);
	    System.out.print(cs);
	    
	    //look for whitespace
	    if ((cs.compareTo("\t")==0)|
		(cs.compareTo(" ")==0)|
		(cs.compareTo("")==0)|
		(cs.compareTo("\n")==0)|
		(cs.compareTo("\r")==0)) {
		mode=1;
	    } else {
		mode=2;
	    };

	    //was whitespace, now word
	    if ((lastmode==1) && (mode==2)) {
		// lookupOrStore the whitespace,
		nrWhitespace w=new nrWhitespace();
		w.setDataValue(whitespace);
		w.findOrStore();

		//add to children of current node
		nrNode wn=new nrNode();
		wn.setNodeTypeID(nrNode.TypeWhitespace);
		wn.setNameID(w.id);
		wn.parent=n;
		n.children.add(wn);

		// null the whitespace
		whitespace="";

		// add current character to word.
		word=word+cs;
	    };

	    //was word, now whitespace
	    if ((lastmode==2) && (mode==1)) {
		// lookupOrStore the word,
		nrWord w=new nrWord();
		w.setDataValue(word);
		w.findOrStore();

		//add to children of current node
		nrNode wn=new nrNode();
		wn.setNodeTypeID(nrNode.TypeWord);
		wn.setNameID(w.id);
		wn.parent=n;
		n.children.add(wn);

		// null the word
		word="";

		// add current character to whitespace.
		whitespace=whitespace+cs;
	    };

	    //was whitespace or nothing, now whitespace
	    if (((lastmode==1)||(lastmode==0)) && (mode==1)) {
		// add current character to whitespace
		whitespace=whitespace+cs;
	    };

	    //was word or nothing, now word
	    if (((lastmode==2)||(lastmode==0)) && (mode==2)) {
		//add current character to word
		word=word+cs;
	    };
	    
	}; //end character loop

	System.out.println();
	
    }; //end function




    public void endElement(String uri, String localpart, String raw) throws SAXException {
	
	System.out.print("checkinSAXParser:endElement ");
	System.out.print(uri);
	System.out.print(" ");
	System.out.print(localpart);
	System.out.println("   is ending");
	
	//evaluate checksum

	n=n.parent;

    };

};











