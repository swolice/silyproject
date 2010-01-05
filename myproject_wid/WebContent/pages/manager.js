//设置树的点击事件
function treeClick(node,e) {
	 if(node.isLeaf()){
	 	center1.setTitle(node.text);
	 	try{
		 	var url = node.url.getUrl();
		 	Ext.getDom('content-panel').innerHTML='<iframe width="100%"  height="100%"  src="' + url + '">  </iframe>'; 
		}catch(D) {
			
		}	
	 
	 }
}
leafNode=function() {
	this.url = null;
}	
leafNode.prototype.setUrl = function(url)
{
	this.url = url;
}
leafNode.prototype.getUrl = function()
{
	return this.url;
}

var center = new Ext.Panel({ // raw
				id:'content-panel',
                region:'center',
                contentEl: 'center2'});
var center1 = new Ext.Panel({ // raw
				id:'content-panel1',
                region:'center',
                contentEl: 'center1',
                titlebar: true,                                       
                split:true,
                height: 80,
                minSize: 800,
                maxSize: 1000,
                collapsible: false,
                title:'功能',
                margins:'0 0 0 0',
              	items: [center]  
                });
                				                    
Ext.onReady(function(){
   //layout
   var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			new Ext.BoxComponent({
				region:'north',
				el: 'north',
				height:80
			}),
			new Ext.BoxComponent({
				region:'south',
				el: 'south',
				height:25
			}),
			{
			region:'west',
			id:'west-panel',
			split:true,
			width: 200,
			minSize: 175,
			maxSize: 400,
			margins:'0 0 0 0',
			layout:'accordion',
			title:'系统菜单',
			collapsible :true,
			layoutConfig:{animate:true} ,
		    items: [ {
						title:'功能树',
						border:false,
						html:'<div id="tree-div" style="overflow:auto;width:100%;height:100%"></div>'
			       }]
			},
	        center1//初始标签页
		 ]
	});
	var iconImg = 'pages/img/im2.gif';
	var requestPath="pages/"
	//设置树形面板
	var Tree = Ext.tree;
	Ext.tree.TreeNode.prototype.url=leafNode;
	// set the root node
	var root1 = new Ext.tree.TreeNode({
		    id:"root",
		    icon:iconImg,
		    text:"系统树"
		});
   var resourcesOrderSearch=new Ext.tree.TreeNode({
		id:'res-1',
		icon:iconImg,
		text:'订单下发查询',
		leaf:true
	});
   resourcesOrderSearch.url = new leafNode();
   resourcesOrderSearch.url.setUrl(requestPath + "search/interfaceSearchOrderSend.jsp");
   
   var resourcesOrderRepeatSend=new Ext.tree.TreeNode({
		id:'res-2',
		icon:iconImg,
		text:'订单重新下发',
		leaf:true
	});

   resourcesOrderRepeatSend.url = new leafNode();
   resourcesOrderRepeatSend.url.setUrl(requestPath + "longresource/ordersend.jsp");
   //-------------------------------------------------
    var interfaceSearchFinishNotify=new Ext.tree.TreeNode({
		id:'res-3',
		icon:iconImg,
		text:'长途资源报竣',
		leaf:true
	});

   interfaceSearchFinishNotify.url = new leafNode();
   interfaceSearchFinishNotify.url.setUrl(requestPath + "search/interfaceSearchFinishNotify.jsp");
//--------------------------------------------------------------

    var resources=new Ext.tree.TreeNode({
		id:'res-0',
		icon:iconImg,
		text:'长途资源',
		leaf:false
	});

	resources.appendChild([resourcesOrderRepeatSend,resourcesOrderSearch,interfaceSearchFinishNotify]);
   //下发省数据	
   var hubOrderSearch=new Ext.tree.TreeNode({
		id:'hub-1',
		icon:iconImg,
		text:'订单下发查询及重发',
		leaf:true
	});
 
   hubOrderSearch.url = new leafNode();
   hubOrderSearch.url.setUrl(requestPath + "search/interfaceSearchSendProvince.jsp");
   
    var hubOrderHistory=new Ext.tree.TreeNode({
		id:'hub-3',
		icon:iconImg,
		text:'订单下发历史记录',
		leaf:true
	});
 
   	hubOrderHistory.url = new leafNode();
  	hubOrderHistory.url.setUrl(requestPath + "search/interfaceSearchHubHistory.jsp");
    
    var hubTerminal=new Ext.tree.TreeNode({
		id:'hub-2',
		icon:iconImg,
		text:'端局报竣信息',
		leaf:true
	});
 
   hubTerminal.url = new leafNode();
   hubTerminal.url.setUrl(requestPath + "search/interfaceSearchTerminal.jsp");
   
