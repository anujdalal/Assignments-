from xml.etree.ElementTree import Element
from xml.dom import minidom
from subprocess import call
import xml.etree.ElementTree as etree
import os
# this function constructs flows for each device, and destination address
def createflows():

    # These variables will be changed for each flow.
    node = 'Enter the device you want to modify (openflow:x): '
    directory = 'Directory for device: '
    flowname = 'Flow Name: '
    flid = 'Flow ID: '
    flpriority = 'Flow Priority: '
    transport = 'TCP/UDP: '
    ipv4 = 'Destination IP: '
    outint = 'Egress interface: '
    path='flows/'
    true = 1

    while (true == 1):
        dev = 'openflow:11' #raw_input(node)
        direc = 'S1' #raw_input(directory)
        path += direc+'/'
        print path
        print 'Please fill in the following to add a flow'
        flname = 'S1toH1-tcp' #raw_input(flowname)
        flowid = '5' #raw_input(flid)
        flowpriority = '115' #raw_input(flpriority)
        ipprot = '6' #raw_input(transport)
        ipaddr = '192.168.1.1/32' #raw_input(ipv4)
        output = '5097' #raw_input(outint)

        # Start XML document for flow
        root=Element('flow')
        root.set('xmlns', 'urn:opendaylight:flow:inventory')
        id=Element('id')
        id.text=flowid
        root.append(id)
        instructions=Element('instructions')
        root.append(instructions)
        instruction=Element('instruction')
        instructions.append(instruction)
        order=Element('order')
        order.text='0'
        instruction.append(order)
        applyactions=Element('apply-actions')
        instruction.append(applyactions)
        action=Element('action')
        applyactions.append(action)
        order1=Element('order')
        order1.text='0'
        action.append(order1)
        outputaction=Element('output-action')
        action.append(outputaction)
        maxlen=Element('max-length')
        maxlen.text='60'
        outputaction.append(maxlen)
        outconnector=Element('output-node-connector')
        outconnector.text=output
        outputaction.append(outconnector)
        match=Element('match')
        root.append(match)
        l2match=Element('ethernet-match')
        match.append(l2match)
        l2type=Element('ethernet-type')
        l2match.append(l2type)
        type=Element('type')
        type.text='2048'
        l2type.append(type)
        ipmatch=Element('ip-match')
        match.append(ipmatch)
        ipproto=Element('ip-protocol')
        ipproto.text=ipprot
        ipmatch.append(ipproto)
        ipv4addr=Element('ipv4-destination')
        ipv4addr.text=ipaddr
        match.append(ipv4addr)
        strict=Element('strict')
        strict.text='false'
        root.append(strict)
        name=Element('flow-name')
        name.text=flname
        root.append(name)
        tableid=Element('table_id')
        tableid.text='0'
        root.append(tableid)
        priority=Element('priority')
        priority.text=flowpriority
        root.append(priority)
        hard=Element('hard-timeout')
        hard.text='0'
        root.append(hard)
        idle=Element('idle-timeout')
        idle.text='0'
        root.append(idle)
        installhw=Element('installHw')
        installhw.text='true'
        root.append(installhw)
        rstring=etree.tostring(root, encoding='utf-8')
        reparsed=minidom.parseString(rstring)
        s=str(reparsed.toprettyxml(indent="     ", encoding='UTF-8'))
        #flow=open(r'' + path + flname, 'w')
        #flow.write(s)
        print s
        #os.system('curl -X PUT -d @'+path+flname+' -H "Content-Type: application/xml" -H "Accept: application/xml" --user admin:admin http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'+dev+'/table/0/flow/'+flowid)

        addmore = 'Add another flow?'
        response = raw_input(addmore)
        if (response == 'y' or 'Y'):
            true = 1
        else:
            break

createflows()
