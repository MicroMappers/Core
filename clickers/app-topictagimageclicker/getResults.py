#!/usr/bin/env python 
# -*- coding: utf-8 -*-

# Copyright (C) 2012 Citizen Cyberscience Centre
# 
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

from optparse import OptionParser
import pbclient
import json
if __name__ == "__main__":
    # Arguments for the application
    usage = "usage: %prog [options]"
    parser = OptionParser(usage)
    
    parser.add_option("-s", "--url", dest="api_url", help="PyBossa URL http://domain.com/", metavar="URL")
    parser.add_option("-k", "--api-key", dest="api_key", help="PyBossa User API-KEY to interact with PyBossa", metavar="API-KEY")
    parser.add_option("-r", "--results", dest="results", action="store_true", 
                      help="Create the application",
                      metavar="CREATE-APP")

    parser.add_option("-v", "--verbose", action="store_true", dest="verbose")
    parser.add_option("-c", "--completed", action="store_true", dest="completed")
    
    (options, args) = parser.parse_args()

    if not options.api_url:
        options.api_url = 'http://localhost:5000'

    pbclient.set('endpoint', options.api_url)

    if not options.api_key:
        parser.error("You must supply an API-KEY to create an applicationa and tasks in PyBossa")
    else:
        pbclient.set('api_key', options.api_key)

    if (options.verbose):
       print('Running against PyBosssa instance at: %s' % options.api_url)
       print('Using API-KEY: %s' % options.api_key)

    if (options.results):
        offset=0
        limit=2000

        app = pbclient.find_app(short_name='MM_ImageClicker')[0]
        if options.completed: 
            completed_tasks = pbclient.find_tasks(app.id, state="completed", offset=offset,limit=limit)
        else:
            completed_tasks = pbclient.find_tasks(app.id,offset=offset,limit=limit)
        #'category': null,
        #                'text': task.info.text,
        #               'task_id': task.info.tweetid,
        #                'date': task.info.date,
        #                'username': task.info.username,
        #               'userid': task.info.userid
        # AUTHOR \t TWEET \t   TIMESTAMP \t region  \t country \t LAT \t LONG
        # Now get the task runs
        import csv
        f = csv.writer(open("results.csv", "wb"))
        f.writerow(['taskid',
                    'tweetid',
                    'text',
                    'date',
                    'username',
                    'category',
                    'taskCompletionTime',
                    'imageURL'
            ])

        while completed_tasks:
            for t in completed_tasks:
                print t.id
                answers = pbclient.find_taskruns(app.id, task_id=int(t.id))
                for a in answers:
                    line = []

                    if t.id:
                        line.append(t.id)
                    else:
                        line.append("")

                    if t.info['tweetid']:
                        line.append(t.info['tweetid'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if t.info['tweet']:
                        line.append(t.info['tweet'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if t.info['timestamp']:
                        line.append(t.info['timestamp'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if t.info['author']:
                        line.append(t.info['author'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if a.info['damage']:
                        line.append(a.info['damage'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if t.created:
                        line.append(t.created.encode('utf-8', 'ignore'))
                    else:
                        line.append("")

                    if a.info['url']:
                        line.append(a.info['url'].encode('utf-8', 'ignore'))
                    else:
                        line.append("")



                    f.writerow(line)
            offset = offset + limit
            if options.completed: 
                completed_tasks = pbclient.find_tasks(app.id, state="completed", offset=offset,limit=limit)
            else:
                completed_tasks = pbclient.find_tasks(app.id,offset=offset,limit=limit)
