# illumio-Jeff-Wu
 Illumio Technical Assessment 2024

## Problem
Write a program that can parse a file containing flow log data and maps each row to a tag based on a lookup table. The lookup table is defined as a csv file, and it has 3 columns, dstport,protocol,tag.   The dstport and protocol combination decide what tag can be applied.

## Doubt
In our sample_flow_log, we do not have any des_port that is 22, but in our `PortProtocolCombinationCountExpectedResult.csv`, there is output of 22
In our look_up_table, we do not have any 1024 des_port, but we still have this in `PortProtocolCombinationCountExpectedResult.csv`
I am wondering if the output files are correct
### PortProtocolCombinationCountExpectedResult.csv
```
Port/Protocol Combination Counts: 
Port,Protocol,Count
22,tcp,1
23,tcp,1
25,tcp,1
110,tcp,1
143,tcp,1
443,tcp,1
993,tcp,1
1024,tcp,1
49158,tcp,1
80,tcp,1
```

## Assumptions
The program will read two input files: `sample_flow_log.csv` and `lookup_table.csv`. The `lookup_table.csv` will be used to populate a hashmap that maps port-protocol pairs to their corresponding tags. After building this hashmap, the program will process the `sample_flow_log.csv` file. For each valid entry in the flow log (lines with at least 14 fields), it will `count occurrences of each tag` and `port-protocol combination` based on the data from the lookup table.

## Usages
Go to the root folder, open a terminal
run `javac *.java`
then run `java Main lookup_table.csv sample_flow_log.csv`
This will generate us two output files [port_protocol_com_count_report.csv](https://github.com/jeffwu0724/illumio-Jeff-Wu/blob/master/src/output/port_protocol_com_count_report.csv) and [tag_count_report.csv](https://github.com/jeffwu0724/illumio-Jeff-Wu/blob/master/src/output/tag_count_report.csv)based on the input files [lookup_table.csv](https://github.com/jeffwu0724/illumio-Jeff-Wu/blob/master/src/lookup_table.csv) and [sample_flow_log.csv](https://github.com/jeffwu0724/illumio-Jeff-Wu/blob/master/src/sample_flow_log.csv)

## Test
There is a unit test, but I would like to make sure the doubt is clear before I can add more test case

## Files
### sample_flow_log.csv
```
2 123456789012 eni-0a1b2c3d 10.0.1.201 198.51.100.2 443 49153 6 25 20000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-4d3c2b1a 192.168.1.100 203.0.113.101 23 49154 6 15 12000 1620140761 1620140821 REJECT OK
2 123456789012 eni-5e6f7g8h 192.168.1.101 198.51.100.3 25 49155 6 10 8000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-9h8g7f6e 172.16.0.100 203.0.113.102 110 49156 6 12 9000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-7i8j9k0l 172.16.0.101 192.0.2.203 993 49157 6 8 5000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-6m7n8o9p 10.0.2.200 198.51.100.4 143 49158 6 18 14000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-1a2b3c4d 192.168.0.1 203.0.113.12 1024 80 6 10 5000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 203.0.113.12 192.168.0.1 80 1024 6 12 6000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 10.0.1.102 172.217.7.228 1030 443 6 8 4000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-5f6g7h8i 10.0.2.103 52.26.198.183 56000 23 6 15 7500 1620140661 1620140721 REJECT OK
2 123456789012 eni-9k10l11m 192.168.1.5 51.15.99.115 49321 25 6 20 10000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 192.168.1.6 87.250.250.242 49152 110 6 5 2500 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-2d2e2f3g 192.168.2.7 77.88.55.80 49153 993 6 7 3500 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-4h5i6j7k 172.16.0.2 192.0.2.146 49154 143 6 9 4500 1620140661 1620140721 ACCEPT OK 
```

### lookup_table.csv
```
dstport,protocol,tag
25,tcp,sv_P1
68,udp,sv_P2
23,tcp,sv_P1
31,udp,SV_P3
443,tcp,sv_P2
22,tcp,sv_P4
3389,tcp,sv_P5
0,icmp,sv_P5 
110,tcp,email
993,tcp,email
143,tcp,email
```

### PortProtocolCombinationCountExpectedResult.csv
```
Port/Protocol Combination Counts: 
Port,Protocol,Count
22,tcp,1
23,tcp,1
25,tcp,1
110,tcp,1
143,tcp,1
443,tcp,1
993,tcp,1
1024,tcp,1
49158,tcp,1
80,tcp,1
```

### TagCountExpectedResult.csv
```
Tag Counts:
Tag,Count
sv_P2,1
sv_P1,2
sv_P4,1
email,3
Untagged,9
```
## This is the output that generated with the code
### port_protocol_com_count_report.csv
```
Port/Protocol Combination Counts: 
Port,Protocol,Count
23,tcp,1
25,tcp,1
110,tcp,1
143,tcp,1
443,tcp,1
993,tcp,1
```

### tag_count_report.csv
```
Tag Counts:
Tag,Count
sv_p2,1
sv_p1,2
email,3
Untagged,8
```