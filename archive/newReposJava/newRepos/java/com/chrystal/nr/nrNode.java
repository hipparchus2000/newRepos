package com.chrystal.nr;

import java.util.*;

public class nrNode {

    public final static int TypeAttributeInstance=0;
    public final static int TypeElementInstance=1;
    public final static int TypeWhitespace=2;
    public final static int TypeWord=3;

    private int nodeTypeID=-1;
    private int uriID=-1;
    private int nameID=-1;
    private int valueID=-1;

    //always set, so you know what this is in the tree
    public void setNodeTypeID(int id) {
	nodeTypeID=id;
    };

    //set for attributes and elements
    public void setUriID(int id) {
	uriID=id;
    };
	
    //set for attributes and elements
    public void setNameID(int id) {
	nameID=id;
    };

    //set for attributes, words and whitespace
    public void setValueID(int id) {
	valueID=id;
    };


    //always set, so you know what this is in the tree
    public int getNodeTypeID() {
	return nodeTypeID;
    };

    //set for attributes and elements
    public int getUriID() {
	return uriID;
    };
	
    //set for attributes and elements
    public int getNameID() {
	return nameID;
    };

    //set for attributes, words and whitespace
    public int getValueID() {
	return valueID;
    };

    public Vector children=new Vector(); //they are nrNodes
    public nrNode parent=null;

}








