create  table url_info(
	row_id int auto_increment not null primary key,
	url_href varchar(254),
	up_url_href varchar(254),
	url_type char(1),
	vflag char(1),
	create_time timestamp default now()
)