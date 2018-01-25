package com.chrystal.nr;

public class nrPropertyInstance extends Standard4intType {

    int DocumentInstanceID;
    int propertyNameID;
    int propertyValueID;

    String getDataFieldName0() {
	return("DocumentInstanceID") ;
    };

    String getDataFieldName1() {
	return("propertyNameID");
    };

    String getDataFieldName2() {
	return("propertyValueID");
    };

    String getTableName() {
	return("PropertyInstance");
    };

    int getDataValue0() {
	return DocumentInstanceID;
    };

    void setDataValue0(int dat) {
	DocumentInstanceID=dat;
    };

    int getDataValue1() {
	return propertyNameID;
    };

    void setDataValue1(int dat) {
	propertyNameID=dat;
    };

    int getDataValue2() {
	return propertyValueID;
    };

    void setDataValue2(int dat) {
	propertyValueID=dat;
    };

};
