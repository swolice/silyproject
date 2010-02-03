-- ---start-------Dv_Admin-------start-------
create table Dv_Admin (
	id int auto_increment primary key, 
	username varchar(50), 
	password varchar(50), 
	flag text, 
	LastLogin datetime, 
	LastLoginIP varchar(50), 
	adduser varchar(50), 
	AcceptIP varchar(255)
);

-- ---start-------Dv_bbs1-------start-------
create table Dv_bbs1 (
	AnnounceID int auto_increment primary key, 
	ParentID int, 
	BoardID int, 
	UserName varchar(50), 
	postuserid int, 
	Topic varchar(255), 
	Body text, 
	DateAndTime datetime, 
	Dv_bbs1_length int, 
	RootID int, 
	layer int, 
	orders int, 
	isbest char(1), 
	ip varchar(40), 
	Expression varchar(100), 
	locktopic int, 
	signflag char(1), 
	emailflag char(1), 
	isagree varchar(250), 
	isupload char(1), 
	isaudit char(1), 
	PostBuyUser text, 
	UbbList varchar(250), 
	GetMoney int, 
	UseTools varchar(255), 
	GetMoneyType char(1)
);

-- ---start-------Dv_bbs2-------start-------
create table Dv_bbs2 (
	AnnounceID int auto_increment primary key, 
	ParentID int, 
	BoardID int, 
	UserName varchar(50), 
	PostUserID int, 
	Topic varchar(250), 
	Body text, 
	DateAndTime datetime, 
	Dv_bbs2_length int, 
	RootID int, 
	layer int, 
	orders int, 
	isbest char(1), 
	ip varchar(40), 
	Expression varchar(100), 
	locktopic int, 
	signflag char(1), 
	emailflag char(1), 
	isagree varchar(50), 
	isupload char(1), 
	isaudit char(1), 
	PostBuyUser text, 
	UbbList varchar(255), 
	GetMoney int, 
	UseTools varchar(255), 
	GetMoneyType char(1)
);

-- ---start-------Dv_BbsLink-------start-------
create table Dv_BbsLink (
	id int, 
	boardname varchar(50), 
	readme varchar(255), 
	url varchar(150), 
	logo varchar(250), 
	islogo int
);

-- ---start-------Dv_BbsNews-------start-------
create table Dv_BbsNews (
	id int auto_increment primary key, 
	boardid int, 
	title varchar(50), 
	content text, 
	username varchar(50), 
	addtime datetime, 
	bgs varchar(100)
);

-- ---start-------Dv_BestTopic-------start-------
create table Dv_BestTopic (
	id int auto_increment primary key, 
	Announceid int, 
	Rootid int, 
	Boardid int, 
	title varchar(250), 
	PostUserName varchar(50), 
	PostUserID int, 
	dateandtime datetime, 
	Expression varchar(100)
);

-- ---start-------Dv_Board-------start-------
create table Dv_Board (
	boardid int, 
	BoardType varchar(50), 
	ParentID int, 
	ParentStr varchar(250), 
	Depth int, 
	RootID int, 
	Child int, 
	orders int, 
	readme varchar(255), 
	BoardMaster varchar(100), 
	PostNum int, 
	TopicNum int, 
	indexIMG varchar(255), 
	todayNum int, 
	boarduser text, 
	LastPost varchar(255), 
	sid int, 
	Board_Setting text, 
	Board_Ads text, 
	Board_user varchar(250), 
	IsGroupSetting varchar(200), 
	BoardTopStr varchar(255), 
	CID char(1), 
	Rules text
);

-- ---start-------Dv_BoardPermission-------start-------
create table Dv_BoardPermission (
	Pid int auto_increment primary key, 
	Boardid int, 
	GroupID int, 
	PSetting text
);

-- ---start-------Dv_BookMark-------start-------
create table Dv_BookMark (
	id int auto_increment primary key, 
	username varchar(50), 
	url varchar(100), 
	topic varchar(100), 
	addtime datetime
);

