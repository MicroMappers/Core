#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Copyright (C) 2013 Daniel Lombraña González
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

import json
import logging
from optparse import OptionParser
from requests import exceptions
import pbclient


def check_api_error(api_response):
    """Check if returned API response contains an error"""
    if type(api_response) == dict and (api_response.get('status') == 'failed'):
        raise exceptions.HTTPError


def format_error(module, error):
    """Format the error for the given module"""
    logging.error(module)
    # Beautify JSON error
    if type(error) == list:
        print "Application not found"
    else:
        print json.dumps(error, sort_keys=True, indent=4, separators=(',', ': '))
    exit(1)


def get_coordinates(file):
    """
    Gets coordinates from a file
    :arg string file: File name that has all the coordinates
    :returns: A list of coordinates (Lon,Lat).
    :rtype: list
    """
    file = open(file)
    coordinates = file.readlines()
    file.close()
    return coordinates


if __name__ == "__main__":
    # Arguments for the application
    usage = "usage: %prog [options]"
    parser = OptionParser(usage)
    # URL where PyBossa listens
    parser.add_option("-s", "--server", dest="api_url",
                      help="PyBossa URL http://domain.com/",
                      metavar="URL")
    # API-KEY
    parser.add_option("-k", "--api-key", dest="api_key",
                      help="PyBossa User API-KEY to interact with PyBossa",
                      metavar="API-KEY")
    # Create App
    parser.add_option("-a", "--create-app", action="store_true",
                      dest="create_app",
                      help="Create the application",
                      metavar="CREATE-APP")
    # Template
    parser.add_option("-t", "--template", dest="template",
                      help="PyBossa HTML+JS template for app presenter",
                      metavar="TEMPLATE")
    # Update template for tasks and long_description for app
    parser.add_option("-u", "--update-template", action="store_true",
                      dest="update_template",
                      help="Update Tasks template",
                      metavar="UPDATE-TEMPLATE")

    # Update tasks question
    parser.add_option("-q", "--update-tasks",
                      dest="update_tasks",
                      help="Update Tasks n_answers",
                      metavar="UPDATE-TASKS")

    # Modify the number of TaskRuns per Task
    # (default 30)
    parser.add_option("-n", "--number-answers",
                      dest="n_answers",
                      help="Number of answers per task",
                      metavar="N-ANSWERS")
    # File with list of coordinates
    parser.add_option("-c", "--coordinates", dest="coordinates",
                      help="File with the name of the coordinates",
                      metavar="COORDINATES")
    # Verbose?
    parser.add_option("-v", "--verbose", action="store_true",
                      dest="verbose")

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
        options.api_url = 'http://localhost:5000'
    pbclient.set('endpoint', options.api_url)

    if not options.api_key:
        parser.error("You must supply an API-KEY to create " +
                     "an application and tasks in PyBossa")
    pbclient.set('api_key', options.api_key)

    if not options.template:
        print("Using default template: template.html")
        options.template = "template.html"

    if not options.coordinates:
        parser.error("You must supply a file name with the coordinates")
    if not options.n_answers:
        options.n_answers = 30

    if (options.verbose):
        print('Running against PyBosssa instance at: %s' % options.api_url)
        print('Using API-KEY: %s' % options.api_key)

    if options.create_app:
        try:
            response = pbclient.create_app(app_config['name'],
                                           app_config['short_name'],
                                           app_config['description'])
            check_api_error(response)
            response = pbclient.find_app(short_name=app_config['short_name'])
            check_api_error(response)
            app = response[0]
            app.long_description = open('long_description.html').read()
            app.info['task_presenter'] = open('template.html').read()
            app.info['thumbnail'] = app_config['thumbnail']
            app.info['tutorial'] = open('tutorial.html').read()
        except:
            format_error("pbclient.create_app or pbclient.find_app", response)

        try:
            response = pbclient.update_app(app)
            check_api_error(response)
        except:
            format_error("pbclient.update_app", response)

        coordinates = get_coordinates(options.coordinates)
        for c in coordinates:
                lon, lat = c.split(",")
                task_info = dict(question=app_config['question'],
                                 n_answers=int(options.n_answers),
                                 lon=float(lon),
                                 lat=float(lat))
                try:
                    response = pbclient.create_task(app.id, task_info)
                    check_api_error(response)
                except:
                    format_error("pbclient.create_task", response)

    if options.update_template:
        print "Updating app template"
        try:
            response = pbclient.find_app(short_name=app_config['short_name'])
            check_api_error(response)
            app = response[0]
            app.long_description = open('long_description.html').read()
            app.info['task_presenter'] = open('template.html').read()
            app.info['tutorial'] = open('tutorial.html').read()
            response = pbclient.update_app(app)
            check_api_error(response)
        except:
            format_error("pbclient.find_app or pbclient.update_app", response)

    if options.update_tasks:
        print "Updating task question"
        try:
            response = pbclient.find_app(short_name=app_config['short_name'])
            check_api_error(response)
            app = response[0]
            n_tasks = 0
            offset = 0
            limit = 100
        except:
            format_error("pbclient.find_app", response)

        try:
            tasks = pbclient.get_tasks(app.id, offset=offset, limit=limit)
            check_api_error(tasks)
        except:
            format_error("pbclient.get_tasks", tasks)
        while tasks:
            for task in tasks:
                print "Updating task: %s" % task.id
                if ('n_answers' in task.info.keys()):
                    del(task.info['n_answers'])
                task.n_answers = int(options.update_tasks)
                try:
                    response = pbclient.update_task(task)
                    check_api_error(response)
                    n_tasks += 1
                except:
                    format_error("pbclient.update_task", response)
            offset = (offset + limit)
            try:
                tasks = pbclient.get_tasks(app.id, offset=offset, limit=limit)
                check_api_error(tasks)
            except:
                format_error("pbclient.get_tasks", tasks)
        print "%s Tasks have been updated!" % n_tasks

    if not options.create_app and not options.update_template:
        parser.error("Please check --help or -h for the available options")
