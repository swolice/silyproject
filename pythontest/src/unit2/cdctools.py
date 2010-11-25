#-*- coding:utf-8 -*-
import os,chardet,pickle
from ConfigParser import RawConfigParser as rcp
def cdWalker(cdrom,cdcfile):
    export = ""
    for root, dirs, files in os.walk(cdrom):
        export += formatCDinfo(root, dirs, files)
        open(cdcfile, 'w').write(export)
        
def cdcGrep1(cdcpath,keyword):
    export = ""
    filelist = os.listdir(cdcpath) # 搜索目录中的文件
    for cdc in filelist: # 循环文件列表
        if ".cdc" in cdc:
            cdcfile = open(cdcpath+cdc) # 拼合文件路径，并打开文件
            for line in cdcfile.readlines(): # 读取文件每一行，并循环
                if keyword in line: # 判定是否有关键词在行中
                    #print line # 打印输出
                    export += cdc + ":  ["+smartcode(line)+"]<br>"
    print export
    
def cdcGrep(cdcpath,keyword):
    expDict = {}
    filelist = os.listdir(cdcpath) # 搜索目录中的文件
    for cdc in filelist: # 循环文件列表
        if ".cdc" in cdc:
            cdcfile = open(cdcpath+cdc) # 拼合文件路径，并打开文件
            expDict[cdc]=[]
            for line in cdcfile.readlines():
                if keyword in line: # 判定是否有关键词在行中
                #print line # 打印输出
                    expDict[cdc].append(line)
    pickle.dump(expDict,open("searched.dump","w"))

                    
def smartcode(stream):
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
    export = "\n" + smartcode(root) + "\n"
    for d in dirs:
        export += "-d " + smartcode(root) + smartcode(d) + "\n"
    for f in files:
        export += "-f %s %s \n" % (smartcode(root), smartcode(f))
        export += "=" * 70  
        export += "\n"
        print export
    return export                   

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
            filesAll.append("%s/%s"%(smartcode(root),smartcode(af)))
#            print "%s/%s"%(smartcode(root),smartcode(af))
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
        
if __name__ == '__main__': # this way the module can be
    CDROM = 'G:/'
#    cdWalker(CDROM,"cdc/g.cdc")
    cdcGrep('H:/workspace/pythontest/src/unit2/cdc/','需求')
#    cdcGrep(CDCPATH,"EVA")
    searcheDict = pickle.load(open("searched.dump"))
    for cdc in searcheDict.keys():
        print cdc
        for line in searcheDict[cdc]:
            print line.decode("utf-8")

