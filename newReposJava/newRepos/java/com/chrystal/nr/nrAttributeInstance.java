package com.chrystal.nr;

public class nrAttributeInstance extends Standard4intType {

    int namespaceID;
    int attributeNameID;
    int attributeValueID;

    int getNamespaceID() {
	return namespaceID;
    };

    int getAttributeNameID() {
	return attributeNameID;
    };

    int getAttributeValueID() {
	return attributeValueID;
    };

    void setNamespaceID(int val){
	namespaceID=val;
    };

    void setAttributeNameID(int val) {
	attributeNameID=val;
    };

    void setAttributeValueID(int val) {
	attributeValueID=val;
    };

    String getDataFieldName0() {
	return ("nameSpaceUriID");
    };

    String getDataFieldName1() {
	return ("attributeNameID");
    };

    String getDataFieldName2() {
	return ("attributeValueID");
    };

    String getTableName() {
	return ("AttributeInstance") ;
    };

    int getDataValue0() {
	return namespaceID;
    };

    void setDataValue0(int dat) {
	namespaceID=dat;
    };

    int getDataValue1() {
	return attributeNameID;
    };

    void setDataValue1(int dat) {
	attributeNameID=dat;
    };

    int getDataValue2() {
	return attributeValueID;
    };

    void setDataValue2(int dat) {
	attributeValueID=dat;
    };

};

