from pylib.cmd import exec_cmd2
from pylib.files import append_file
from pylib.users import get_user
import os
import logging

log = logging.getLogger('main')

if __name__=='__main__':
	exec_cmd2("wget -nc https://s3.eu-central-1.amazonaws.com/scaleappjavapackages/apache-maven-3.2.5-bin.tar.gz")
	exec_cmd2("tar -xzvf apache-maven-3.2.5-bin.tar.gz -C /usr/local/")
	bashrc = os.path.join(os.path.join("/home", get_user()), ".bashrc")
	log.info("bashrc %s" % bashrc)
	append_file(bashrc, 'MVN_HOME=/usr/local/apache-maven-3.2.5')
	append_file(bashrc, 'export MVN_BIN=$MVN_HOME/bin')
	append_file(bashrc, 'export PATH=$MVN_BIN:$PATH')
