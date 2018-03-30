#Tool to access the first paragraph of a Wikipedia article

#Syntax to run command: python Wikipedia.py <search term>
#       where <search term> is anything that could be searchable on Wikipedia.

from bs4 import BeautifulSoup
#Beautiful Soup for HTML parsing

import requests
#HTML requests

import sys
#system functions & arguments

import re
#regex

#Verify we have correct number of arguments
if len(sys.argv) < 2:
    print("Incorrect input.\n\t==> python Wikipedia.py <search term>")
#end Verify

r = requests.get('https://en.wikipedia.org/wiki/' + sys.argv[1])
#perform the HTML request

soup = BeautifulSoup(r.content, 'html.parser')
#create the Beautiful Soup object
print(re.sub("(\[.*\])","",str(soup.find_all('p')[0].get_text())))
#finds the first paragraph element on the page,
#   gets the text from it (removing all tags), and prints it
