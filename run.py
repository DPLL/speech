#!/usr/bin/python

##########################
# Yunlong Gao
#########################

import os
import re
from subprocess import Popen, PIPE, STDOUT, check_output
from sys import *
import glob
import socket
import pickle
from array import array

UDP_IP = '127.0.0.1'
UDP_PORT = 9999

def main():

    # run matlab to add noise
    #matlabCmd = 'matlab -nojvm < ~/Downloads/sphinx4-1.0beta6/mat.m'
    #p = Popen(matlabCmd, shell=True)
    #p.wait()

    # results stores the string array for further operation
    results = []
    wordSeq = ''
    for file in sorted(glob.glob('./wav/*.wav')):
 #    for file in sorted(glob.glob('./noise/*.wav')):
        #print file
        cmd = 'java -jar bin/LatticeDemo.jar ' + file
        #print cmd
        out = check_output(cmd, shell=True)
        #print out
        
        pat = 'I heard: '
        result = re.search(pat+'(.*)', out)
        if result:
            #print result.group()
            print result.group(1)
            results.append(result.group(1))        
            wordSeq += result.group(1) + '|'
    print results
    print wordSeq

    #wordSeq = 'occidental|as is story|atlantic|enter being us|'
    #send the converted results to MHMM server through UDP
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    except socket.error:
        print 'Failed to create socket'
        sys.exit()
    #print pickle.dumps(results)
    #print repr(pickle.loads(pickle.dumps(results)))

    try:
        #sock.sendto(pickle.dumps(results), (UDP_IP, UDP_PORT))
        sock.sendto(wordSeq, (UDP_IP, UDP_PORT))
    except socket.error:
        print 'Failed to send message to UDP server'
        sys.exit()

if __name__ == '__main__':
    main()
