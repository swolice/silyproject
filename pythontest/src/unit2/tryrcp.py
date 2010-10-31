# -*- coding:gb2312 -*-
from ConfigParser import RawConfigParser as rcp
def testrcp():
    cfg = rcp()
    cfg.add_section("Info")
    cfg.set("Info","ImagePath","Z:/python2-chardet-2.0.1/docs/images/tip.png")
    cfg.set("Info","foo","CD 信息")
    cfg.write(open("try.ini","w"))
    
if __name__ == "__main__":
    testrcp()