# Author: Anuj Dalal
# Team: Austin Toll, Ahmed Alabdulrahman, David Ballantyne
# Written for the NET 4901 Senior Project – Dynamic Traffic Diversion in SDN
# April 24th, 2016
# Supervisor: Prof. Marc St-Hilaire – Carleton University

import re
import os
import time
import urllib2

'''
This function accesses the RESTful northboun interface of the controller, retrieves, and parses the interface data
for the number of transmitted bytes
'''


def getbytes(device, interface):
    # basic HTTP authentication
    auth_handler = urllib2.HTTPBasicAuthHandler()
    auth_handler.add_password(realm='application',
                              uri='http://localhost:8181/restconf/operational/opendaylight-inventory:nodes/node/'
                                  'openflow:' + device + '/node-connector/openflow:' + device + ':' + interface +
                                  '/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes',
                              user='admin',
                              passwd='admin')
    opener = urllib2.build_opener(auth_handler)
    urllib2.install_opener(opener)
    host = 'http://localhost:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:' + device + \
           '/node-connector/openflow:' + device + ':' + interface + \
           '/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes'
    req = urllib2.Request(host)
    nodeInfo = urllib2.urlopen(req)
    data = nodeInfo.read()
    sent = (re.findall('(?<="transmitted":)\w+', data))
    sent = map(int, sent)
    return sent


'''
For each device and interface in the relevant topology, get the interface statistics, and store them in an array
'''


def topo():
    dev = ['11', '13']
    int = [['5100'], ['5100']]
    data = []

    for index in range(len(dev)):
        if index is 0:
            device = dev[index]
            for index in range(len(int[0])):
                interface = int[0][index]
                data.extend(getbytes(device, interface))
        elif index is 1:
            device = dev[index]
            for index in range(len(int[1])):
                interface = int[1][index]
                data.extend(getbytes(device, interface))
    return data


'''
Diverts appropriate flows to alternate interface using REST API and cURL
'''


def DTD(outint, dev, flow1, flow2):
    flowid = [flow1, flow2]
    for index in range(len(flowid)):
        os.system('''curl -u admin:admin -H 'Content-type: application/json' -X PUT -d '{ "instruction":
        [{ "order": "0", "apply-actions": { "action": [{ "order": "0",
        "output-action": { "output-node-connector": "''' + outint + '''", "max-length": "60"}}]}}]}'
        'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/openflow:''' + dev +
                  '''/flow-node-inventory:table/0/flow/''' +
                  flowid[index] + '''/instructions/instruction/0' ''')

    print  "!!!Traffic for flows " + flow1 + " and flow " + flow2 + " diverted to alternate link!!!"


def main():
    print "Starting DTD application for physical topology...\n"
    data = topo()  # get initial topoology data. 2 position is this array: 0 = S1, 1 = S3
    print "Monitoring Switch 1: Gi1/0/23 and Switch 3: Gi1/0/24\n"
    state = [0, 0]  # state for both devices is "no flows have been diverted"
    while True:
        for index in range(len(data)):
            if index is 0:
                outint = '5100'
                intname = 'Gi1/0/23'
                altint = '5103'
                altintname = 'Gi1/0/24'
                flow1 = '9'
                flow2 = '10'
                dev = '11'
                devname = 'Switch 1'
            elif index is 1:
                outint = '5100'
                intname = 'Gi1/0/24'
                altint = '5099'
                altintname = 'Gi1/0/23'
                flow1 = '5'
                flow2 = '6'
                dev = '13'
                devname = 'Switch 3'
            sentBytes = data[index]
            while True:
                oldSentBytes = sentBytes
                data = topo()
                sentBytes = data[index]
                if sentBytes != oldSentBytes:
                    '''Cisco switches take ~ 5 seconds to locally update byte values. Therefore number of bytes between
                       intervals / 5 = byterate in Bps'''
                    byteRate = float(sentBytes - oldSentBytes) / 5
                    util = round((byteRate / 125000000) * 100, 2)  # 125000000 = 1 Gbit
                    print "Utilizaition = " + str(util) + "% on " + devname + ": " + intname + "\n"
                    if util > 90 and state[index] is 0:
                        print "!!!Upper threshold reached on " + devname + ": " + intname + ", Utilization = " + str(
                            util) + "%!!!\n"
                        DTD(altint, dev, flow1, flow2)
                        state[index] = 1
                    elif util < 50 and state[index] is 1:
                        print "!!!Lower threshold reached on " + devname + ": " + intname + ", Utilization = " + str(
                            util) + "%!!!\n"
                        DTD(outint, dev, flow1, flow2)
                        state[index] = 0
                time.sleep(0.5)
                break


if __name__ == "__main__":
    main()
