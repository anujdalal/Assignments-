from xml.etree import ElementTree
from xml.etree.ElementTree import Element
from xml.dom import minidom
import xml.etree.ElementTree as etree




# this function pushes the flows to the devices, via the REST interface of the controller
def addflows():

    node = ''
    src = ''
    dst = ''
    flowname = ''
    flowid = ''

# this function constructs flows for each device, and destination address
def createflows():

    # These variables will be changed for each flow.
    node = []
    flname = 'S1toH1-tcp'
    flowid = '5'
    flowpriority = '116'
    ipprot = '6'
    ipaddr = '192.168.1.1'
    output = '5097'

    # Start XML document for flow
    root=Element('flow')
    root.set('xmlns', 'urn:opendaylight:flow:inventory')
    strict=Element('strict')
    strict.text='false'
    root.append(strict)
    name=Element('flow-name')
    name.text=flname
    root.append(name)
    id=Element('id')
    id.text=flowid
    root.append(id)
    tableid=Element('table-id')
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
    outconnector=Element('output-node-connector')
    outconnector.text=output
    outputaction.append(outconnector)
    maxlen=Element('max-length')
    maxlen.text='60'
    outputaction.append(maxlen)
    rstring=ElementTree.tostring(root, 'utf-8')
    reparsed=minidom.parseString(rstring)
    print reparsed.toprettyxml(indent="     ")
    reparsed.writexml(open(r'/home/anujdalal/Desktop/'+flname+'.xml','w'),addindent="     ",newl='\n')

createflows()
