# Tool uses the ChemSpiPy library to assist in accessing the ChemSpider Database

# Syntax to run command: python ChemSpider.py -(f/n) term
#     -f name -> get the formula for the common name formula
#     -n formula -> get the common name for the formula

import sys

# allows us to use command line arguments

if len(sys.argv) < 3:
    print("Incorrect input.\n\t==> python ChemSpider.py [-f/-n] <argument>")
    sys.exit()

from chemspipy import ChemSpider

cs = ChemSpider('3e05e0a6-9f49-4dff-ba0e-a9d6ca3d04ea')
# imports the ChemSpider api, and passes our access token to it

for result in cs.search(sys.argv[2])[:5]:
    # Give the first five results for -f.
    if sys.argv[1] == "-f":
        print(result.common_name)
        print(result.molecular_formula)
        # print(result.common_name)
    if sys.argv[1] == "-n":
        print(result.common_name)
        break
