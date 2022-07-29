# README #

Repository for simple Java API for static HTML chart development

The goal is to have simple wrapper around:
1. Google Charts (Javascript) https://developers.google.com/chart
2. Plotly JS (https://plotly.com/javascript/is-plotly-free/) MIT license 
3. d3js (ISC license, which is MIT like, see: https://github.com/d3/d3/blob/main/LICENSE)
4. d3-force-3d (https://github.com/vasturiano/d3-force-3d) under MIT license

and optionally around:
5. 3DMolJS (https://3dmol.csb.pitt.edu/doc/tutorial-code.html) under BSD Clause 3 license
6. MolStar (https://github.com/molstar/molstar/blob/master/LICENSE) under MIT license
7. UglyMol (https://github.com/uglymol/uglymol/blob/master/LICENSE) under MIT license
8. PV (https://biasmv.github.io/pv/) under MIT license

Why not use something like TableSaw (https://github.com/jtablesaw/tablesaw)? Because we want to reduce dependency on external libraries (particularly as TableSaw has a BUNCH of external inputs)

## Scope 

### Initial Charts Types to Support

1. Pie Chart
2. Line Chart
3. Bar Chart (horizontal and vertical)
4. Scatter Plot
5. Scatter Plot Matrix
6. Histogram
7. Heat Map
8. Bubble Chart

### Subsequent A - Visualizations to Support (FUTURE MILESTONE)

1. Support above eight chart types using d3js library
2. Support force based graph visualization (in 2D) using d3js
3. Support force based graph visualization (in 3D) using d3js 
4. Support force based graph visualization (in 2D) using d3-force-3d
5. Support force based graph visualization (in 3D) using d3-force-3d 

### Subsequent B - Visualizations to Support (FUTURE MILESTONE)

1. TreeLayout support d3js

### Subsequent C - Visualizations to Support 3DMolJS

1. Java API that specifies multiple molecules for visualization

        Each molecule is specified by a collection of X,Y,Z cartesian coordinates along with an element name (e.g. C, H, O, etc), and an optional information string
        API should take in a collection of such molecules for visualization
        A self contained simple HTML file should be generated for the visualization
        
2. Java API that specifies multiple RNA (A, C, G, U) or DNA (A, C, G, T) strings for the following

        linear textual visualization side by side
        linear textual visualization side by side highlighting matching areas
        linear structural visualization side by side

3. Java API that generates a self contained HTML file for viewing of a SET of molecules using

        A: http://gjbekker.github.io/molmil/
        B: http://3dmol.csb.pitt.edu 
            http://3dmol.csb.pitt.edu/doc/tutorial-embeddable.html
            http://3dmol.csb.pitt.edu/doc/index.html
        C: https://molstar.org/viewer/    
                        

## Architectural Directives

Keep it simple, simple, simple....

### PRIME DIRECTIVE #1

Google Chart, Plotly JS, d3js  must ONLY be incorporated via *CDN* for these generated files.

    https://developers.google.com/chart/interactive/docs/quick_start
    https://plotly.com/javascript/getting-started/
    https://d3js.org

### PRIME DIRECTIVE #2

1. The API defined should be really really simple (think java string[], double[]). API must return String (i.e. not write to a file). It will be up to the caller whether to write to a file or something else.
2. Best to define intelligence and determine labels, categorical values, numerical values if possible based on passed data
3. Think about: 
    <pre>
    chart type (enums driven - see VizHTMLEnums for some draft types)
    auto axis determination (or axis from 0)
    linear axis (default), log axis (optional)
    footer that is passed in
    </pre>

### PRIME DIRECTIVE #3

1. Consider using tokens within template HTML files where the tokens are replaced runtime based on labels and data that is passed in. See some initial token definitions in <code>VizHTMLEnums.java</code>. Note: I thought template HTML snippets with token replacement would be simplest to implement, update and maintain (see resources/com/ariscience/vizhtml/template/*). You may have other ideas - but do clear it with me.
2. Some default assumptions (to make the Java API simple) is ok to have

### PRIME DIRECTIVE #4

1. MUST NOT depend on any external Java library apart from the four Apache commons libraries already included in the lib folder

### INSPIRATION and CONVENTION DIRECTIVES

1. An example function definition MIGHT be: 

        generateBarChart(xLabels String[][], yLabels String[][], xData double[][], yData double[][], String xAxisTitle, String yAxisTitle, String chartTitle) .....other parameters as appropriate

2. Convention wise, throughout the API, it might be good that 

        xData[0][...] is series 0, 
        yData[0][...] is series 0
        
   and similarly 
       
       xData[1][..] is series 
       yData[1][...] is series 1....and so on
    
## How do I get setup?

1. Uses Ant for build processs
2. Uses Apache Ivy for library dependency management (ivy.xml)
3. Uses JUnit for automated regression tests
4. buildAndRunTests.sh should build all code and run all tests

## Coding Standards

1. All member variables must start with m_
2. All static variables must start with s_
3. Code must be clean with proper procedural and object abstraction
4. Code must NOT violate any copyright of any party or be plagiarized from anywhere
5. Apache Ant (in combination with Apache Ivy) must be used to build the project (process already setup in GIT)
6. Only core java libraries can be used. Open JDK 1.8 must be used.
7. The only 3rd party libraries that can be used are apache commons libraries or other popular libraries released under Apache, LGPL, GPL with CP exception or MIT licenses. Any 3rd party library inclusion needs authorization of Joy Alamgir.
8. As appropriate JUnit based unit tests must be created (structure already in GIT repository). Not UI tests, but if there's any internal calculation being done, those would need unit tests to assure correct behavior)
9. The source and resource file layouts have already been setup in this repository
10. Every method and class created/modified by developer should have proper and complete javadoc. Code must be appropriately commented.
11. Please follow proper javadoc standard for method javadocs. For example see the extractProjectName method in the following file. http://svn.apache.org/viewvc/ant/core/trunk/src/main/org/apache/tools/ant/DefaultLogger.java?view=co
12. While developer can use an IDE of their preference (we used IntelliJ IDEA) there must be NO dependency on compiling or running the project on a specific IDE. IDE specific files must NOT be checked in.
13. Inner classes should be very INFREQUENTLY used. Inner classes should NEVER have inner classes themselves.
14. Every new source file/class file/config file should have the following IP declaration at top: 
        <pre>        
        "Proprietary and confidential software of Alamgir Research Inc. All rights reserved. (c) 2017-2021 Alamgir Research Inc."
        </pre>
15. NOT OK to use Angular framework or any other fancy UI framework. Plain basic HTML is preferred.
16. Ok to use CDN based inclusion of bootstrap or fontawesome css

## Contribution guidelines

* Talk to Joy Alamgir before committing anything to this repository

## Who do I talk to?

* See above comment

## External References

    1. https://www.macinchem.org/scientificsoftware/jsmolviewers.php
    2. https://github.com/uglymol/uglymol/wiki/MolecularViewers
    3. https://www.d3indepth.com/force-layout/

Example on how D3 works
    
    4. https://bl.ocks.org/steveharoz/8c3e2524079a8c440df60c1ab72b5d03

# Notice

(c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. All Rights Reserved.