# GoogleEarthGPS
The library reads geographic data from CSV type files into Java GIS objects, those objects can be manipulated by different types of classes and functions, at the end, there's the ability to convert those very GIS objects into KML file which is readable my the Google Earth application. <br> <br>

## GIS: 
*What is GIS (Geographic information system)? <br>*
please read https://en.wikipedia.org/wiki/Geographic_information_system <br> <br>

## Google earth:
*What is Google Earth? <br>*
Google earth is an application for viewing the globe from above with satelites images. <br>
please see https://www.google.com/intl/iw/earth/ <br> <br>

## This library uses: 
The **"Csv2Kml"** class:
this class is a static class contains functions to convert from CVS files to GIS objects and from GIS objects into KML files. 
it uses the **"CsvReader"** class to read Csv files and convert them into GIS objects, **"MultiCSV"** to read an entire directory of CSV files and convert them into a complete GIS project object and **"BuildKml"** class to convert those GIS objects into KML files. <br>

## What does this library contains?
1. **"Coords"** package:
   -  "MyCoords" class -> for converting and manipulating polar/geodetic 	coordinates, such as moving a point by a vector, calculate distances and more. <br>
2. **"Geom"** package, contains geometry elements:
	- "Point3D" class -> for representing a point in space or on map using both polar coordinates or certezian.
3. **"File_formats"** package, for manipulating CSV and KML files:
	- "CsvReader" static class -> for reading CSV files into GIS-type objects, see GIS package for reference.
	- "BuildKml" static class -> for generating / building / creating a KML file that can be read by Google Earth from GIS-type objects, see GIS package for reference.
	- "Csv2Kml" static class -> class that uses both of the above classes along with the "MultiCSV" static class in the algorithms package to easily manipulate files from CSV into GIS-type objects and from GIS-type objects into KML in a single stop.
4. **"Algorithms"** package:
	- "MultiCSV" static class -> reading multpile CSV files from given directory and it's sub-directories into a GIS_project object.
5. **"GIS"** package:
	- "GISElement" class -> representing a single GIS element which contains a Geomtery element along with its meta data, (mostly a point, a single CSV row in file)
	- "GISLayer" class -> a set of multiple GISElements, (mostly a single CSV file)
	- "GISProject" class -> a set of multiple GISLayers, can be exported to KML using described classes in "File_formats" package.
	- "MetaData" class -> object contains the metadata of GIS-type objects.

## How to use?
#### Generating GIS-type objects from CSV files on the file system.
Read from CSV file into GIS_layer object using the CsvReader or Csv2Kml static classes from File_formats package.
```java
// ok style: using the CsvReader class directly
GISLayer layer = CsvReader.read("src/Data/WigleWifi_20171201110209.csv");

// prefered style: using the Csv2Kml class 
GISLayer layer = Csv2Kml.readCSVFile("src/Data/WigleWifi_20171201110209.csv");
```
Read multiple CSV files from a directory path into GIS_project object using the MultiCSV or Csv2Kml static classes from File_formats package.
```java
// ok style: using the MultiCSV class directly
GISProject project = MultiCSV.readDirectory("src/Data/");

// prefered style: using the Csv2Kml class 
GISProject project = Csv2Kml.readCSVDirectory("src/Data/");
```
*Note: all read operations are relative to current application run origin.*
#### Using GIS-type elements
**"GISElement"**-> the basic object that holds data about coordinates.
```java
//TODO: DESCRIPTION ABOUT GIS-TYPE ELEMENTS
```
#### Building a KML file from GISProject object.
Example on how to export GISProject as KML file.
The syntax:
```java
// ok style: using the BuildKml class directly
BuildKml.create(GIS_project project, String filename);

// better style: using the BuildKml class directly
MultiCSV.createKML(GIS_project project, String filename);

// prefered style: using the Csv2Kml class 
Csv2Kml.createKML(GIS_project project, String filename);
```
*Note: filename includes relative path + file name + file extension*
Example:
```java
// ok style: using the BuildKml class directly
BuildKml.create(project, "src/Data/myKML.kml");

// better style: using the BuildKml class directly
MultiCSV.createKML(project, "src/Data/myKML.kml");

// prefered style: using the Csv2Kml class
Csv2Kml.createKMLFile(project, "src/Data/myKML.kml");
```
Here we see: 
	-"src/Data/" as relative path.
	-"myKML" as file name.
	-".kml" as file extension.

