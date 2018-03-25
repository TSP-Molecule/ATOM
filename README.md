# ATOM: A Tiny Object Modeler
TSP Spring 2018 project

Project SDK: Java 1.8

Element data from [andrejewski's periodic table](https://github.com/andrejewski/periodic-table) 

# Releases
* Deliverables 1: [Release](https://github.com/TSP-Molecule/ATOM/raw/Deliverables-1.0/ATOM.jar) | [Source](https://github.com/TSP-Molecule/ATOM/tree/Deliverables-1.0/)

# Requirements
* Java 1.8.x JRE
* Python 3.6.x
* chemspipy Python package
    * `pip install chemspipy`

Please make sure that the `ChemSpider.py` script is in the same directory as `ATOM.jar` when running the project, else the search functionality will _not_ work.
![Example Dir](https://i.imgur.com/k9Qa5xy.png)

***IMPORTANT NOTE***:
If you are on Windows, you will need to make sure to have your Python directory listed in your `Path` Environment Variable. There is an option to do so during your python installation, but if this isn't set, a simple guide to do so can be found [here](http://www.itprotoday.com/management-mobility/how-can-i-add-new-folder-my-system-path).

***ATOM will not function on Windows if this is not set!***
![System Variable Screen](https://i.imgur.com/SJopMh7.png)

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

# Project Screenshots
Main Window
![Main Window](https://i.imgur.com/ect59gc.png)

Rotating the molecular model

<img src="https://i.imgur.com/F4g0Jh9.gif" width="30%" height="30%"/>
<img src="https://i.imgur.com/mWVuCdB.gif" width="30%" height="30%"/>

Parsing a chemical name into formula and atoms

<img src="https://i.imgur.com/yxhulwd.gif" width="100%" height="100%"/>

Interactive Periodic Table

<img src="https://i.imgur.com/lc60jLM.png" width="50%" height="50%"/>
<img src="https://i.imgur.com/b7NeVCB.gif" width="50%" height="50%"/>