-- ---start-------Dv_ChallengeInfo-------start-------
create table Dv_ChallengeInfo (
	D_ForumID varchar(50), 
	D_UserName varchar(50), 
	D_Password varchar(50), 
	D_RealName varchar(50), 
	D_identityNo varchar(50), 
	D_sex varchar(5), 
	D_postcode varchar(10), 
	D_address text, 
	D_receiver varchar(50), 
	D_email varchar(100), 
	D_forumname varchar(100), 
	D_forumurl varchar(200), 
	D_telephone varchar(50), 
	D_mobile varchar(20), 
	D_forumProvider varchar(50), 
	D_version varchar(20), 
	D_challengePassWord varchar(50), 
	D_icpNO varchar(50), 
	D_web_intro text
);

-- ---start-------DV_ChanOrders-------start-------
create table DV_ChanOrders (
	O_ID int auto_increment primary key, 
	O_type int, 
	O_mobile varchar(20), 
	O_Username varchar(50), 
	O_isApply char(1), 
	O_issuc char(1), 
	O_PayMoney double, 
	O_Paycode varchar(30), 
	O_BoardID int, 
	O_TopicID int, 
	O_AddTime datetime
);

-- ---start-------Dv_Friend-------start-------
create table Dv_Friend (
	F_id int auto_increment primary key, 
	F_username varchar(50), 
	F_friend varchar(50), 
	F_addtime datetime, 
	F_Mod char(1), 
	F_userid int
);

-- ---start-------Dv_GroupName-------start-------
create table Dv_GroupName (
	id int auto_increment primary key, 
	GroupName varchar(50)
);

-- ---start-------Dv_Help-------start-------
create table Dv_Help (
	H_ID int auto_increment primary key, 
	H_ParentID int, 
	H_title varchar(250), 
	H_content text, 
	H_type char(1), 
	H_stype int, 
	H_bgimg varchar(100), 
	H_Addtime datetime
);

-- ---start-------Dv_Log-------start-------
create table Dv_Log (
	l_id int auto_increment primary key, 
	l_announceid int, 
	l_boardid int, 
	l_touser varchar(50), 
	l_username varchar(50), 
	l_content varchar(255), 
	l_addtime datetime, 
	l_ip varchar(50), 
	l_type char(1)
);

-- ---start-------Dv_Message-------start-------
create table Dv_Message (
	id int auto_increment primary key, 
	sender varchar(50), 
	incept varchar(50), 
	title varchar(100), 
	content text, 
	flag int, 
	sendtime datetime, 
	delR int, 
	delS int, 
	isSend int
);

-- ---start-------Dv_MoneyLog-------start-------
create table Dv_MoneyLog (
	Log_ID int auto_increment primary key, 
	ToolsID int, 
	CountNum int, 
	Log_Money int, 
	Log_Ticket int, 
	AddUserName varchar(50), 
	AddUserID int, 
	Log_IP varchar(40), 
	Log_Time datetime, 
	Log_Type char(1), 
	BoardID int, 
	Conect text, 
	HMoney varchar(250)
);

-- ---start-------Dv_Online-------start-------
create table Dv_Online (
	id double, 
	username varchar(50), 
	userclass varchar(20), 
	stats varchar(250), 
	ip varchar(40), 
	actforip varchar(40), 
	startime datetime, 
	lastimebk datetime, 
	boardid int, 
	browser varchar(250), 
	usergroupid int, 
	actCome varchar(50), 
	userid int, 
	userhidden int
);

-- ---start-------Dv_Plus-------start-------
create table Dv_Plus (
	ID int auto_increment primary key, 
	Plus_Type varchar(100), 
	Plus_Name varchar(100), 
	Isuse char(1), 
	Plus_Setting text, 
	Mainpage varchar(100), 
	IsShowMenu char(1), 
	plus_Copyright varchar(200), 
	plus_adminpage varchar(100), 
	plus_id varchar(100)
);

-- ---start-------Dv_Plus_Tools_Buss-------start-------
create table Dv_Plus_Tools_Buss (
	ID int auto_increment primary key, 
	UserID int, 
	UserName varchar(50), 
	ToolsID int, 
	ToolsName varchar(50), 
	ToolsCount int, 
	SaleCount int, 
	UpdateTime datetime, 
	SaleMoney int, 
	SaleTicket int
);

