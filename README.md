# ATOM: A Tiny Object Modeler
TSP Spring 2018 project

Project SDK: Java 1.8

Element data from [andrejewski's periodic table](https://github.com/andrejewski/periodic-table) 

Python script for ChemSpider data pull:
~$: python ChemSpider.py -f "chemical name" #returns the first chemical formula that matches the name provided
  Example name: water, glucose, "nitric acid"
~$: python ChemSpider.py -n "chemical formula" #returns the first common name that matches the formula provided
  Example formula: C6H12O6, H2O, HCl
The flag must come before other arguments.
