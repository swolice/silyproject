# -*- coding:utf-8 -*-
import chardet
def _smartcode(stream):
    """smart recove stream into UTF-8
    """
    ustring = stream
    codedetect = chardet.detect(ustring)["encoding"]
    print codedetect
    try:
        print ustring
        ustring = unicode(ustring, codedetect)
        print ustring
#        return "%s %s" % ("", ustring.encode('utf8'))
        return ustring
    except:
        return u"bad unicode encode try!"      

print _smartcode("中文1212312");