-- ---start-------Dv_Plus_Tools_Info-------start-------
create table Dv_Plus_Tools_Info (
	ID int auto_increment primary key, 
	ToolsName varchar(50), 
	ToolsInfo varchar(255), 
	ToolsImg varchar(150), 
	IsStar int, 
	SysStock int, 
	UserStock int, 
	UserTicket int, 
	UserMoney int, 
	UserPost int, 
	UserWealth int, 
	UserEp int, 
	UserCp int, 
	UserGroupID varchar(255), 
	BoardID text, 
	BuyType char(1), 
	ToolsSetting text
);

-- ---start-------Dv_Plus_Tools_MagicFace-------start-------
create table Dv_Plus_Tools_MagicFace (
	ID int auto_increment primary key, 
	Title varchar(50), 
	MagicFace_s int, 
	MagicFace_l int, 
	MagicType int, 
	iMoney int, 
	iTicket int, 
	tMoney int, 
	tTicket int, 
	MagicSetting varchar(255)
);

-- ---start-------Dv_Setup-------start-------
create table Dv_Setup (
	id int auto_increment primary key, 
	Forum_Setting text, 
	Forum_ads text, 
	Forum_Badwords text, 
	Forum_rBadword text, 
	Forum_Maxonline int, 
	Forum_MaxonlineDate datetime, 
	Forum_TopicNum int, 
	Forum_PostNum int, 
	Forum_TodayNum int, 
	Forum_UserNum int, 
	Forum_YesTerdayNum int, 
	Forum_MaxPostNum int, 
	Forum_MaxPostDate datetime, 
	Forum_lastUser varchar(50), 
	Forum_LastPost varchar(255), 
	Forum_BirthUser text, 
	Forum_Sid int, 
	Forum_Version varchar(20), 
	Forum_NowUseBBS varchar(8), 
	Forum_IsInstall char(1), 
	Forum_challengePassWord varchar(50), 
	Forum_Ad text, 
	Forum_ChanName varchar(50), 
	Forum_ChanSetting varchar(250), 
	Forum_LockIP text, 
	Forum_Cookiespath varchar(50), 
	Forum_Boards text, 
	Forum_alltopnum varchar(250), 
	forum_pack varchar(250), 
	Forum_Cid char(1), 
	Forum_AvaSiteID varchar(50), 
	Forum_AvaSign varchar(50), 
	Forum_AdminFolder varchar(255), 
	Forum_BoardXML text, 
	Forum_Css text
);

-- ---start-------Dv_SmallPaper-------start-------
create table Dv_SmallPaper (
	S_ID int auto_increment primary key, 
	S_BoardID int, 
	S_UserName varchar(50), 
	S_Title varchar(100), 
	S_Content text, 
	S_Hits int, 
	S_Addtime datetime
);

-- ---start-------Dv_Style-------start-------
create table Dv_Style (
	ID int auto_increment primary key, 
	StyleName varchar(50), 
	Main_Style text, 
	Style_Pic text, 
	page_index text, 
	page_dispbbs text, 
	page_showerr text, 
	page_login text, 
	page_online text, 
	page_usermanager text, 
	page_fmanage text, 
	page_boardstat text, 
	page_paper_even_toplist text, 
	page_query text, 
	page_show text, 
	page_dispuser text, 
	page_help_permission text, 
	page_postjob text, 
	page_post text, 
	page_boardhelp text
);

-- ---start-------Dv_StyleHelp-------start-------
create table Dv_StyleHelp (
	id int auto_increment primary key, 
	StyleName varchar(50), 
	Main_Style text, 
	Style_Pic text, 
	page_index text, 
	page_dispbbs text, 
	page_showerr text, 
	page_login text, 
	page_online text, 
	page_usermanager text, 
	page_fmanage text, 
	page_boardstat text, 
	page_paper_even_toplist text, 
	page_query text, 
	page_show text, 
	page_dispuser text, 
	page_help_permission text, 
	page_postjob text, 
	page_post text, 
	page_boardhelp text
);

-- ---start-------Dv_TableList-------start-------
create table Dv_TableList (
	id int auto_increment primary key, 
	TableName varchar(8), 
	TableType varchar(50)
);

