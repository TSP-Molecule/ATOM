# ATOM: A Tiny Object Modeler
TSP Spring 2018 project

Project SDK: Java 1.8

Element data from [andrejewski's periodic table](https://github.com/andrejewski/periodic-table) 

# Releases
* Deliverables 1: [Release](https://github.com/TSP-Molecule/ATOM/raw/Deliverables-1.0/ATOM.jar) | [Source](https://github.com/TSP-Molecule/ATOM/tree/Deliverables-1.0/)

# Requirements
To run ATOM, you will need both Python 3.x.x and Java 1.8.x JRE installed on your machine.

***IMPORTANT NOTE***:
If you are on Windows, you will need to set the `PYTHONPATH` environment variable to the location of your Python executable. ***ATOM WILL NOT WORK PROPERLY IF THIS IS NOT DONE***.

# To run ATOM
Commandline compilation isn't currently functioning, but we've built a jar available in the repo, as well as provided one in our deliverables with the most up-to-date functional changes. 

So, the command to run is as follows:
`~$: java -jar ATOM.jar`

To use, simply click any GUI items available. The periodic table popup is available under `Navigation->View Periodic Table` in the menu bar.


# To run Python script for ChemSpider data pull
`~$: python ChemSpider.py -f "chemical name"` #returns the first chemical formula that matches the name provided</br>
  Example name: water, glucose, "nitric acid"</br>
  
`~$: python ChemSpider.py -n "chemical formula"` #returns the first common name that matches the formula provided</br>
  Example formula: C6H12O6, H2O, HCl</br>
  
The flag must come before other arguments.

