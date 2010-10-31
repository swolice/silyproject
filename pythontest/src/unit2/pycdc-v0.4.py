# -*- coding: gb2312 -*-
import sys,cmd
from cdctools import *

class PyCDC(cmd.Cmd):
    def __init__(self):
       cmd.Cmd.__init__(self)
       self.CDROM = 'i:/'
       self.CDDIR = 'cdc/'
       self.prompt="(PyCDC)>"
       self.intro = '''PyCDC0.5 使用说明:
    11 dir 目录名 #指定保存和搜索目录，默认是 "cdc"
    12 walk 文件名 #指定光盘信息文件名，使用 "*.cdc"
    13 find 关键词 #使用在保存和搜索目录中遍历所有.cdc 文件，输出含有关键词的行
    14 ? # 查询
    15 EOF # 退出系统，也可以使用Crtl+D(Unix)|Ctrl+Z(Dos/Windows)
    16 '''
    def help_EOF(self):
        print "退出程序"
    def do_EOF(self,line):
        sys.exit()
    def help_walk(self):
        print "扫描光盘的内容 输出*.cdc文件"
    def do_walk(self,filename):
        if filename == "":filename = raw_input("输入的cdc文件名：")
        print "扫描盘内容到：'%s'" % filename
        cdWalker(self.CDROM,self.CDDIR+filename)
        
    def help_dir(self):
        print "指定保存或搜索目录"
    def do_dir(self,pathname):
        if pathname == "":pathname = raw_input("请输入路径名：")
        self.CDDIR = pathname
        print "指定保存/搜索目录:'%s' ;默认是:'%s'" % (pathname,self.CDDIR)
    
    def help_dir(self):
        print "请输入关键字"
    def do_dir(self,keywork):
        if keywork == "" : keywork = raw_input("输入关键字:")
        print "输入关键字：'%s'" % filename
if __name__ == '__main__':
    cdc = PyCDC()
    cdc.cmdloop()
#    CDROM = 'i:/'
#    cdWalker(CDROM,"cdc/cdctools.cdc")

             
         
        
    