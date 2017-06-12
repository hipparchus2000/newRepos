package com.chrystal.nr;

abstract public class Standard4intType{

    int id;

    boolean newlyMade=false;
    boolean error=false;

    abstract String getDataFieldName0();
    abstract String getDataFieldName1();
    abstract String getDataFieldName2();

    abstract String getTableName();

    abstract int getDataValue0();
    abstract void setDataValue0(int dat);
    abstract int getDataValue1();
    abstract void setDataValue1(int dat);
    abstract int getDataValue2();
    abstract void setDataValue2(int dat);

    public int getID() {
	return id;
    };

    public void setID(int newID) {
	id=newID;
    };


    /**returns true if stored successfully
     */
    public boolean store() {
	DataStore datastore=new DataStore();
	boolean success=datastore.store(id,
					getDataValue0(),
					getDataValue1(),
					getDataValue2(),
					getTableName(),
					getDataFieldName0(),
					getDataFieldName1(),
					getDataFieldName2());
	return success;
    };

    /** returns true if found  
     */
    public boolean lookup() {
	DataStore datastore=new DataStore();
	boolean found=datastore.lookup(id,
				       getDataValue0(),
				       getDataValue1(),
				       getDataValue2(),
				       getTableName(),
				       getDataFieldName0(),
				       getDataFieldName1(),
				       getDataFieldName2(),
				       error);
	return found;
    };

    public void findOrStore() {
	if (lookup()==false) {
	    error=!store();
	    newlyMade=true;
	};
    }

};




