# ATOM: A Tiny Object Modeler
TSP Spring 2018 project

Project SDK: Java 1.8

Element data from [andrejewski's periodic table](https://github.com/andrejewski/periodic-table) 

# Installation
To install, simply extract the contents of `ATOM-x.x.zip` into a folder and run the JAR. It does support double-clicking the JAR to run, but this is dependent on your OS settings. `$ java -jar ATOM.jar` will suffice.

# To run ATOM
Commandline compilation isn't currently functioning, but we've built a jar available in the repo, as well as provided one in our deliverables with the most up-to-date functional changes. 

So, the command to run is as follows:
`~$: java -jar ATOM.jar`

To use, simply click any GUI items available. The periodic table popup is available under `Navigation->View Periodic Table` in the menu bar.


# To run Python script for ChemSpider data pull (separately from ATOM)
`~$: python ChemSpider.py -f "chemical name"` #returns the first chemical formula that matches the name provided</br>
  Example name: water, glucose, "nitric acid"</br>
  
`~$: python ChemSpider.py -n "chemical formula"` #returns the first common name that matches the formula provided</br>
  Example formula: C6H12O6, H2O, HCl</br>
  
The flag must come before other arguments.
# To move around the 3D oxygen molecule
   First click on the molecule to ensure its part of the screen is selected.</br>
   Then type one of the following lowercase letters:</br>
   <table>
   <tr><td>Move the molecule Right</td><td>a</td></tr>
   <tr><td>Move the molecule Left:</td><td>       d</td></tr>
   <tr><td>Move the molecule Back:</td><td>     s</td></tr>
   <tr><td>Move the molecule Forward: </td><td> w</td></tr>
   <tr><td>Move the molecule Up:  </td><td>     f</td></tr>
   <tr><td>Move the molecule Down: </td><td>    r</td></tr></table></br>
   
   <table>
   <tr><td>Rotate the molecule counter-clockwise around the y-axis:  </td><td>j</td></tr>
   <tr><td>Rotate the molecule clockwise around the y-axis:          </td><td>l</td></tr>
   <tr><td>Rotate the molecule counter-clockwise around the x-axis:  </td><td>h</td></tr>
   <tr><td>Rotate the molecule clockwise around the x-axis:          </td><td>y</td></tr>
   <tr><td>Rotate the molecule counter-clockwise around the z-axis:  </td><td>i</td></tr>
   <tr><td>Rotate the molecule clockwise around the z-axis:          </td><td>k</td></tr></table></br>
   
   Note:  The rotation axes are for the molecule in its original position.  As the molecule is rotated in multiple directions,
   the axis of rotation will change.
   
# To use the search bar
   Click in the search bar and type the chemical name or formula you want to find.  Then click the button that reads "GO".  The search 
   may take a few seconds to display simple information in the right bottom panel.
   
