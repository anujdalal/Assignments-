# -*= coding: utf-8 -*-
from xml.etree.ElementTree import ElementTree
from xml.etree.ElementTree import Element
import xml.etree.ElementTree as etree




def addflows():

    node = ''
    src = ''
    dst = ''
    flowname = ''
    flowid = ''


def createflows():

    node = []
    flname = 'S1toH1-tcp'
    id = '5'
    priority = '116'
    ipprot = '6'
    ipaddr = '192.168.1.1'
    output = '5097'



    root=Element('flow')
    tree=ElementTree(root)
    strict=Element('strict')
    strict.text='false'
    root.append(strict)
    name=Element('flow-name')
    name.text=flname
    root.append(name)
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
    outputaction=Element('output-action')
    action.append(outputaction)
    outconnector=Element('output-node-connector')
    outconnector.text=output
    outputaction.append(outconnector)
    maxlen=Element('max-length')
    maxlen.text='60'
    outputaction.append(maxlen)
    print etree.tostring(root)
    tree.write(open(r'C:\users\anuj dalal\desktop\test.xml','w'))


createflows()
