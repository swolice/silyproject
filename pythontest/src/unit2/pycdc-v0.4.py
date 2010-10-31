# -*- coding: gb2312 -*-
import sys,cmd
from cdctools import *

class PyCDC(cmd.Cmd):
    def __init__(self):
       cmd.Cmd.__init__(self)
       self.CDROM = 'i:/'
       self.CDDIR = 'cdc/'
       self.prompt="(PyCDC)>"
       self.intro = '''PyCDC0.5 ʹ��˵��:
    11 dir Ŀ¼�� #ָ�����������Ŀ¼��Ĭ���� "cdc"
    12 walk �ļ��� #ָ��������Ϣ�ļ�����ʹ�� "*.cdc"
    13 find �ؼ��� #ʹ���ڱ��������Ŀ¼�б�������.cdc �ļ���������йؼ��ʵ���
    14 ? # ��ѯ
    15 EOF # �˳�ϵͳ��Ҳ����ʹ��Crtl+D(Unix)|Ctrl+Z(Dos/Windows)
    16 '''
    def help_EOF(self):
        print "�˳�����"
    def do_EOF(self,line):
        sys.exit()
    def help_walk(self):
        print "ɨ����̵����� ���*.cdc�ļ�"
    def do_walk(self,filename):
        if filename == "":filename = raw_input("�����cdc�ļ�����")
        print "ɨ�������ݵ���'%s'" % filename
        cdWalker(self.CDROM,self.CDDIR+filename)
        
    def help_dir(self):
        print "ָ�����������Ŀ¼"
    def do_dir(self,pathname):
        if pathname == "":pathname = raw_input("������·������")
        self.CDDIR = pathname
        print "ָ������/����Ŀ¼:'%s' ;Ĭ����:'%s'" % (pathname,self.CDDIR)
    
    def help_dir(self):
        print "������ؼ���"
    def do_dir(self,keywork):
        if keywork == "" : keywork = raw_input("����ؼ���:")
        print "����ؼ��֣�'%s'" % filename
if __name__ == '__main__':
    cdc = PyCDC()
    cdc.cmdloop()
#    CDROM = 'i:/'
#    cdWalker(CDROM,"cdc/cdctools.cdc")

             
         
        
    