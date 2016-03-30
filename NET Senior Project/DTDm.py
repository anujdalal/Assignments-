import re
import os
import time
import urllib2

def getbytesP(device, interface):

    auth_handler = urllib2.HTTPBasicAuthHandler()
    auth_handler.add_password(realm='application',
                              uri='http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:'+device+'/node-connector/openflow:'+device+':'+interface+'/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes',
                              user='admin',
                              passwd='admin')
    opener = urllib2.build_opener(auth_handler)
    urllib2.install_opener(opener)
    host = 'http://134.117.92.76:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:'+device+'/node-connector/openflow:'+device+':'+interface+'/opendaylight-port-statistics:flow-capable-node-connector-statistics/bytes'
    req = urllib2.Request(host)
    nodeInfo = urllib2.urlopen(req)
    data = nodeInfo.read()
    sent = (re.findall('(?<="transmitted":)\w+', data))
    sent = map(int, sent)
    return sent

def topo():
    dev = ['11', '13']
    int = [['5100', '5103'],['5100', '5099']]
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
    data = topo()
    while True:
        for index in range(len(data)):
            sentBytes = data[index]
            while True:
                oldSentBytes = sentBytes
                data = topo()
                sentBytes = data[index]
                if sentBytes != oldSentBytes:
                    byteRate = float(sentBytes - oldSentBytes)
                    print byteRate
                    util = round((byteRate/131072), 2)
                    print util
                else:
                    print "No change..."
                time.sleep(1)
                break


def DTD(outint,dev,flowid):

    os.system('''curl -u admin:admin -H 'Content-type: application/json' -X  PUT -d '{ "instruction":
            [{ "order": "0", "apply-actions": { "action": [{ "order": "0",
            "output-action": { "output-node-connector": "'''+outint+'''", "max-length": "60"}}]}}]}'
            'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/
            flow-node-inventory:table/0/flow/'''+flowid+'''/instructions/instruction/0' ''')



def main():
    linkUtil()


if __name__ == "__main__":
    main()
