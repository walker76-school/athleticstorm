import requests
import time
import random
import logging
from bs4 import BeautifulSoup
import json
from pprint import pprint

logging.basicConfig(filename='application.log', filemode='w', format='%(asctime)s - %(message)s', level=logging.INFO)
min_sleep = 0.5
max_sleep = 2.5

# Get all teams
teams = set()
teams_arr = json.load(open("teams.json", 'r'))
for team in teams_arr:
    teams.add(team['school'])

# Encode all of the queries using team names
queries = []
for team in teams:
    encoded_team = team.replace(' ', '+')
    query = str.format("https://www.google.com/search?q={}+football+offensive+coordinator+2017", encoded_team)
    print(query)
    queries.append(query)

# Open each query to dump to the outfiles
for query in queries:
    logging.info("\tGetting from " + query)
    text = requests.get(query.strip()).text
    soup = BeautifulSoup(text, features="html.parser")
    table_div = soup.find_all("div", {"class": "webanswers-webanswers_table__webanswers-table"})
    sleep_time = (random.random() * (max_sleep - min_sleep)) + min_sleep
    logging.info(f'\tSleeping for %f seconds', sleep_time)
    time.sleep(sleep_time)

# max_pages = 26120
# min_sleep = 0.5
# max_sleep = 2.5
#
#
# for i in range(1, max_pages + 1):
#     out_file = str.format("out/{}.links", i)
#     logging.info("Opening " + out_file)
#     with open(out_file) as fp:
#         for cnt, line in enumerate(fp):
#             logging.info("\tGetting from " + line.strip())
#             text = requests.get(line.strip()).text
#             soup = BeautifulSoup(text, features="html.parser")
#             code_snippets = soup.findAll("code")
#             sleep_time = (random.random() * (max_sleep - min_sleep)) + min_sleep
#             logging.info(f'\tSleeping for %f seconds', sleep_time)
#             time.sleep(sleep_time)
