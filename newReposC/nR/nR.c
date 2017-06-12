#include <stdio.h>
#include <string.h>

#define XMLTYPE 0
#define PUBLICDTD 1
#define SYSTEMDTD 2
#define DTD 3	

#define NAMESPACE 4
#define ELEMENTNAME 5
#define ATTRIBUTENAME 6
#define ATTRIBUTEVALUE 7

#define WORD 8
#define WHITESPACE 9

#define PROCESSINGINSTRUCTIONAME 10
#define PROCESSINGINSTRUCTIONVALUE 11

#define numberOfTypes 12
#define maxStringSize 40
#define entriesPerList 30
#define parentStackSize 20
#define childChainDataSize 18
#define childChainPointerNum 10

int stackdbg=0;

/* main system storage */
char stringData[numberOfTypes][entriesPerList][maxStringSize];
int childChainData[childChainDataSize][5];
/* two parts of the child chain structure */
int childChainStartPointer[childChainPointerNum];
int childChainLength[childChainPointerNum];

int objectStackType[parentStackSize];
int objectStackID[parentStackSize];
int objectStackID2[parentStackSize];
int objectStackNSID[parentStackSize];
int objectStackNew[parentStackSize];
int objectStackPointer=0;
/* postincrement predecrement */

void pushObject(int oST, int oSNSID, int oSID, int oSID2, int oSN) {
  if( stackdbg==1 ) printf("pushing %d %d %d %d %d \r\n",oST,oSNSID,oSID,oSID2,oSN);
  objectStackType[objectStackPointer]=oST;
  objectStackID[objectStackPointer]=oSID;
  objectStackID2[objectStackPointer]=oSID2;
  objectStackNSID[objectStackPointer]=oSNSID;
  objectStackNew[objectStackPointer]=oSN;
  objectStackPointer++;
};

void popObject(int *oST, int *oSNSID, int *oSID, int *oSID2, int *oSN) {
  objectStackPointer--;
  if (objectStackPointer<0) {
	printf("negative stack pointer\r\n");
	exit (0);
  };
  *oST=objectStackType[objectStackPointer];
  *oSID=objectStackID[objectStackPointer];
  *oSID2=objectStackID2[objectStackPointer];
  *oSNSID=objectStackNSID[objectStackPointer];
  *oSN=objectStackNew[objectStackPointer];
  if( stackdbg==1 ) printf("popping %d %d %d %d %d \r\n",*oST,*oSNSID,*oSID,*oSID2,*oSN);
};

int objectStackTypeB[parentStackSize];
int objectStackIDB[parentStackSize];
int objectStackID2B[parentStackSize];
int objectStackNSIDB[parentStackSize];
int objectStackNewB[parentStackSize];
int objectStackPointerB=0;
/* postincrement predecrement */

void pushObjectB(int oST, int oSNSID, int oSID, int oSID2, int oSN) {
printf("Bpushing %d %d %d %d %d \r\n",oST,oSNSID,oSID,oSID2,oSN);
  objectStackTypeB[objectStackPointerB]=oST;
  objectStackIDB[objectStackPointerB]=oSID;
  objectStackID2B[objectStackPointerB]=oSID2;
  objectStackNSIDB[objectStackPointerB]=oSNSID;
  objectStackNewB[objectStackPointerB]=oSN;
  objectStackPointerB++;
};

void popObjectB(int *oST, int *oSNSID, int *oSID, int *oSID2, int *oSN) {
  objectStackPointerB--;
  if (objectStackPointerB<0) {
	printf("negative stack pointer\r\n");
	exit (0);
  };
  *oST=objectStackTypeB[objectStackPointerB];
  *oSID=objectStackIDB[objectStackPointerB];
  *oSID2=objectStackID2B[objectStackPointerB];
  *oSNSID=objectStackNSIDB[objectStackPointerB];
  *oSN=objectStackNewB[objectStackPointerB];
printf("Bpopping %d %d %d %d %d \r\n",*oST,*oSNSID,*oSID,*oSID2,*oSN);
};

int NewObjectCreated;  /* global set or reset by lookupOrCreate */

/* no support for entities intially */
void cleanStringData() {
	int i,j,k;
	for(i=0;i<numberOfTypes;i++) 
	  for(j=0;j<entriesPerList;j++)
	    for(k=0;k<maxStringSize;k++) stringData[i][j][k]=0;
	for(i=0;i<childChainDataSize;i++) 
	  for(j=0;j<5;j++) childChainData[i][j]=0;
	for(i=0;i<childChainPointerNum;i++) {
	  childChainStartPointer[i]=0;
	  childChainLength[i]=-1;
	};
};

#define RFILENAME ".//repos.txt"

