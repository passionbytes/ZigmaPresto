# ZigmaPresto - Java Swing Based Query Builder for PrestoDB
### Presented at PrestoCon - 2021 by Ravishankar Nair

#### Here is the recorded video presentation : [PrestoCon2021][mylink]

Based on open source [SQLLeo][df1], ZigmaPresto is a Java Swing UI which allows drag and drop query builder from a single canvas, thus generating query for unified data. The canvas can show all tables/views from the disparate data sources configured as PrestoDB catalogs.

- All data sources in one screen
- Just select and join across

## Features

- Allows catalog.schema.table support
- Drag and drop attributes across the selected tables within the canvas 
- Import and save files from the resulting query
- Generate profiling of unified data
- Export the unified data to CSV for further consumption



## Installation

ZigmaPresto requires [Java 11][Java11] or higher to compile and run.

Step 1: Install the dependencies and devDependencies and start the server. Install Python > 3.x, after create a venv or conda environment. 
You can install using the pip package manager by running

```sh
pip install pandas-profiling==2.9.0
```
Activate the virtual environment. This Python dependency is for generating the profiled output.
Step 2: Clone the repo and 

```sh
cd ZigmaPresto
mvn clean install
java -jar prestoqb-1.0.jar
```

Thats all..enjoy...

## License

[LGPL][LGPL]

**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

  
   [df1]: <https://sqleo.sourceforge.io/>
   [Java11]: <https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>
   [LGPL]: <https://www.gnu.org/licenses/old-licenses/lgpl-2.0.en.html>
   [mylink]: <https://www.youtube.com/watch?v=Idtc7t93Q00&list=PLJVeO1NMmyqUDkrabo6CRGQ7zNTOMvu2L&index=18>
  

