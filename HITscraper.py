import requests
import string
import os, sys
import urllib
from bs4 import BeautifulSoup
from pprint import pprint
import time
import re
from parse_rest.connection import ParseBatcher, register
from parse_rest.datatypes import Object as ParseObject


class HIT(ParseObject):
	pass

def main():
	soup = BeautifulSoup(requests.get('https://www.mturk.com/mturk/viewhits?searchWords=&pageNumber=4&searchSpec=HITGroupSearch%23T%231%2310%23-1%23T%23%21%23%21NumHITs%211%21%23%21&sortType=NumHITs%3A1&selectedSearchType=hitgroups').text, "html.parser")
	titles = soup.findAll('a', {"class" : "capsulelink"})

	num_results = int(soup.findAll('td', {"class" : "title_orange_text"})[0].text.strip()[8:-7])

	print("\nTotal number of HITs: " + str(num_results))
	count = 0
	page = 1
	requestErrors = 0
	privateCount = 0

	register("DKJjvfvhnCGRK0cAdOpJN9MwR7zhIpuYya5xvbuF", "d8hIYrBrcW4r2ujEkL79vE03FmLxE2QCJgSwuXYv")
	HITClass = ParseObject.factory("HIT")
	all_hits = HITClass.Query.all()
	batcher = ParseBatcher()
	batcher.batch_delete(all_hits)
	while (count < 200):
		soup = BeautifulSoup(requests.get('https://www.mturk.com/mturk/viewhits?searchWords=&pageNumber=' + str(page)  + '&searchSpec=HITGroupSearch%23T%231%2310%23-1%23T%23%21%23%21NumHITs%211%21%23%21&sortType=NumHITs%3A1&selectedSearchType=hitgroups').text, "html.parser")
		titles = soup.findAll('a', {"class" : "capsulelink"})
		for t in titles:
			time.sleep(.3)
			count = count + 1
			print("\n" + str(count) + "\nTitle: " + t.text.strip())
			linkA = t.parent.parent.findAll('span')[1].a
			# check if the link is public
			if linkA.has_attr('href'):
				link = linkA['href']
				hitPage = BeautifulSoup(requests.get('https://www.mturk.com' + link).text, "html.parser")
				form = hitPage.findAll('form', {'name' : 'hitForm'})
				# Check for error 
				if len(form) >= 3:
					form = form[2]
					requester = form.find("input", {'name' : 'prevRequester'})['value']
					print('Requester: ' + requester)
					reward = form.find("input", {'name' : 'prevReward'})['value']
					print('Reward: ' + reward)
					groupID = form.find("input", {'name' : 'groupId'})['value']
					print('Group id: ' + groupID)
					
					anyObject = HIT(requester=requester, reward=float(reward[3:]), 
									title=t.text.strip(), groupID=groupID)
					anyObject.save()

				else:
					requestErrors = requestErrors + 1
					print(link)
					print(form)
			else:
				link = linkA['id']
				print(link)
				privateCount = privateCount + 1
		page = page + 1

	print("\n\nErrors: " + str(requestErrors))
	print("Private HITs: " + str(privateCount))




if __name__ == "__main__":
    main()
