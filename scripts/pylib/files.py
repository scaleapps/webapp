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

logging.config.dictConfig(settings.LOGGING)

log = logging.getLogger('main')

def append_file(fpath, line):
    f = open(fpath, "a")
    f.write(line + '\n')
    f.close()
