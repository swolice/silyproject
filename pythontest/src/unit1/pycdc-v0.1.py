# _*_ coding: GBK _*_
import os,sys
print sys.argv
CDROM='h:/'
def cdWalker(cdrom,cdcfile):
    export=""
    try:
        path = cdcfile[0:cdcfile.rindex("/")]
        if os.path.exists(path):
            print 'Ŀ¼����1'
        else:
            os.makedirs(path)
    except:
        try:
            path = cdcfile[0:cdcfile.rindex("\\")]
            if os.path.exists(path):
                print "Ŀ¼����2"
            else:
                os.makedirs(path)                                 
        except:
            print "�쳣��Ϣ"

    for root,dirs,files in os.walk(cdrom):
        export += "\n %s;%s;%s" %(root,dirs,files)
    open(cdcfile,"w").write(export)
    #cdWalker("h:/python","cd1.cdc")
#    if "-e"==sys.argv[1]:
#        cdWalker(CDROM,sys.argv[2])
#        print "��¼������Ϣ�� %s" % sys.argv[2]
#    elif "-d"==sys.argv[1]:
#        if "-k"==sys.argv[3]:
#            #�����ļ�����
#            print "�ļ�����"
#        else:
#            print '''PyCDC ʹ�÷�ʽ:
#            python pycdc.py -d cdc -k �й���
#            #���� cdc Ŀ¼�еĹ�����Ϣ��Ѱ���С��й����������ļ�����Ŀ¼�����Ź�����
#            '''
#    else:
#        print '''PyCDC ʹ�÷�ʽ:
#            python pycdc.py -d cdc -k �й���
#            #���� cdc Ŀ¼�еĹ�����Ϣ��Ѱ���С��й����������ļ�����Ŀ¼�����Ź�����
#            '''

if __name__=="__main__":
    cdWalker("h:/","cdc/cd22.cdc")