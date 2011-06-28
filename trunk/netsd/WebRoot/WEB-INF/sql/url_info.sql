
/*drop table url_info*/

CREATE TABLE `url_info` (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `url_href` varchar(254) DEFAULT NULL,
  `up_url_href` varchar(254) DEFAULT NULL,
  `url_type` char(1) DEFAULT NULL,
  `vflag` char(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `html_title` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`row_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk

create index url_href_index on url_info(url_href)
create index up_url_href_index on url_info(up_url_href)


select * from url_info where url_type = 2

