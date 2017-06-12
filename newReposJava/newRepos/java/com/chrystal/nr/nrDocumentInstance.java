package com.chrystal.nr;

public class nrDocumentInstance extends Standard4intType {

    int RootElementID;
    int ancestorDocID; //ancestor is 0 for original

    //ancestor relationship is one of
    // original (0) fork, newVersion, translation, transformation
    int ancestorRelationshipID;

    String getDataFieldName0() {
	return("rootElementID");
    };
    String getDataFieldName1() {
	return("ancestorDocID");
    };
    String getDataFieldName2() {
	return("ancestorRelationshipID");
    };

    String getTableName() {
	return ("DocumentInstance");
    };

    int getDataValue0() {
	return RootElementID;
    };

    void setDataValue0(int dat) {
	RootElementID=dat;
    };

    int getDataValue1() {
	return ancestorDocID;
    };

    void setDataValue1(int dat) {
	ancestorDocID=dat;
    };

    int getDataValue2() {
	return ancestorRelationshipID;
    };

    void setDataValue2(int dat) {
	ancestorRelationshipID=dat;
    };

};





















