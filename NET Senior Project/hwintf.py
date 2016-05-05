#Base script grabbed from https://github.com/mininet/mininet/blob/master/examples/hwintf.py
#Modified by David Ballantyne (Carleton University)

#!/usr/bin/python

"""
This example shows how to add an interface (for example a real
hardware interface) to a network after the network is created.
"""

import re
import sys

from mininet.cli import CLI
from mininet.node import Controller, RemoteController, OVSController
from mininet.log import setLogLevel, info, error
from mininet.net import Mininet
from mininet.link import Intf
from mininet.topolib import TreeTopo
from mininet.util import quietRun

def checkIntf( intf ):
    "Make sure intf exists and is not configured."
    if ( ' %s:' % intf ) not in quietRun( 'ip link show' ):
        error( 'Error:', intf, 'does not exist!\n' )
        exit( 1 )
    ips = re.findall( r'\d+\.\d+\.\d+\.\d+', quietRun( 'ifconfig ' + intf ) )
    if ips:
        error( 'Error:', intf, 'has an IP address,'
               'and is probably in use!\n' )
        exit( 1 )

if __name__ == '__main__':
    setLogLevel( 'info' )

    # try to get hw intf from the command line; by default, use eth1[host1]
    intfName1 = sys.argv[ 1 ] if len( sys.argv ) > 1 else 'eth6'
    info( '*** Connecting to hw intf: %s' % intfName1 )

    info( '*** Checking', intfName1, '\n' )
    checkIntf( intfName1 )


    # try to get hw intf from the command line; by default, use eth3[host3
    intfName3 = sys.argv[ 1 ] if len( sys.argv ) > 1 else 'eth3'
    info( '*** Connecting to hw intf: %s' % intfName3 )

    info( '*** Checking', intfName3, '\n' )
    checkIntf( intfName3 )


 # try to get hw intf from the command line; by default, use eth2[host4]
    intfName4 = sys.argv[ 1 ] if len( sys.argv ) > 1 else 'eth1'
    info( '*** Connecting to hw intf: %s' % intfName4 )

    info( '*** Checking', intfName4, '\n' )
    checkIntf( intfName4 )




    # try to get hw intf from the command line; by default, use eth6[host2]
    intfName2 = sys.argv[ 1 ] if len( sys.argv ) > 1 else 'eth5'
    info( '*** Connecting to hw intf: %s' % intfName2 )

    info( '*** Checking', intfName2, '\n' )
    checkIntf( intfName2 )







#TEST adding remote controllers
   #c1 = net.addController( 'c1', ip='192.168.134.102')

    info( '*** Creating network\n' )
    #tree4 = TreeTopo(depth=2,fanout=2)	
    net = Mininet( topo=None )



    # Create switches
    s1 = net.addSwitch( 's1', listenPort=6634, mac='00:00:00:00:00:01', max_queue_size=1, use_htb=True )
    s2 = net.addSwitch( 's2', listenPort=6635, mac='00:00:00:00:00:02', max_queue_size=1, use_htb=True )
    s3 = net.addSwitch( 's3', listenPort=6636, mac='00:00:00:00:00:03', max_queue_size=1, use_htb=True )

    # Add Links between switches
    net.addLink( s1, s2)
    net.addLink( s1, s3)
    net.addLink( s2, s3)

    # Add controller to network
    c0 = RemoteController( 'c0', ip='192.168.134.102', port=6633 )
    #def net.RemoteController.__init__( ip = '192.169.134.102', port = 6633 )

    switch = net.switches[ 0 ]
    info( '*** Adding hardware interface', intfName1, 'to switch', s1, '\n' )
    _intf = Intf( intfName1, node=s1)

    info( '*** Adding hardware interface', intfName3, 'to switch', s3, '\n' )
    _intf = Intf( intfName3, node=s3 )

    info( '*** Adding hardware interface', intfName4, 'to switch', s3, '\n' )
    _intf = Intf( intfName4, node=s3 )


    info( '*** Adding hardware interface', intfName2, 'to switch', s1, '\n' )
    _intf = Intf( intfName2, node=s1 )

    info( '*** Note: you may need to reconfigure the interfaces for '
          'the Mininet hosts:\n', net.hosts, '\n' )

    # add hosts to switches
    #net.addLink( , s1)

#    sflow.s1.host = 192.168.134.102:161
#    sflow.s1.sampling = 512
#    sflow.s1.polling = 5
#    sflow.s1.header = 128

    #net.get('switch').start([poxController])
    net.addController(c0)



    net.start()
    CLI( net )
    net.stop()

#sudo tc qdisc add dev s1-eth1 root tbf rate 1000mbit burst 10kb latency 1ms
#sudo tc qdisc add dev s1-eth2 root tbf rate 1000mbit burst 10kb latency 1ms
#sudo tc qdisc add dev s3-eth1 root tbf rate 1000mbit burst 10kb latency 1ms
#sudo tc qdisc add dev s3-eth2 root tbf rate 1000mbit burst 10kb latency 1ms
