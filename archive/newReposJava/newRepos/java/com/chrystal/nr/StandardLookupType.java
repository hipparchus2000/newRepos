package com.chrystal.nr;

abstract public class StandardLookupType{
    int id;
    boolean newlyMade=false;
    boolean error=false;

    abstract String getDataFieldName();
    abstract String getTableName();
    abstract String getDataValue();
    abstract void setDataValue(String dat);

    public int getID() {
	return id;
    };

    public void setID(int newID) {
	id =newID;
    };


    /**returns true if stored successfully
     */
    public boolean store() {
	DataStore datastore=new DataStore();
	boolean success=datastore.store(id,getDataValue(),getTableName(),getDataFieldName());
	return success;
    };

    /** returns true if found  
     */
    public boolean lookup() {
	DataStore datastore=new DataStore();
	boolean found=datastore.lookup(id,getDataValue(),getTableName(),getDataFieldName(),error);
	return found;
    };

    public void findOrStore() {
	if (lookup()==false) {
	    error=!store();
	    newlyMade=true;
	};
    }

};


