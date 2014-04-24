from __future__ import division
from bs4 import BeautifulSoup
import re
#from urllib.request import urlopen
import urllib2
import json
import csv
import sys


missedLinks = []

data = []

csvwriter = csv.writer(open("articles5.csv", "w"))
csvwriter.writerow(["tweetid", "tweet", "timestamp", "author", "lat", "lon", "url", "imgurl"])

j = 0
with open('tornadotweets.csv', 'rU') as csvfile:
	csvreader = csv.reader(csvfile, delimiter=',')
	for row in csvreader:
		if row[0].lower() != 'tweetid':
			j += 1
			tweetid = row[0]
			tweet = row[1]
			timestamp = row[2]
			author = row[3]
			lat = row[4]
			lon = row[5]
			url = row[6]
			try:
				page = urllib2.urlopen(url).read()
				soup = BeautifulSoup(page)
				imgurl = soup.find(attrs={"class":"photo"})['src']
				newrow = [tweetid, tweet, timestamp, author, lat, lon, url, imgurl]
				for i in range(len(newrow)):  # For every value in our newrow
					if hasattr(newrow[i], 'encode'):
						newrow[i] = newrow[i].encode('utf8')
				csvwriter.writerow(newrow)
			except:
				missedLinks.append(url)
			#per = i / 10741
			#sys.stdout.write("Download progress: %s%%   \r" % (url) )
			sys.stdout.write("Download progress: %d%%   \r" % (j) )
			sys.stdout.flush()
print missedLinks
