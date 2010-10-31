# _*_ coding: GBK _*_
import os,sys
print sys.argv
CDROM='h:/'
def cdWalker(cdrom,cdcfile):
    export=""
    try:
        path = cdcfile[0:cdcfile.rindex("/")]
        if os.path.exists(path):
            print '目录存在1'
        else:
            os.makedirs(path)
    except:
        try:
            path = cdcfile[0:cdcfile.rindex("\\")]
            if os.path.exists(path):
                print "目录存在2"
            else:
                os.makedirs(path)                                 
        except:
            print "异常信息"

    for root,dirs,files in os.walk(cdrom):
        export += "\n %s;%s;%s" %(root,dirs,files)
    open(cdcfile,"w").write(export)
    #cdWalker("h:/python","cd1.cdc")
#    if "-e"==sys.argv[1]:
#        cdWalker(CDROM,sys.argv[2])
#        print "记录光盘信息到 %s" % sys.argv[2]
#    elif "-d"==sys.argv[1]:
#        if "-k"==sys.argv[3]:
#            #进行文件搜索
#            print "文件搜索"
#        else:
#            print '''PyCDC 使用方式:
#            python pycdc.py -d cdc -k 中国火
#            #搜索 cdc 目录中的光盘信息，寻找有“中国火”字样的文件或是目录在哪张光盘中
#            '''
#    else:
#        print '''PyCDC 使用方式:
#            python pycdc.py -d cdc -k 中国火
#            #搜索 cdc 目录中的光盘信息，寻找有“中国火”字样的文件或是目录在哪张光盘中
#            '''

if __name__=="__main__":
    cdWalker("h:/","cdc/cd22.cdc")