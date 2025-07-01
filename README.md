NewRepos

This is an XML document store. The current implementation is a basic Windows Forms interface. The important bit is the way that document fragments common to multiple files are reused in the data structure.
The data structure is stored as json in C:\temp (written again after every file uploaded).

Potential future work:
======================
1. add working directory for import
2. add working directory for export
3. add pretty print files in import directory (to make round trip comparison easier)
4. add bulk import
5. add bulk export
6. add bulk compare import and export
7. add list repo contents
8. add list files contents

9. add version entry (or autoupdate)
10. add xml diff 
11. add xml visualisation of fragments as styled HTML **
   show all used by in js popup

12. add fragments of attribute lists
   full processing of this would require rewriting prior data with the new updated object
13. add fragments of attribute children
   full processing of this would require rewriting prior data with the new updated object
14. add word extraction from character data
15. add word chain fragments
   full processing of this would require rewriting prior data with the new updated object


