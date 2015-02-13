from pylib.cmd import exec_cmd2
from pylib.files import append_file
from pylib.users import get_user
import os
import logging

log = logging.getLogger('main')

if __name__=='__main__':
	exec_cmd2("wget -nc https://s3.eu-central-1.amazonaws.com/scaleappjavapackages/jdk-8u31-linux-x64.rpm")
	exec_cmd2("wget -nc https://s3.eu-central-1.amazonaws.com/scaleappjavapackages/US_export_policy.jar")
	exec_cmd2("wget -nc https://s3.eu-central-1.amazonaws.com/scaleappjavapackages/local_policy.jar")
	exec_cmd2("yum -y localinstall jdk-8u31-linux-x64.rpm")
	bashrc = os.path.join(os.path.join("/home", get_user()), ".bashrc")
	log.info("bashrc %s" % bashrc)
	append_file(bashrc, 'JAVA_HOME=/usr/java/jdk1.8.0_31')
	append_file(bashrc, 'export JAVA_BIN=$JAVA_HOME/bin')
	append_file(bashrc, 'export PATH=$JAVA_BIN:$PATH')
