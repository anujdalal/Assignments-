import urllib2
import re
import xml.etree.ElementTree as ET

#def getTopology():
auth_handler = urllib2.HTTPBasicAuthHandler()
auth_handler.add_password(realm='opendaylight',
                          uri='http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes/',
                          user='admin',
                          passwd='admin')

opener = urllib2.build_opener(auth_handler)
urllib2.install_opener(opener)
s = urllib2.urlopen('http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes/')
print 'Found the following OpenFlow devices:' , re.match("^n", "node")
