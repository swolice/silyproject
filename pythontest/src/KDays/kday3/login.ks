#-*- coding: UTF-8 -*-
from Karrigell_QuickForm import Karrigell_QuickForm

def index(**args):
    p = Karrigell_QuickForm('teste','POST','index','登陆信息')
    p.addElement('text','login', {'size':20, 'maxlength':50})
    p.addElement('text','password', {'size':20, 'maxlength':50})
    p.addRule('login','required',"用户名必须填写")
    p.addGroup(["submit","botao_enviar","submit","Send"],["reset","botao_limpar","reset","Clear"])
    p.display()

    if 0 == len(QUERY):
        return
    else:
        print '<html><head><meta http-equiv="content-type" content="text/html; charset=UTF-8"></head><body>'
        print '<h3>测试Karrigell_QuickForm的结果</h3>登录帐号为: %s<br/>密码为: %s' % (QUERY["login"], QUERY["password"])
        print '</body></html>'