void saveTables() {
	FILE *f; int i,j,k;
	f=fopen(RFILENAME,"w");
	if (f==NULL) {
		printf("can't open //repos.txt for writing\r\n");
		exit(0);
	};
	printf(".\r\n");
	for(i=0;i<numberOfTypes;i++)
	  for(j=0;j<entriesPerList;j++)
	    fprintf(f,".%s\r\n",&stringData[i][j][0]);
	printf(";\r\n");
	for(i=0;i<childChainDataSize;i++) 
	  for(j=0;j<5;j++) {
	    k=childChainData[i][j];
	    fprintf(f,"%d\r\n", k);
	};
	printf(":\r\n");
	for(i=0;i<childChainPointerNum;i++) {
	  k=childChainStartPointer[i];
	  fprintf(f,"%d\r\n",k);
	};
	printf("o\r\n");
	for(i=0;i<childChainPointerNum;i++) {
	  k=childChainLength[i];
	  fprintf(f,"%d\r\n",k);
	};
	printf("O\r\n");

	fclose(f);
};

void loadTables() {
	char *test;
	FILE *f; int i,j,k; char buf[maxStringSize];
	f=fopen(RFILENAME,"r");
	if (f==NULL) {
		printf("can't open //repos.txt for reading\r\n");
		exit(0);
	};
	printf("-\r\n");
	for(i=0;i<numberOfTypes;i++) {
	  for(j=0;j<entriesPerList;j++) {
	    fscanf(f,"%s",&buf[0]);
	    strcpy(&stringData[i][j][0],&buf[1]);
	    test=&stringData[i][j][0];
	    printf("<%s>\r\n",test);
	  };
	};
	printf("+\r\n");
	for(i=0;i<childChainDataSize;i++) {
	  for(j=0;j<5;j++) {
	    fscanf(f,"%d", &k);
	    childChainData[i][j]=k;
	  };
	};
	printf("=\r\n");
	for(i=0;i<childChainPointerNum;i++) {
	  fscanf(f,"%d",&k);
	  childChainStartPointer[i]=k;
	};
	printf("z\r\n");
	for(i=0;i<childChainPointerNum;i++) {
	  fscanf(f,"%d",&k);
	  childChainLength[i]=k;
	};
	printf("Z\r\n");

	fclose(f);
};

int lookupOrCreate(int objectType,char *text) {
	int j;
	NewObjectCreated=0;
	for(j=0;j<entriesPerList;j++) {
	  if(strcmp(&stringData[objectType][j][0],text)==0) {
		/*printf("found object type %d id %d value %s\r\n",objectType,
		j,text);*/
		break;
	  };
	};
	if (j<entriesPerList) return j;

	NewObjectCreated=1;
	for(j=0;j<entriesPerList;j++) {
	  if(stringData[objectType][j][0]==0) {
	      printf("creating object type %d id %d value %s\r\n",objectType,
	      j,text);
	    strcpy(&stringData[objectType][j][0],text);
	    break;
	  };
	};
	
	if(j==entriesPerList) {
		printf("no space to make new object type %d\r\n",objectType);
		printf("please increase string Entries Size\r\n");
		exit (0);
	} else {
		return j;
	};
};


int processStartElement(char *nameSpaceName, char *elementName) {
	int numCreated=0;
	int nsIdx;
	int enIdx;
	nsIdx=lookupOrCreate(NAMESPACE, nameSpaceName);
	if (NewObjectCreated==1) numCreated++;
	enIdx=lookupOrCreate(ELEMENTNAME, elementName);
	if (NewObjectCreated==1) numCreated++;
	pushObject( ELEMENTNAME, nsIdx, enIdx, 0, ((numCreated>0)?1:0)); 
};

int processAttribute(char *nameSpaceName, char *attributeName,
		char *attributeValue) {
	int numCreated=0;
	int nsIdx;
	int anIdx;
	int avIdx;
	nsIdx=lookupOrCreate(NAMESPACE, nameSpaceName);
	if (NewObjectCreated==1) numCreated++;
	anIdx=lookupOrCreate(ATTRIBUTENAME, attributeName);
	if (NewObjectCreated==1) numCreated++;
	avIdx=lookupOrCreate(ATTRIBUTEVALUE, attributeValue);
	if (NewObjectCreated==1) numCreated++;
	pushObject( ELEMENTNAME, nsIdx, anIdx, avIdx, ((numCreated>0)?1:0)); 
};

int processWord(char *text) {
	int numCreated=0;
	int wdIdx;
	wdIdx=lookupOrCreate(WORD, text);
	pushObject( WORD, wdIdx, 0, 0, ((numCreated>0)?1:0));
};

