#Tool uses the ChemSpiPy library to assist in accessing the ChemSpider Database

#Syntax to run command: python ChemSpider.py -(f/n) term
#     -f name -> get the formula for the common name formula
#     -n formula -> get the common name for the formula

import sys
#allows us to use command line arguments

from chemspipy import ChemSpider
cs = ChemSpider('3e05e0a6-9f49-4dff-ba0e-a9d6ca3d04ea')
#imports the ChemSpider api, and passes our access token to it

for result in cs.search(sys.argv[2]):
     if sys.argv[1] == "-f":
          print(result.molecular_formula)
     if sys.argv[1] == "-n":
          print(result.common_name)
     break
#we break in the first loop, because we only want 1 entry in our Java program, and it can run for a very long time without this break statement. Break was added for speed.
