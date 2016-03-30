import re
import os
import time
import urllib2

def getbytesP(device, interface):

    auth_handler = urllib2.HTTPBasicAuthHandler()
    auth_handler.add_password(realm='application',
                              uri='http://localhost:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:'+device+'/node-connector/openflow:'+device+':'+interface+'/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes',
                              user='admin',
                              passwd='admin')
    opener = urllib2.build_opener(auth_handler)
    urllib2.install_opener(opener)
    host = 'http://localhost:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:'+device+'/node-connector/openflow:'+device+':'+interface+'/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes'
    req = urllib2.Request(host)
    nodeInfo = urllib2.urlopen(req)
    data = nodeInfo.read()
    sent = (re.findall('(?<="transmitted":)\w+', data))
    sent = map(int, sent)
    return sent

def topo():
    dev = ['11', '13']
    int = [['5100'],['5100']]
    data = []

    for index in range(len(dev)):
        if index is 0:
            device = dev[index]
            for index in range(len(int[0])):
                interface = int[0][index]
                data.extend(getbytesP(device, interface))
        elif index is 1:
            device = dev[index]
            for index in range(len(int[1])):
                interface = int[1][index]
                data.extend(getbytesP(device, interface))
    return data

def linkUtil():

    return data




def DTD(outint,dev):

    flowid = ['9','11']
    for index in range(len(flowid)):
        print ('''curl -u admin:admin -H 'Content-type: application/json' -X  PUT -d '{ "instruction":[{ "order": "0", "apply-actions": { "action": [{ "order": "0", "output-action": { "output-node-connector": "'''+outint+'''", "max-length": "60"}}]}}]}' 'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/flow-node-inventory:table/0/flow/'''+flowid[index]+'''/instructions/instruction/0' ''')

        os.system ('''curl -u admin:admin -H 'Content-type: application/json' -X  PUT -d '{ "instruction":[{ "order": "0", "apply-actions": { "action": [{ "order": "0", "output-action": { "output-node-connector": "'''+outint+'''", "max-length": "60"}}]}}]}' 'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/flow-node-inventory:table/0/flow/'''+flowid[index]+'''/instructions/instruction/0' ''')



def main():
    data = topo()
    state = 0
    while True:
        for index in range(len(data)):
            if index is 0:
                outint = '5100'
                altint = '5103'
                dev = '11'
            elif index is 1:
                outint = '5099'
                altint = '5103'
                dev = '13'
            sentBytes = data[index]
            while True:
                oldSentBytes = sentBytes
                data = topo()
                sentBytes = data[index]
                if sentBytes != oldSentBytes:
                    byteRate = float(sentBytes - oldSentBytes)/5
                    print byteRate, index
                    util = round((byteRate/125000000), 2)
                    print util
                    if util > 0.85 and state is 0:
                        DTD(altint,dev)
                        state = 1
                        print "Upper threshold reached. Util:"+str(util)+"%. Flows Diverted."
                    elif util < 0.50 and state is 1:
                        DTD(outint,dev)
                        state = 0
                        print "Lower threshold reached. Util:"+str(util)+"%. Flows Diverted."
                else:
                    print "No change...", index
                time.sleep(0.50)
                break


if __name__ == "__main__":
    main()
