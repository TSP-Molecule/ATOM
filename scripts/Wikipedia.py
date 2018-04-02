# Tool to access the first paragraph of a Wikipedia article

# Syntax to run command: python Wikipedia.py <search term>
#       where <search term> is anything that could be searchable on Wikipedia.

from bs4 import BeautifulSoup
# Beautiful Soup for HTML parsing

import wikipedia
# Wikipedia python lib

import requests
# HTML requests

import sys
# system functions & arguments

import re
# regex

# Verify we have correct number of arguments
if len(sys.argv) < 2:
    print("Incorrect input.\n\t==> python Wikipedia.py <search term>")
    
content = str(wikipedia.summary(sys.argv[1]).encode("utf-8"))[2:-1].split("\\n")

for x in content:
    print(x)
    print("\n")

file_write = open("cache/" + sys.argv[1] + ".txt", "w")
for x in content:
    file_write.write(x)
    file_write.write("\n")
file_write.close()
