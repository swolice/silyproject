# -*- coding:utf-8 -*-
import os
from cdctools import _smartcode
from ConfigParser import RawConfigParser as rcp

def iniCDinfo(cdrom,cdcfile):
    '''光盘信息.ini 格式化函式
    @note: 直接利用 os.walk() 函式的输出信息由ConfigParser.RawConfigParser
    进行重组处理成 .ini 格式文本输出并记录
    @param cdrom: 光盘访问路径
    @param cdcfile: 输出的光盘信息记录文件(包含路径,绝对、相对都可以)
    @return: 无,直接输出成组织好的类.ini 的*.cdc 文件
    '''
    walker = {}
    filesAll = []
    for root, dirs, files in os.walk(cdrom):
        walker[root]=(dirs,files) # 这里是个需要理解的地方
        for af in files:
            filesAll.append("%s/%s"%(_smartcode(root),_smartcode(af)))
#            print "%s/%s"%(_smartcode(root),_smartcode(af))
        cfg = rcp()
        cfg.add_section("Info")
        cfg.add_section("Comment")
        cfg.set("Info", 'ImagePath', cdrom)
        cfg.set("Info", 'Volume', cdcfile)
        dirs = walker.keys()
        i = 0
        for d in dirs:
            i+=1
            cfg.set("Comment", str(i),d)
            for p in walker:
                try:
                    cfg.add_section(p)
                except:
                    continue
                for f in walker[p][1]:
                    cfg.set(p, f, os.stat("%s/%s"%(p,f)).st_size)
        cfg.write(open(cdcfile,"w"))
#    print filesAll
    findThirdMax(filesAll)
        
def findThirdMax(fileAll):
    print fileAll
    
    fileAll.sort(lambda x,y: cmp(os.stat(y).st_size, os.stat(x).st_size))
#    for i in fileAll:
#        for j in fileAll:
#            if(os.stat(i).st_size<os.stat(j).st_size):
                
    print fileAll
    print fileAll[0],_smartcode('大小：'),os.stat(fileAll[0]).st_size
    print fileAll[1],_smartcode('大小：'),os.stat(fileAll[1]).st_size
    print fileAll[2],_smartcode('大小：'),os.stat(fileAll[2]).st_size

if __name__ == "__main__":
#    print "111111"
    iniCDinfo("i:/","i.cdc")