package com.chrystal.nr;
import java.util.*;

public class nrElementInstance extends Standard4intType {

    int elementNameID;
    int namespaceID;
    int childrenIDsChecksum;

    String getDataFieldName0() {
	return("elementNameID");
    };

    String getDataFieldName1() {
	return("namespaceID");
    };

    String getDataFieldName2() {
	return("childrenIDsChecksum");
    };

    String getTableName() {
	return("ElementInstance");
    };

    int getDataValue0() {
	return elementNameID;
    };

    void setDataValue0(int dat) {
	elementNameID=dat;
    };

    int getDataValue1() {
	return namespaceID;
    };

    void setDataValue1(int dat) {
	namespaceID=dat;
    };

    int getDataValue2() {
	return childrenIDsChecksum;
    };

    void setDataValue2(int dat) {
	childrenIDsChecksum=dat;
    };

};

