import sys

from chemspipy import ChemSpider
cs = ChemSpider('3e05e0a6-9f49-4dff-ba0e-a9d6ca3d04ea')

for result in cs.search(sys.argv[1]):
     print(result.molecular_formula)