-- ---start-------Dv_Topic-------start-------
create table Dv_Topic (
	TopicID int auto_increment primary key, 
	Title varchar(250), 
	BoardID int, 
	PollID int, 
	LockTopic int, 
	Child int, 
	PostUserName varchar(50), 
	PostUserID int, 
	DateAndTime datetime, 
	hits int, 
	Expression varchar(100), 
	VoteTotal int, 
	LastPost varchar(250), 
	PostTable varchar(8), 
	istop char(1), 
	LastPostTime datetime, 
	isbest char(1), 
	isvote char(1), 
	SmsUserList text, 
	IsSmsTopic char(1), 
	LastSmsTime datetime, 
	TopicMode char(1), 
	Mode int, 
	GetMoney int, 
	UseTools varchar(255), 
	GetMoneyType char(1)
);

-- ---start-------Dv_Upfile-------start-------
create table Dv_Upfile (
	F_ID int auto_increment primary key, 
	F_AnnounceID varchar(50), 
	F_BoardID int, 
	F_UserID int, 
	F_Username varchar(50), 
	F_Filename varchar(250), 
	F_FileType varchar(10), 
	F_Type int, 
	F_FileSize int, 
	F_Readme varchar(250), 
	F_DownNum int, 
	F_ViewNum int, 
	F_DownUser text, 
	F_Flag int, 
	F_AddTime datetime, 
	F_Viewname varchar(255)
);

-- ---start-------Dv_User-------start-------
create table Dv_User (
	UserID int auto_increment primary key, 
	UserName varchar(50), 
	UserPassword varchar(20), 
	UserEmail varchar(255), 
	UserPost int, 
	UserTopic int, 
	UserSign varchar(255), 
	UserSex char(1), 
	UserFace varchar(255), 
	UserWidth int, 
	UserHeight int, 
	UserIM text, 
	JoinDate datetime, 
	LastLogin datetime, 
	UserLogins int, 
	UserViews int, 
	LockUser char(1), 
	UserClass varchar(20), 
	UserGroup varchar(50), 
	userWealth int, 
	userEP int, 
	userCP int, 
	UserPower int, 
	UserDel int, 
	UserIsBest int, 
	UserTitle varchar(50), 
	UserBirthday varchar(50), 
	UserQuesion varchar(50), 
	UserAnswer varchar(50), 
	UserLastIP varchar(30), 
	UserPhoto varchar(255), 
	UserFav varchar(255), 
	UserInfo text, 
	UserSetting varchar(255), 
	UserGroupID int, 
	TitlePic varchar(50), 
	UserHidden char(1), 
	UserMsg varchar(100), 
	IsChallenge char(1), 
	UserMobile varchar(20), 
	TruePassWord varchar(20), 
	UserToday varchar(250), 
	UserIsAva char(1), 
	UserAvaSetting text, 
	UserMoney int, 
	UserTicket int, 
	FollowMsgID text, 
	Vip_StarTime datetime, 
	Vip_EndTime datetime
);

-- ---start-------Dv_UserAccess-------start-------
create table Dv_UserAccess (
	uc_userid int, 
	uc_boardid int, 
	uc_Setting text
);

-- ---start-------Dv_UserGroups-------start-------
create table Dv_UserGroups (
	UserGroupID int auto_increment primary key, 
	title varchar(50), 
	usertitle varchar(50), 
	GroupSetting text, 
	Orders int, 
	GroupPic varchar(50), 
	ParentGID int, 
	IsSetting varchar(50), 
	TitlePic varchar(50), 
	MinArticle int
);

-- ---start-------Dv_Vote-------start-------
create table Dv_Vote (
	voteid int auto_increment primary key, 
	vote text, 
	votenum text, 
	votetype int, 
	LockVote int, 
	voters int, 
	TimeOut datetime, 
	UArticle int, 
	UWealth int, 
	UEP int, 
	UCP int, 
	UPower int
);

-- ---start-------Dv_VoteUser-------start-------
create table Dv_VoteUser (
	ID int auto_increment primary key, 
	VoteID int, 
	UserID int, 
	VoteDate datetime, 
	VoteOption varchar(50)
);

