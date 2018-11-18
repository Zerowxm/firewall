# firewall

## Data Structure

I organized rules into four groups:
inbound+tcp, inbound+udp, outbound+tcp, outbound+udp

Then each group is a hash map with Port as key, ips as value.

At the beginning, I have no idea how to organized the rules effectively, 
then I tried several data structures like set, map, list, tree, finally decide this data structure to store.
The reason is that, there might be many ranges for a single port, so it's better take it into consideration instead of viewing them as individual string.

 