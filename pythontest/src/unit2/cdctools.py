# -*- coding:utf-8 -*-
import os,chardet
def cdWalker(cdrom,cdcfile):
    export = ""
    for root, dirs, files in os.walk(cdrom):
        export += formatCDinfo(root, dirs, files)
        open(cdcfile, 'w').write(export)
        
def cdcGrep(cdcpath,keyword):
    filelist = os.listdir(cdcpath)
    for cdc in filelist:
        if ".cdc" in cdc:
            cdcfile = open(cdcpath+cdc)
            for line in cdcfile.readlines():
                if keyword in line:
                    print line

                    
def _smartcode(stream):
    """smart recove stream into UTF-8
    """
    ustring = stream
    codedetect = chardet.detect(ustring)["encoding"]
#    print codedetect
    try:
#        print ustring
        ustring = unicode(ustring, codedetect)
#        print ustring
#        return "%s%s" %("",ustring.encode("utf-8"))
        return ustring
    except:
        return u"bad unicode encode try!" 
    
def formatCDinfo(root, dirs, files):
    export = "\n" + _smartcode(root) + "\n"
    for d in dirs:
        export += "-d " + _smartcode(root) + _smartcode(d) + "\n"
    for f in files:
        export += "-f %s %s \n" % (_smartcode(root), _smartcode(f))
        export += "=" * 70  
        export += "\n"
        print export
    return export                   

        
if __name__ == '__main__': # this way the module can be
    CDROM = 'G:/'
    cdWalker(CDROM,"cdc/g.cdc")
#    cdcGrep('H:/workspace/pythontest/src/unit2/cdc/','Ubuntu')

