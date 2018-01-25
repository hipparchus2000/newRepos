package com.chrystal.nr;

abstract public class StandardIndirectData extends StandardLookupType{
    int id;
    String data;
  
    String getDataFieldName() {
	return ("data");
    };

    void setDataValue(String dat) {
	data=dat;
    };

    String getDataValue() {
	return (data);
    };

};
