#!/usr/bin/env python
# -*- coding: utf-8 -*-

# This file is part of PyBOSSA.
#
# PyBOSSA is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# PyBOSSA is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with PyBOSSA.  If not, see <http://www.gnu.org/licenses/>.

#These are the things you need to change when creating a new task

import json
import re
from optparse import OptionParser
import pbclient
from urlparse import urlparse, parse_qsl

def get_videoURL(targetText):
    targetVideoURL = "";
    pattern1 ='http://youtu.be/';
    re1='(http:\\/\\/youtu\\.be\\/)';
    rg = re.compile(re1,re.IGNORECASE|re.DOTALL);
    m = rg.search(targetText);
    if m:
        targetVideoURL = targetText.replace(pattern1, '')
        targetVideoURL = 'http://www.youtube.com/embed/'+ targetVideoURL
    else :
            urlQuery = parse_qsl(urlparse(targetText)[4]);
            for item in urlQuery:
                queryString = item
                if len(item) > 1:
                    if item[0] == 'v':
                        targetVideoURL = item[1]
                        targetVideoURL = 'http://www.youtube.com/embed/'+ targetVideoURL

    return targetVideoURL

def row_value_in_line(headerinfo, searchheader, row) :
    i = 0
    returnvalue = ''
    while i < len(headerinfo):
        if headerinfo[i].lower() == searchheader.lower() :
            returnvalue =  row[i]

        i = i + 1

    return returnvalue


def task_formatter(app_config, app , headerinfo, row, n_answers):

    #targeturl = get_videoURL(row_value_in_line(headerinfo, 'url', row));

    if len(row) > 0 :
        task_info = dict(question=app_config['question'],
                    n_answers=int(n_answers),
                    tweetid = row_value_in_line(headerinfo, 'tweetid', row),
                    tweet = row_value_in_line(headerinfo, 'tweet', row),
                    timestamp = row_value_in_line(headerinfo, 'timestamp', row),
                    author = row_value_in_line(headerinfo, 'author', row),
                    lat = row_value_in_line(headerinfo, 'lat', row),
                    lon = row_value_in_line(headerinfo, 'lon', row),
                    url = row_value_in_line(headerinfo, 'url', row),
                    videourl = row_value_in_line(headerinfo, 'url', row)
                    )

        pbclient.create_task(app.id, task_info)

if __name__ == "__main__":
    # Arguments for the application
    usage = "usage: %prog [options]"
    parser = OptionParser(usage)
    # URL where PyBossa listens
    parser.add_option("-s", "--server", dest="api_url",
                      help="PyBossa URL http://domain.com/", metavar="URL")
    # API-KEY
    parser.add_option("-k", "--api-key", dest="api_key",
                      help="PyBossa User API-KEY to interact with PyBossa",
                      metavar="API-KEY")
    # Create App
    parser.add_option("-c", "--create-app", action="store_true",
                      dest="create_app",
                      help="Create the application",
                      metavar="CREATE-APP")
    # Update template for tasks and long_description for app
    parser.add_option("-t", "--update-template", action="store_true",
                      dest="update_template",
                      help="Update Tasks template",
                      metavar="UPDATE-TEMPLATE"
                     )

    # Update tasks question
    parser.add_option("-q", "--update-tasks",
                      dest="update_tasks",
                      help="Update Tasks question",
                      metavar="UPDATE-TASKS"
                     )

    parser.add_option("-x", "--extra-task", action="store_true",
                      dest="add_more_tasks",
                      help="Add more tasks",
                      metavar="ADD-MORE-TASKS"
                      )
    # Modify the number of TaskRuns per Task
    # (default 30)
    parser.add_option("-n", "--number-answers",
                      dest="n_answers",
                      help="Number of answers per task",
                      metavar="N-ANSWERS"
                     )

    parser.add_option("-v", "--verbose", action="store_true", dest="verbose")
    (options, args) = parser.parse_args()

    # Load app details
    try:
        app_json = open('app.json')
        app_config = json.load(app_json)
        app_json.close()
    except IOError as e:
        print "app.json is missing! Please create a new one"
        exit(0)

    if not options.api_url:
        options.api_url = 'http://localhost:5000/'
    pbclient.set('endpoint', options.api_url)

    if not options.api_key:
        parser.error("You must supply an API-KEY to create an \
                      applicationa and tasks in PyBossa")
    else:
        pbclient.set('api_key', options.api_key)

    if (options.verbose):
        print('Running against PyBosssa instance at: %s' % options.api_url)
        print('Using API-KEY: %s' % options.api_key)

    if not options.n_answers:
        options.n_answers = 10

    if options.create_app:
        import csv
        pbclient.create_app(app_config['name'],
                app_config['short_name'],
                app_config['description'])
        app = pbclient.find_app(short_name=app_config['short_name'])[0]
        app.long_description = open('long_description.html').read()
        app.info['task_presenter'] = open('template.html').read()
        app.info['thumbnail'] = app_config['thumbnail']
        app.info['tutorial'] = open('tutorial.html').read()

        pbclient.update_app(app)
        with open('videoTweet2.csv', 'rb') as csvfile:
            csvreader = csv.reader(csvfile, delimiter=',')
            rownum = 0
            for row in csvreader:
                if rownum == 0 :
                    headerinfo = row
                    #print row
                else :
                    task_formatter(app_config,app, headerinfo, row, options.n_answers)

                rownum+=1
    else:
        app = pbclient.find_app(short_name=app_config['short_name'])[0]
        if options.add_more_tasks:
            import csv
            with open('videoTweet2.csv', 'rb') as csvfile:
                csvreader = csv.reader(csvfile, delimiter=',')
                rownum = 0
                for row in csvreader:
                    if rownum == 0 :
                        headerinfo = row
                        #print row
                    else :
                        task_formatter(app_config,app, headerinfo, row, options.n_answers)

                    rownum+=1

    if options.update_template:
        print "Updating app template"
        app = pbclient.find_app(short_name=app_config['short_name'])[0]
        app.long_description = open('long_description.html').read()
        app.info['task_presenter'] = open('template.html').read()
        app.info['tutorial'] = open('tutorial.html').read()
        pbclient.update_app(app)

    if options.update_tasks:
        print "Updating task n_answers"
        app = pbclient.find_app(short_name=app_config['short_name'])[0]
        n_tasks = 0
        offset = 0
        limit = 100
        tasks = pbclient.get_tasks(app.id,offset=offset,limit=limit)
        while tasks:
            for task in tasks:
                print "Updating task: %s" % task.id
                if ('n_answers' in task.info.keys()):
                    del(task.info['n_answers'])
                task.n_answers = int(options.update_tasks)
                pbclient.update_task(task)
                n_tasks += 1
            offset = (offset + limit)
            tasks = pbclient.get_tasks(app.id,offset=offset,limit=limit)
        print "%s Tasks have been updated!" % n_tasks


    if not options.create_app and not options.update_template\
            and not options.add_more_tasks and not options.update_tasks:
        parser.error("Please check --help or -h for the available options")
