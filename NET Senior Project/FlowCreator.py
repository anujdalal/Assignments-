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


    for node 'S1':
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



        print etree.tostring(root)



createflows()
