from pylib.cmd import exec_cmd2
from pylib.files import append_file
from pylib.users import get_user, get_home
from pylib.ssh import SshExec
import os
import logging

log = logging.getLogger('main')

if __name__=='__main__':
	key_file = os.path.join(os.path.join(get_home(), "keys"), "test.pem")
	s = SshExec(log, "54.93.112.180", "ec2-user", key_file=key_file)
	s.cmd("service cassandra stop")
	s.cmd("service cassandra start")
