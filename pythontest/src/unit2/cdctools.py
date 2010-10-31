# -*- coding: utf-8 -*-
import os
def cdWalker(cdrom,cdcfile):
    export = ""
    for root, dirs, files in os.walk(cdrom):
        export+="\n %s;%s;%s" % (root,dirs,files)
        open(cdcfile, 'w').write(export)
        
def cdcGrep(cdcpath,keyword):
    filelist = os.listdir(cdcpath)
    for cdc in filelist:
        if ".cdc" in cdc:
            cdcfile = open(cdcpath+cdc)
            for line in cdcfile.readlines():
                if keyword in line:
                    print line
                    
        
if __name__ == '__main__': # this way the module can be
#    CDROM = 'i:/'
#    cdWalker(CDROM,"cdc/cdctools.cdc")
    cdcGrep('H:/workspace/pythontest/src/unit2/cdc/','Ubuntu')

