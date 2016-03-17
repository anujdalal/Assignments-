from xml.etree.ElementTree import Element
from xml.dom import minidom
from subprocess import call
import xml.etree.ElementTree as etree
import os
# this function constructs flows for each device, and destination address
def createflows():

    # These variables will be changed for each flow.
    node = 'Enter the device you want to modify (openflow:x): '
    flowname = 'Flow Name: '
    flid = 'Flow ID: '
    flpriority = 'Flow Priority: '
    transport = 'TCP/UDP: '
    ipv4 = 'Destination IP: '
    outint = 'Egress interface: '
    true = 1

    while (true == 1):
        dev = 'openflow:11' #raw_input(node)
        print 'Please fill in the following to add a flow'
        flname = raw_input(flowname)
        flowid = raw_input(flid)
        flowpriority = raw_input(flpriority)
        ipprot = raw_input(transport)
        ipaddr = raw_input(ipv4)
        output = raw_input(outint)
	os.system('''curl -u admin:admin -H 'Content-type: application/json' -X PUT -d '{
                  "flow": [{"id": "'''+flowid+'''", "match": { "ethernet-match": { "ethernet-type": { "type": "2048"}}, "ip-match": {"ip-protocol": "'''+ipprot+'''"},
                  "ipv4-destination": "'''+ipaddr+'''"}, "instructions": { "instruction": [{ "order": "0",
                  "apply-actions": { "action": [{ "order": "0", "output-action": { "output-node-connector": "'''+output+'''",
                  "max-length": "60"}}]}}]}, "flow-name": "'''+flname+'''", "installHw": "true", "strict": "false", "priority": "'''+flowpriority+'''",
                  "idle-timeout": "0", "hard-timeout": "0", "table_id": "0"}]}' 'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/flow-node-inventory:table/0/flow/'''+flowid+"'"'')
       
	addmore = 'Add another flow?'
        response = raw_input(addmore)
        if (response == 'y' or 'Y'):
            true = 1
        else:
            true = 0

createflows()
