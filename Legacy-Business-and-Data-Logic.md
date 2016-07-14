Discover the business/data rules and processing logic of the CWS/CMS application.

The CWS/CMS application allows Child Welfare Services agents to record and track case information related to their charter.  It is, in the abstract, a forms/reports tracking client/server application.

This document is intended to describe methods used in the extraction of business/data rules from the CWS/CMS application.  The CWS/CMS application is documented by DocTool which will be the primary tool used to extract business/data rules.  Other tools and methods will be used and documented as they are required.

#DocTool for CWS/CMS

##DocTool Description

##DocTool Database

### Access
DocToll can be found at: http://10.210.2.23/doctool.htm


##DocTool Objects
Objects in DocTool represent major categories of organization within the CMS/CWS application.

###Notebook Object
Notebooks are containers of information and rules in the CWS/CMS application.  It is the organizational basis of the application.
Notebooks contain the name, description/help text, class (form, dialog), primary subject area, and primary Entity.
Notebooks drill down the **Pages** associated with the form.

###Entity Object
Entities represent tables or views within the logical data model of CMS/CWS.
Entities contain the name, physical name, DPD name, DeclGen, and persistence (database, view, form).
Entities drill down to **Attributes** (columns) associated with table, **DPD attribute**s, **Data Access Packages** (DAPs) associated with the Entity.

###Rule Object
Rules represent business or data processing logic contained within the CWS/CMS application.
Rules contain the Business Area, Primary Entity, Rule Text, Access Logic, Class (business, user interface, access path), location (workstation, host, both), and Implementation Text.
Rules drill down to a list of **Controls**, **Elementary Processes**, **Data Access Programs** (DAPs), **Errors**, and **Test Cases** mapped to the rule

###Elementary Process Object
Elementary processes describe a specific action provided by the CWS/CMS application.
Elementary processes contain the type (function, process, elementary process) and Implementation text.
Elementary processes drill down to a list of **Rules** and **Pages** mapped to the process.

###DAP Object
Data Access Packages (DAPs) describe the business and data rules pertaining to an **Entity**.
DAPs contain the package name and parent Entity.
DAPs drill down to a list of **Rules** mapped to the DAP.

###Error Object
Errors represent conditions detected during processing within the CWS/CMS application.
Errors contain the name, field constant value, text, category, host action, workstation action, and implementation notes.
Errors drill down to **Rules** and **Controls** mapped to the Error.

###Code Table Object
Code tables represent system and user data tables within the CWS/CMS application.
Code tables contain the table name, DSD name, Logical ID's, long description, short description, Allows category (yes/no), Maintained by (System or User), and default values.
Code tables drill down to **Categories**, Code Table Values, and **Attributes** mapped to the Code Table.

###Test Case Object
Test cases represent testing scenario's designed to validate Rules as applied to Controls.
Test Cases contain a test case number and comment
Test cases drill down to **Controls** and **Rules** mapped to the Test case.

##Other DocTool Objects

###Attributes Object
Attributes represent rows or fields within the logical data model of the CWS/CMS application.
Attributes contain name, type (length, decimal, avg len, percent, allows nulls), physical name, parent entity, special processing (timestamp, user id, fix value, none), and default value.
Attributes drill down to **DPD**, **Controls**, and **Code tables** mapped to the Attribute.

###Pages Object
Pages represent a screen or form within the CWS/CMS application.
Pages contain the name, caption, Implementation text, form name, and a screen shot.
Pages drill down to **Controls**, **Labels**, and **Elementary Processes** associate with the Pages.

###Controls and Labels Object
Controls are contained of forms/screens of the CWS/CMS application.
Controls contain Notebook name, Page name, Control name, caption, implementation notes, Help context ID, Class, and type (control, menu item, or frame).
Controls drill down to **Rules**, **Attributes**, **Errors**, and **Test Cases** mapped to the Control.



#DocTool Tabs

##Queries
This screen will allow SQL type queries to the DocTool database.
It has predefined queries.

##Rules by Notebook
This screen will allow one to scroll through the Rules associated with a given Notebook.

##Audit Log
Display audit logs associated with any of the DocTool objects.