//--------------------------------------------------------------
   
    var hubHeadLevel=new Ext.tree.TreeNode({
		id:'hub-0',
		icon:iconImg,
		text:'下发省数据',
		leaf:false
	});
	hubHeadLevel.appendChild([hubOrderSearch,hubTerminal,hubOrderHistory]);
	//客户同步发送
	 var orderCustSync=new Ext.tree.TreeNode({
		id:'CustSync-1',
		icon:iconImg,
		text:'客户同步发送',
		leaf:true
	});
 
   orderCustSync.url = new leafNode();
   orderCustSync.url.setUrl(requestPath + "search/interfaceSearchOrderCustSync.jsp");
   
    var orderCustSyncLevel=new Ext.tree.TreeNode({
		id:'CustSync-0',
		icon:iconImg,
		text:'客户同步',
		leaf:false
	});
	orderCustSyncLevel.appendChild(orderCustSync);


	//综合VPN平台数据发送
	 var VPNDataSend=new Ext.tree.TreeNode({
		id:'VPNDataSend-1',
		icon:iconImg,
		text:'综合VPN平台数据发送',
		leaf:true
	});
 
   VPNDataSend.url = new leafNode();
   VPNDataSend.url.setUrl(requestPath + "search/interfaceSearchVPNDataSend.jsp");
	
	 var VPNDataRepeatSend=new Ext.tree.TreeNode({
		id:'VPNDataSend-3',
		icon:iconImg,
		text:'综合VPN平台数据分发',
		leaf:true
	});
 
   VPNDataRepeatSend.url = new leafNode();
   VPNDataRepeatSend.url.setUrl(requestPath + "search/interfaceSearchVPNDataRepeatSend.jsp");
   //----------------------------------
    var interfaceSearchVPNFinishNotify=new Ext.tree.TreeNode({
		id:'VPNDataSend-2',
		icon:iconImg,
		text:'综合VPN平台报竣信息',
		leaf:true
	});

   interfaceSearchVPNFinishNotify.url = new leafNode();
   interfaceSearchVPNFinishNotify.url.setUrl(requestPath + "search/interfaceSearchVPNFinishNotify.jsp");
   //----------------------------
    var VPNDataSendLevel=new Ext.tree.TreeNode({
		id:'VPNDataSend-0',
		icon:iconImg,
		text:'综合VPN平台',
		leaf:false
	});
	VPNDataSendLevel.appendChild([VPNDataSend,interfaceSearchVPNFinishNotify,VPNDataRepeatSend]);
	
	
	
	
	
	//企业总机平台数据发送
	var interfaceSearchEnterpriseFinishNotify=new Ext.tree.TreeNode({
		id:'enterprisePlatformSend-2',
		icon:iconImg,
		text:'企业总机平台报竣信息',
		leaf:true
	});
 
   interfaceSearchEnterpriseFinishNotify.url = new leafNode();
   interfaceSearchEnterpriseFinishNotify.url.setUrl(requestPath + "search/interfaceSearchEnterpriseFinishNotify.jsp");
   //---------------------------------
	var enterprisePlatformSend=new Ext.tree.TreeNode({
		id:'enterprisePlatformSend-1',
		icon:iconImg,
		text:'企业总机平台数据发送',
		leaf:true
	});
 
   enterprisePlatformSend.url = new leafNode();
   enterprisePlatformSend.url.setUrl(requestPath + "search/interfaceSearchEnterprisePlatformSend.jsp");
   //---------------------------------
	var enterprisePlatformRepeatSend=new Ext.tree.TreeNode({
		id:'enterprisePlatformSend-3',
		icon:iconImg,
		text:'企业总机平台数据分发',
		leaf:true
	});
 
   enterprisePlatformRepeatSend.url = new leafNode();
   enterprisePlatformRepeatSend.url.setUrl(requestPath + "search/interfaceSearchEnterprisePlatformRepeatSend.jsp");
   //---------------------------------
    var enterprisePlatformSendLevel=new Ext.tree.TreeNode({
		id:'enterprisePlatformSend-0',
		icon:iconImg,
		text:'企业总机平台',
		leaf:false
	});
	enterprisePlatformSendLevel.appendChild([enterprisePlatformSend,interfaceSearchEnterpriseFinishNotify,enterprisePlatformRepeatSend]);
	
	
	
	
	
	
	
	
	//IVPN一点计费上传测试 
	var ftpIVPNDownloadTest=new Ext.tree.TreeNode({
		id:'downLoad-2',
		icon:iconImg,
		text:'下载测试',
		leaf:true
	});
 
   ftpIVPNDownloadTest.url = new leafNode();
   ftpIVPNDownloadTest.url.setUrl(requestPath + "search/ftpIVNPDownloadTest.jsp");
   //---------------------------------
	var ftpIVPNUploadTest=new Ext.tree.TreeNode({
		id:'downLoad-1',
		icon:iconImg,
		text:'上传测试',
		leaf:true
	});
 
   ftpIVPNUploadTest.url = new leafNode();
   ftpIVPNUploadTest.url.setUrl(requestPath + "search/ftpIVNPUploadTest.jsp");
   //---------------------------------
    var ftpIVPNTest=new Ext.tree.TreeNode({
		id:'downLoad-0',
		icon:iconImg,
		text:'IVPN一点计费上传测试',
		leaf:false
	});
	ftpIVPNTest.appendChild([ftpIVPNDownloadTest,ftpIVPNUploadTest]);
	
	
	
	//呼叫外包
	//---------------------------------
	var interfaceSearchBpoSendContent=new Ext.tree.TreeNode({
		id:'interfaceSearchBpo-1',
		icon:iconImg,
		text:'订单下发查询',
		leaf:true
	});
 
   interfaceSearchBpoSendContent.url = new leafNode();
   interfaceSearchBpoSendContent.url.setUrl(requestPath + "search/interfaceSearchBpoSend.jsp");
   
  
    //--------------------------------------------------------------------------------------
	var interfaceSearchBpoReceiveContent=new Ext.tree.TreeNode({
		id:'interfaceSearchBpo-2',
		icon:iconImg,
		text:'接收号百平台信息查询',
		leaf:true
	});
 
   interfaceSearchBpoReceiveContent.url = new leafNode();
   interfaceSearchBpoReceiveContent.url.setUrl(requestPath + "search/interfaceSearchBpoReceive.jsp");
   
       //--------------------------------------------------------------------------------------
	var interfaceSearchBpoCustSynContent=new Ext.tree.TreeNode({
		id:'interfaceSearchBpo-5',
		icon:iconImg,
		text:'呼叫外包客户编码同步',
		leaf:true
	});
 
   interfaceSearchBpoCustSynContent.url = new leafNode();
   interfaceSearchBpoCustSynContent.url.setUrl(requestPath + "search/interfaceSearchBpoCustSyn.jsp");
   
   //------------------------------------------------------------------------------------------
  
    var interfaceSearchBpo=new Ext.tree.TreeNode({
		id:'interfaceSearchBpo-0',
		icon:iconImg,
		text:'呼叫外包',
		leaf:false
	});
	interfaceSearchBpo.appendChild([interfaceSearchBpoSendContent,interfaceSearchBpoReceiveContent,interfaceSearchBpoCustSynContent]);
	//NmscIsmpEma企业总机平台 
	//------------------------------------------NmscIsmpEma
		var interfaceSearchNmscIsmpEmaSendContent=new Ext.tree.TreeNode({
		id:'interfaceSearchNmscIsmpEma-1',
		icon:iconImg,
		text:'NMSC/ISMP平台订单下发',
		leaf:true
	});
 
   interfaceSearchNmscIsmpEmaSendContent.url = new leafNode();
   interfaceSearchNmscIsmpEmaSendContent.url.setUrl(requestPath + "search/interfaceSearchNmscIsmpEmaSend.jsp");
   
  
    //--------------------------------------------------------------------------------------
	var interfaceSearchNmscIsmpEmaReceiveContent=new Ext.tree.TreeNode({
		id:'interfaceSearchNmscIsmpEma-3',
		icon:iconImg,
		text:'NMSC/ISMP平台报竣',
		leaf:true
	});
 
   interfaceSearchNmscIsmpEmaReceiveContent.url = new leafNode();
   interfaceSearchNmscIsmpEmaReceiveContent.url.setUrl(requestPath + "search/interfaceSearchNmscIsmpEmaReceive.jsp");
   
   //------------------------------------------------------------------------------------------
    var interfaceSearchNmscIsmpEma=new Ext.tree.TreeNode({
		id:'interfaceSearchNmscIsmpEma-0',
		icon:iconImg,
		text:'NMSC/ISMP平台综合办公（NmscIsmpEma）',
		leaf:false
	});
	interfaceSearchNmscIsmpEma.appendChild([interfaceSearchNmscIsmpEmaSendContent,interfaceSearchNmscIsmpEmaReceiveContent]);
	
	
	
	//电子运维 
	//------------------------------------------
		var interfaceSearchElecMaintainSendContent=new Ext.tree.TreeNode({
		id:'interfaceSearchElecMaintain-1',
		icon:iconImg,
		text:'电子运维下发',
		leaf:true
	});
 
   interfaceSearchElecMaintainSendContent.url = new leafNode();
   interfaceSearchElecMaintainSendContent.url.setUrl(requestPath + "search/interfaceSearchElecMaintainSend.jsp");
 
 	//发送------------------------------------------
		var interfaceSearchElecMaintainformRepeatSendContent=new Ext.tree.TreeNode({
		id:'interfaceSearchElecMaintain-2',
		icon:iconImg,
		text:'电子运维分发',
		leaf:true
	});
 
   interfaceSearchElecMaintainformRepeatSendContent.url = new leafNode();
   interfaceSearchElecMaintainformRepeatSendContent.url.setUrl(requestPath + "search/interfaceSearchElecMaintainformRepeatSend.jsp");
 
   //------------------------------------------------------------------------------------------
   
    	//报表------------------------------------------
		var interfaceSearchElecMaintainReport=new Ext.tree.TreeNode({
		id:'interfaceSearchElecMaintain-3',
		icon:iconImg,
		text:'电子运维报表',
		leaf:true
	});
 
   interfaceSearchElecMaintainReport.url = new leafNode();
   interfaceSearchElecMaintainReport.url.setUrl(requestPath + "search/interfaceSearchElecMaintainReport.jsp");
 
   //------------------------------------------------------------------------------------------
    var interfaceSearchElecMaintain=new Ext.tree.TreeNode({
		id:'interfaceSearchElecMaintain-0',
		icon:iconImg,
		text:'电子运维',
		leaf:false
	});
	interfaceSearchElecMaintain.appendChild([interfaceSearchElecMaintainSendContent,interfaceSearchElecMaintainformRepeatSendContent,interfaceSearchElecMaintainReport]);
	
	//领航平台2009/10/30
	//-----------------------------------------------------------------------------------------
	//领航客户发送信息
		var interfaceSearchBnetCustReceive=new Ext.tree.TreeNode({
		id:'bnet-leaf-1',
		icon:iconImg,
		text:'领航客户发送信息',
		leaf:true
	});
 
   interfaceSearchBnetCustReceive.url = new leafNode();
   interfaceSearchBnetCustReceive.url.setUrl(requestPath + "/search/Bnet_Cust_Open.jsp");
 //--------------------------------------------------------------------------------------------
 
 	//领航产品受理信息
		var interfaceSearchBnetProductReceive=new Ext.tree.TreeNode({
		id:'bnet-leaf-2',
		icon:iconImg,
		text:'领航产品受理信息',
		leaf:true
	});
 
   interfaceSearchBnetProductReceive.url = new leafNode();
   interfaceSearchBnetProductReceive.url.setUrl(requestPath + "/search/Bnet_Product_Recieve.jsp");
   //---------------------------------------------------------------------------------------------------------------
      //领航产品开通信息
		var interfaceSearchBnetProducOpen=new Ext.tree.TreeNode({
		id:'bnet-leaf-4',
		icon:iconImg,
		text:'领航产品开通信息',
		leaf:true
	});
 
   interfaceSearchBnetProducOpen.url = new leafNode();
   interfaceSearchBnetProducOpen.url.setUrl(requestPath + "/search/Bnet_Product_Open.jsp");
 
   //------------------------------------------------------------------------------------------
   
    //领航产品报俊信息
		var interfaceSearchBnetProductFinish=new Ext.tree.TreeNode({
		id:'bnet-leaf-3',
		icon:iconImg,
		text:'领航产品报俊信息',
		leaf:true
	});
    //
   interfaceSearchBnetProductFinish.url = new leafNode();
   interfaceSearchBnetProductFinish.url.setUrl(requestPath + "/search/Bnet_Product_Finish.jsp");
   
   //领航平台------------------------------------------------------------------------------------------
    var interfaceSearchBnet=new Ext.tree.TreeNode({
		id:'bnet-node-0',
		icon:iconImg,
		text:'领航平台',
		leaf:false
	});
	interfaceSearchBnet.appendChild([interfaceSearchBnetCustReceive,interfaceSearchBnetProductReceive,interfaceSearchBnetProducOpen,interfaceSearchBnetProductFinish]);
	
	
	root1.appendChild(resources);
	root1.appendChild(hubHeadLevel);
	root1.appendChild(orderCustSyncLevel);
	root1.appendChild(VPNDataSendLevel);
	root1.appendChild(enterprisePlatformSendLevel);
	root1.appendChild(ftpIVPNTest);
	root1.appendChild(interfaceSearchBpo);
	root1.appendChild(interfaceSearchNmscIsmpEma);
	root1.appendChild(interfaceSearchElecMaintain);
	root1.appendChild(interfaceSearchBnet);	
	var tree = new Tree.TreePanel({
		el:'tree-div',
		autoScroll:true,
		root:root1,
		line:true,
		selMode:true,
		animate:true,
		enableDD:true,
		border:false,
		rootVisible:true,
		containerScroll: true
	});

    tree.setRootNode(root1);
	// render the tree
	tree.render();
	root1.expand();
	tree.on('click',treeClick);
	//end loding
	setTimeout(
				function() {
					Ext.get('loading').remove();
					Ext.get('loading-mask').fadeOut({remove:true});
				}, 250
			  );

});
