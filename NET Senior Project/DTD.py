import urllib2
import json
#import pycurl
import sys
import re
import xml.etree.ElementTree as ET

#Retrieves and parses the topology information on the current controller
def getTopology():
	prompt1 = 'Username: '
	prompt2 = 'Password: '
	#HTTP basic authentication required
	auth_handler = urllib2.HTTPBasicAuthHandler()
	print 'Please enter your username and password and press <cr>...\n'
	uname = raw_input(prompt1)
	passwd = raw_input(prompt2)
	auth_handler.add_password(realm='application',
							  uri='http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes',
							  user=uname,
							  passwd=passwd)
	opener = urllib2.build_opener(auth_handler)
	urllib2.install_opener(opener)
	#Open the appropriate URL
	data = "http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes"
	nodeInfo = urllib2.urlopen(data)
	if (nodeInfo.getcode() == 200):
		data = nodeInfo.read()
		print 'good'
		#Prints the data in readable form
		printTopo(data)
		print data
		#print "Found the following OpenFlow devices:" , re.match("^n", "node")
	else:
		print 'Cannot open URL:' + str(nodeInfo.getcode())

#Prints topological information
def printTopo(data):
	'''
	Data is in JSON form, need to parse for the following:
		From the controller database:
		Nodes
		For Each Node: 
		   Interfaces
		   Flow table 0
			  Flows
			  For each flow (if available):
				 Match Parameters
			 Output connectors
	'''

        jsonResponse=json.loads(data)
        jsonData = jsonResponse["nodes"]

        g = 0
        h = 0
        i = 0


        for item in jsonData:

            while (g <= 5):
                node = jsonData["node"][g]["id"]
                table = jsonData["node"][g]["flow-node-inventory:table"][h]["id"]
                print ("node:") + str(node)
                print ("flowtable:") + str(table)
                for i in xrange(0, 6):
                    flow = jsonData["node"][g]["flow-node-inventory:table"][h]["flow"][i]["flow-name"]
                    match = jsonData["node"][g]["flow-node-inventory:table"][h]["flow"][i]["match"]["ipv4-destination"]
                    outtputnode = jsonData["node"][g]["flow-node-inventory:table"][h]["flow"][i]["instructions"]["instruction"][0]["apply-actions"]["action"][0]["output-action"]["output-node-connector"]
                    print ("flow:") + str(flow)
                    print ("output-node-connector:") + str(outtputnode)
                    print ("match:") + str(match)
                    #i = i+1
                g = g + 1


def ui():
	true = 1
	ans=''

	#Run indefinitely until quit
	while (true == 1):
		print "Press 'q' + <cr> at any time to exit the application"
		ans = raw_input()
		if (ans == 'Q' or 'q'):
			break
		else:
			true = 1

def dtd():
	print 'This is the DTD test application.'
	src = 'Choose source host (1, 2, 3, 4): '
	dst = 'Choose destination host (1, 2, 3, 4): '
	type = 'Choose traffic type (TCP, UDP, Other): '
	srchost = raw_input(src)
	dsthost = raw_input(dst)
	traffic = raw_input(type)






def main():
	getTopology()
	#test()
	#ui()



if __name__ == "__main__":
	main()
