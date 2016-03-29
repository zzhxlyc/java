package base.primitive;

public class DoubleValue {
	
	//E, P模式值为double （8进制无法表示在double中）
	// 1.024 * 10^3, E模式必须为10进制（前导0被忽略不属于8进制）
	public static double v1 = 1.024E+3;
	public static double v11 = 0777E1;	//十进制：7770
	// 1.0 * 2^10, P模式必须为16进制
	public static double v2 = 0x1.0P+10;
	//v1 == v2
	
	public static final double POSITIVE_INFINITY = 1.0 / 0.0;	//Infinity
	public static final double NEGATIVE_INFINITY = -1.0 / 0.0;	//-Infinity
	//NaN != NaN true    NaN == NaN false
	public static final double NaN = 0.0 / 0.0;					//NaN, 除整数0才回抛异常
	
	public static void main(String[] args) {
		System.out.println(NaN == NaN);	//false
	}
	

}
