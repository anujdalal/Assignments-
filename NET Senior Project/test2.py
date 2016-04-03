import os
import re
import time

def getbytes():
	sent = []
	tmp = os.popen("sudo tshark -i enp0s3 -d tcp.port==6633,openflow -O openflow_v4 -Y 'ip.addr==192.168.134.1 && openflow_v4.multipart_reply.type == 4' -a duration:1").read()
	while not re.findall('(?<=Tx.bytes:\s)\w+', tmp):
		print "No capture. Trying again..."
		tmp = os.popen("sudo tshark -i enp0s3 -d tcp.port==6633,openflow -O openflow_v4 -Y 'ip.addr==192.168.134.1 && openflow_v4.multipart_reply.type == 4' -a duration:1").read()
	else:
		sent.extend(re.findall('(?<=Tx.bytes:\s)\w+', tmp))
		sent = map(int, sent)
		sent1 = sent[0]
		return sent1


link_capacity = 125000000
upper_threshold = 112500000 
lower_threshold = 87500000 
startTime = time.time()
sentBytes = getbytes()
while True:
	oldSentBytes = sentBytes
	#print oldSentBytes
	sentBytes = getbytes()
#	print sentBytes
	if sentBytes != oldSentBytes:
		elapsedTime = time.time() - startTime
		print elapsedTime
		netBytes = float((sentBytes - oldSentBytes)/elapsedTime)
		print netBytes
		linkUtil = round(float(netBytes/float(link_capacity))*100,2)
		print ("Utilization = " + str(linkUtil) + "%")
		startTime = time.time() - elapsedTime
	else:
		print "No change"
