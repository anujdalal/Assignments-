import os
# this function constructs flows for each device, and destination address
def createflows():
    true = 1
    count = 0
    while true == 1:
        dev = raw_input('Enter the device you want to modify (openflow:x): ')
        request = raw_input('(A)dd a flow, or (m)odify an existing flow?')

        if request == 'a' or request == 'A':
            print 'Please fill in the following to add a flow'
            flowname = raw_input('Flow Name: ')
            flowid = raw_input('Flow ID: ')
            flowpriority = raw_input('Flow Priority: ')
            ipprot = raw_input('TCP/UDP: ')
            ipaddr = raw_input('Destination IP: ')
            outint = raw_input('Egress interface: ')

            os.system('''curl -u admin:admin -H 'Content-type: application/json' -X PUT -d '{
                      "flow": [{"id": "'''+flowid+'''", "match": { "ethernet-match": { "ethernet-type": { "type": "2048"}}, "ip-match": {"ip-protocol": "'''+ipprot+'''"},
                      "ipv4-destination": "'''+ipaddr+'''"}, "instructions": { "instruction": [{ "order": "0",
                      "apply-actions": { "action": [{ "order": "0", "output-action": { "output-node-connector": "'''+outint+'''",
                      "max-length": "60"}}]}}]}, "flow-name": "'''+flowname+'''", "installHw": "true", "strict": "false", "priority": "'''+flowpriority+'''",
                      "idle-timeout": "0", "hard-timeout": "0", "table_id": "0"}]}' 'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/flow-node-inventory:table/0/flow/'''+flowid+"'"'')

            addmore = 'Add another flow?'
            response = raw_input(addmore)
            if response == 'y' or response == 'Y':
                request = 'a'
            elif response == 'n' or response == 'N':
                true = 0
                break

        elif request == 'm' or request == 'M':
            if count == 0:
                flowid = raw_input('Flow ID: ')
                outint = raw_input('Egress interface: ')
            elif count > 1:
                dev = raw_input('Enter the device you want to modify (openflow:x): ')
                flowid = raw_input('Flow ID: ')
                outint = raw_input('Egress interface: ')
                break

            os.system('''curl -u admin:admin -H 'Content-type: application/json' -X PUT -d '{ "instruction": [{ "order": "0", "apply-actions": { "action": [{ "order": "0",
                      "output-action": { "output-node-connector": "'''+outint+'''", "max-length": "60"}}]}}]}' 'http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/'''+dev+'''/flow-node-inventory:table/0/flow/'''+flowid+'''/instructions/instruction/0' ''')
            count += 1
            modify = raw_input('Modify another flow?')
            if modify == 'y' or modify == 'Y':
                request = 'm'
            elif modify == 'n' or modify == 'N':
                true = 0
                break
createflows()

