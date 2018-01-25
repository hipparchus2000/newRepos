package com.chrystal.nr;

public class nrElementChildInstance extends Standard4intType {

    int elementInstanceID;
    int childSequenceNum;
    int childType;

    String getDataFieldName0() {
	return("elementInstanceID") ;
    };

    String getDataFieldName1() {
	return("childSequenceNum");
    };

    String getDataFieldName2() {
	return("childType");
    };

    String getTableName() {
	return("ElementChildInstance");
    };

    int getDataValue0() {
	return elementInstanceID;
    };

    void setDataValue0(int dat) {
	elementInstanceID=dat;
    };

    int getDataValue1() {
	return childSequenceNum;
    };

    void setDataValue1(int dat) {
	childSequenceNum=dat;
    };

    int getDataValue2() {
	return childType;
    };

    void setDataValue2(int dat) {
	childType=dat;
    };

};
