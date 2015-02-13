# vim: tabstop=8 expandtab shiftwidth=4 softtabstop=4
import os
import inspect

currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
CURR_DIR = os.path.abspath(currentdir)
LIB_LOGS_DIR = CURR_DIR

LOGGING = {
    'version' : 1,
    'disable_existing_loggers' : False,
    'formatters': {
        'verbose' : {
            'format' : '%(levelname)s %(asctime)s %(module)s %(process)d %(message)s'
        },
        'simple' : {
            'format' : '%(levelname)s %(asctime)s %(module)s %(message)s'
        },
    },
    'handlers' : {
        'file' : {
            'level' : 'DEBUG',
            'class' : 'cloghandler.ConcurrentRotatingFileHandler',
            'formatter' : 'verbose',
            'filename' : os.path.join(LIB_LOGS_DIR, 'pylib.log'),
            'maxBytes' : 10000000,
            'backupCount' : 10,
        },
 	'stdout' : {
            'level' : 'DEBUG',
	    'class' : 'logging.StreamHandler',
            'formatter' : 'verbose',
	},
    },
    'loggers' : {
        'main' : {
            'handlers' : ['file', 'stdout'],
            'level' : 'DEBUG',
            'propagate' : True,
        },
    },
}

