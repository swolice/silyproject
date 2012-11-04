select b.type_name, b.type_id, a.*
  from PK_ACCOUNT_INFO a, PK_ACCOUT_TYPE b
 where nvl(b.pro1, 0) != 1
   and a.type_id = b.type_id
   and b.game_id = 22
 order by a.type_id, a.info_no
 
 
 select * from pk_sell_goods
