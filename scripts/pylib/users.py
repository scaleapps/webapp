# vim: tabstop=8 expandtab shiftwidth=4 softtabstop=4
import httplib
import json
import subprocess
import os
from datetime import datetime
import logging.config
import logging
import uuid
import threading
import copy
import time
import sys
import inspect
import re

currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))

import settings
import cmd

logging.config.dictConfig(settings.LOGGING)
log = logging.getLogger('main')

def get_home():
    return os.path.join('/home', get_user())

def get_user():
    _, stdout, stderr, _ =  cmd.exec_cmd2("who am i | awk '{print $1}'", throw = True)
    return stdout[0]
