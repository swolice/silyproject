public static float getFloatValue(double d1,double d2){
        
	BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
	BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
	
	return bd1.add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).add(bd2).floatValue();
}

BigDecimalʹ��String ��������Ǿ�ȷ�ģ� ���ʹ�ø����Ͳ�������ȡ�Ľ���ǲ���ȷ��