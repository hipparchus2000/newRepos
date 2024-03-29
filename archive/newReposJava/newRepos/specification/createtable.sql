create table DocumentInstance(
id int,
rootElementID int,
ancestorDocID int,
ancestorRelationshipID int);

create table PropertyInstance(
id int,
DocumentInstanceID int,
propertyNameID int,
propertyValueID int);

create table PropertyName(
id int,
name varchar(255));

create table Propertyvalue(
id int,
data varchar(255));

create table ElementInstance(
id int,
elementNameID int,
namespaceID int,
childrenIDsChecksum int);

create table ElementName(
id int,
name varchar(255));

create table ElementChildInstance(
elementInstanceID int,
childSequenceNum int,
childType int,
childID int);

create table NameSpace(
id int,
data varchar(255));

create table Word(
id int,
data varchar(255));

create table Whitespace(
id int,
data varchar(255));

create table AttributeName(
id int,
name varchar(255));

create table AttributeValue(
id int,
data varchar(255));

create table AttributeInstance(
id int,
namespaceID int,
attributeNameID int,
attributeValueID int);








