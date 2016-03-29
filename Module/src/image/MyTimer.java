package image;

public class MyTimer {
	private long begin;
	private long end;
	
	public MyTimer() {
		begin = 0;
		end = 0;
	}
	
	public void start(){
		begin = System.currentTimeMillis();
	}
	
	public void end(){
		end = System.currentTimeMillis(); 
	}
	
	public long getTime(){
		return end - begin;
	}
	
	public String getTimeString(){
		long time = end - begin;
		int millSecond = (int)(time - time / 1000 * 1000);
		int t = (int)(time / 1000);
		int hour = t / (3600 );
		int minute = (t - hour * 3600) / 60;
		int second = t - hour * 3600- minute * 60; 
		return hour + " hour " + minute + " minute " + second + " second " 
				+ millSecond + " millSecond ";
	}
}
