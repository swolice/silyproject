public static float getFloatValue(double d1,double d2){
        
	BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
	BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
	
	return bd1.add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).floatValue();
}

BigDecimal使用String 来构造才是精确的， 如果使用浮点型参数，获取的结果是不精确的