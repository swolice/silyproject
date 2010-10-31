# -*- coding: UTF-8 -*-
import os
export = []
def test(): 
    for root, dirs, files in os.walk('h:/'):
        #export+="\n %s;%s;%s" % (root,dirs,files)
        export.append("\n %s;%s;%s" % (root,dirs,files))
    open('mycd3.cdc', 'a').write(''.join (export))
    
    file_object = open("mycd3.cdc",'r')
    try:
        all_the_text = file_object.read()
        print all_the_text
    finally:
        file_object.close()

if __name__=="__main__":
    test()