  select pbg.*,select pbg.*,ROUND(TO_NUMBER(create_date - sysdate  + validity) * 24 * 60) invilidate from Pk_Buy_Goods pbg where 1=1  and pbg.trad_Goods_Type = ?  and goods_num > 0  and (pbg.create_Date + pbg.validity) > ?  order by  pbg.create_Date desc 
         ROUND(TO_NUMBER(create_date - sysdate + validity) * 24 * 60) invilidate
    from Pk_Buy_Goods pbg for update 
 where 1 = 1
   and goods_num > 0
   and (pbg.create_Date + pbg.validity) > ?select pbg.*,ROUND(TO_NUMBER(create_date - sysdate  + validity) * 24 * 60) invilidate from Pk_Buy_Goods pbg where 1=1  and pbg.trad_Goods_Type = ?  and goods_num > 0  and (pbg.create_Date + pbg.validity) > ?  order by  pbg.create_Date desc select pbg.*,ROUND(TO_NUMBER(create_date - sysdate  + validity) * 24 * 60) invilidate from Pk_Buy_Goods pbg where 1=1  and pbg.trad_Goods_Type = ?  and goods_num > 0  and (pbg.create_Date + pbg.validity) > ?  order by  pbg.create_Date desc 
