# -*- coding:utf-8 -*-

from xml.etree.ElementTree import ElementTree
import chardet
def readXml():
    xmlFileName = "deptorg.xml"
    #xmlStr = open(xmlFileName,"r").read()
    xmlparse = ElementTree()
    xmlparse.parse(xmlFileName)
    elem = xmlparse.getroot();
    dept = elem.findall("node/node")
    for d in dept:
        print d
        print d.attrib["TEXT"]#.encode("utf8")
        
if __name__ == "__main__":
    readXml()
