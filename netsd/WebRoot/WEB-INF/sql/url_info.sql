
/*drop table url_info*/

create table url_info
(
   row_id int not null primary key,
   url_href varchar(254),
   up_url_href varchar(254),
   url_type char(1),
   vflag char(1),
   html_title varchar(254),
   image_url varchar(254),
   create_time
)

create index url_href_index on url_info(url_href)
create index up_url_href_index on url_info(up_url_href)


select * from url_info where url_type = 2
