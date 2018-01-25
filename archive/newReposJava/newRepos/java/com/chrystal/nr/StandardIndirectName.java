package com.chrystal.nr;

abstract public class StandardIndirectName extends StandardLookupType{
    int id;
    String name;

    String getDataFieldName() {
	return ("name");
    };

    void setDataValue(String dat) {
	name=dat;
    } 

    String getDataValue() {
	return (name);
    };

};
