# -*- coding: utf-8 -*-
from threading import Thread # 导入线程支持模块
from ConfigParser import RawConfigParser as rcp
from cdctools import *
import time,os
class grepIt(Thread):
    def __init__ (self,cdcfile,keyword):
        Thread.__init__(self)
        self.cdcf = cdcfile
        self.keyw = keyword.upper()
        self.report = ""
    
    def run(self):
        if ".cdc" in self.cdcf:
            self.report = markIni(self.cdcf,self.keyw)
            
def markIni(cdcfile,keyword):
    '''配置文件模式匹配函式:
    '''
    report = ""
    keyw = keyword.upper()
    cfg = rcp()
    cfg.read(cdcfile)
    nodelist = cfg.sections()
    nodelist.remove("Comment")
    nodelist.remove("Info")
    for node in nodelist:
        if keyw in node.upper():
            print node
            report += "\n %s"%node # error as "\n",node|str(node)...
            continue
        else:
            for item in cfg.items(node):
                if keyw in item[0].upper():
                    report += "\n %s/%s %s"%(smartcode(node),smartcode(item[0]),u'文件大小:'+smartcode(str(item[1])))
    return report

def grpSearch(cdcpath,keyword):
    '''多线程群体搜索函式:
    '''
    begin = time.time()
    filelist = os.listdir(cdcpath) # 搜索目录中的文件
    searchlist = [] # 记录发起的搜索编程
    for cdcf in filelist:
        if ".cdc" in cdcf:
            pathcdcf = "%s/%s"%(cdcpath,cdcf)
            current = grepIt(pathcdcf,keyword) # 初始化线程对象
            searchlist.append(current) # 追加记录线程队列
            current.start() # 发动线程处理
    for searcher in searchlist:
        searcher.join()
        print "Search from ",searcher.cdcf," out ",searcher.report
        print "usage %s s"%(time.time()-begin)
     
if __name__ == '__main__':
    '''直接利用模块测试部分,先检验是否正确可用
    '''
    grpSearch('H:/workspace/pythontest/src/unit2/cdc','需求')