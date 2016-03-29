package base.primitive;

import java.lang.reflect.Field;

public class Box {
	
	public static void main(String[] args) {
		Box p = new Box();
//		p.reason();
//		p.problem2();
//		p.problem3();
//		p.对象和值的比较();
		p.不同数值对象的equals比较为false();
	}
	
/***************装箱机制中*******************/
	
	//说明=号赋值Integer, 调用Integer.valueOf(int i), 见reason()
	//-128到127之间，直接返回Integer.IntegerCache.cache中的256个Integer对象，并没有new所以==
	//Byte Short Integer Long 都一样，cache范围都是-128到127, Charactor的cache范围0到127
	public void problem1(){ 
		Integer a = 127;
		Integer b = 127;
		System.out.println(a == b);//true
	}
	
	public void problem2(){ //范围之外, 每次都new, 一般的Object比较
		Integer a = 200;
		Integer b = 200;
		System.out.println(a == b);//false
	}
	
/*********************************************/
	//没有装箱的情况下，就是对象，于是地址肯定不同
	public void problem3(){
		Integer a = new Integer(100);
		Integer b = new Integer(100);
		System.out.println(a == b);//false
	}
	
	public void reason(){
		Integer a = -128;
		int id = System.identityHashCode(a);
		for(Class<?> clazz : Integer.class.getDeclaredClasses()){
			//class java.lang.Integer$IntegerCache
			try {
				Field field = clazz.getDeclaredField("cache");
				field.setAccessible(true);
				Integer[] array = (Integer[])field.get(null);
				for(Integer i : array){
					if(id == System.identityHashCode(i)){
						System.out.println(i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//对象和值比较会触发拆箱机制, 即调用Integer.intValue()返回int值
	public void 对象和值的比较(){
		System.out.println(new Integer(1) == 1);	//true
		//先右边触发拆箱机制, 计算结果为int类型的2, 然后和左边Integer(2)比较, 再触发拆箱（而不是右边装箱）
		System.out.println(new Integer(2) == new Integer(1) + new Integer(1));	//true
		System.out.println(new Long(2L) == new Integer(1) + new Integer(1));	//true
	}
	
	//Long的equals(Object obj)先判断obj是否是Long, 不是直接返回false, 再通过longValue()判断值
	public void 不同数值对象的equals比较为false(){
		Long a = 1L;
		System.out.println(a.equals(1));	//装箱成Integer, 不同类型直接返回false
		System.out.println(a.equals(1L));	//装箱成Long, 相同类型相同数值返回true
	}
}
