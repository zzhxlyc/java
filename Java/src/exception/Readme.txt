重写或实现时不能扩大异常的范围，即不能抛出父类没有申明的异常
如果是多继承(接口)，则异常取所有父类方法异常的交集或不抛出异常

finally执行时间		
	在try和catch块中的return语句之后，函数返回之前
		所以finally中一般不能有return语句，否则出现警告，可以用@SuppressWarnings("finally")来取消警告