int processEndElement(char *nameSpaceName, char *elementName) {
	int numCreated=0;
	int nsIdx;
	int enIdx;
	int a,b,c,d,e,i,chainfound,numfound;
	int found=0;
	int numberOfChildren=0;
	nsIdx=lookupOrCreate(NAMESPACE, nameSpaceName);
	if (NewObjectCreated==1) numCreated++;
	enIdx=lookupOrCreate(ELEMENTNAME, elementName);
	if (NewObjectCreated==1) numCreated++;
	if (numCreated>0) {
		printf("EndElement created object(s), must be XML error\r\n");
		exit(0);
	};

printf("end element %d %d %d\r\n",ELEMENTNAME,nsIdx,enIdx);
	
	/* look on stack for start Element */ 
	while (found==0) {
  	  popObject(&a,&b,&c,&d,&e);
	  if ((a==ELEMENTNAME) && (b==nsIdx) && (c==enIdx)) {
	    found=1;
	  } else {
	    pushObjectB(a,b,c,d,e);
	    numberOfChildren++;
	  };
	};	      
	/* tested gets to here ok jd 24.10.04*/

	numfound=0;
	chainfound=-1;

	/* lookupOrCreateChildChainList from stackB */
	for(i=0;i<childChainDataSize;i++) { /* for all chains */
	  int cn,s,l;
	  s=childChainStartPointer[i];
	  l=childChainLength[i];
	  if ((s>=0) && (l==objectStackPointerB)) /* might have to flush stk*/
	  { /* chains with start pointers of -1 are empty */
	    for (cn=0;cn<l;cn++) { /*for each entry*/
	      /* checks this entry in the chain */
	      found=0;
	      if (childChainData[s+i][0]==
		objectStackTypeB[objectStackPointerB+i]) found++;
	      if (childChainData[s+i][1]==
		objectStackIDB[objectStackPointerB+i]) found++;
	      if (childChainData[s][2]==
		objectStackID2B[objectStackPointerB+i]) found++;
	      if (childChainData[s][3]==
		objectStackNSIDB[objectStackPointerB+i]) found++;
	      if (found==4) numfound++;
	      if (numfound==l) { 
		chainfound=i;
		i=childChainDataSize; /* break out of both loops */
	    	cn=l;
	      };
	    }; /*for each entry*/ 
	  }; /* if this chain has data in it*/
	}; /* for all chains */
	     
	if(chainfound>=0) {
	/* write element instance of chain */
	} else {
	/* create new chain */
	/* and write element instance of chain */
	};
	
	/*pushObject the chain! */

};

/*
#define numDocInstances 10
int documentInstanceXMLType[numDocInstances];
int documentInstancePublicDTD[numDocInstances];
int documentInstanceSystemDTD[numDocInstances];
int documentInstanceFirstChild[numDocInstances];
int documentInstanceNumberOfChildren[numDocInstances];

#define numberOfChildObjects 10
int childrenListObjectType[numberOfChildObjects];
int childrenListObjectID[numberOfChildObjects];

#define numElementInstances 10
int ElementInstanceNSID[numElementInstances];
int ElementInstanceENID[numElementInstances];
int ElementInstanceFirstChild[numElementInstances];
int ElementInstanceNumberOfChildren[numElementInstances];
*/

int processStartDocument(char *xmltype, char *publicDtd, char *systemDtd) {
	/* convert to Idx */
	/*push Object */
	int numCreated=0;
	int xmlIdx;
	int pubIdx;
	int sysIdx;
	xmlIdx=lookupOrCreate(XMLTYPE, xmltype);
	if (NewObjectCreated==1) numCreated++;
	pushObject( XMLTYPE, xmlIdx, 0, 0, ((numCreated>0)?1:0)); 

	pubIdx=lookupOrCreate(PUBLICDTD, publicDtd);
	if (NewObjectCreated==1) numCreated++;
	sysIdx=lookupOrCreate(SYSTEMDTD,systemDtd);
	if (NewObjectCreated==1) numCreated++;
	pushObject( DTD, pubIdx, sysIdx, 0, ((numCreated>0)?1:0)); 

};

int processEndDocument() {
	/* look on stack for start Document */
	/* add all children to the ElementChildChainList */
	/* write a document Instance */
};

void main(int argc, char *argv[]) {
	cleanStringData();
	if (argc>1) {
		if (strcmp(argv[1],"clean")==0) {
			printf("saving clean data\r\n");
			saveTables();
			printf("clean data saved ok\r\n");
		};
		if (strcmp(argv[1],"stackverbose")==0) {
			stackdbg=1;
		};
		if (strcmp(argv[1],"stacktest")==0) {
			int a,b,c,d,e,n;
			stackdbg=1;
			n=10;
			a=1;
			b=4;
			c=8;
			d=12;
			e=16;
			for(n=0;n<16;n++) {
			  pushObject(a,b,c,d,e);
		  	  a++;b++;c++;d++;e++;
			};
			for(n=0;n<16;n++) {
			  popObject(a,b,c,d,e);
			};
			exit (0);
		};
	};	
        loadTables();
	printf("tables loaded \r\n");
	processStartDocument("1.0","-//aliensarehere.com//public//en//1.0",
	  "aah.dtd");
	
	processStartElement("ns1a","doc1");
	  processAttribute("ns2","att1","attval1");
	  processAttribute("ns2","att2","attval2");
   	  processWord("test");
	  processWord("by"); 
	    processStartElement("ns3","alarm"); 
	      processAttribute("ns4","att4","EMERGENCY");
	        processWord("emergency-alarm");
	    processEndElement("ns3","alarm");
	  processWord("jeff");
	processEndElement("ns1a","doc1");
	processEndDocument();
	saveTables(); 
};


