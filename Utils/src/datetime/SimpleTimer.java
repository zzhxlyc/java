package datetime;

public class SimpleTimer {
	private long begin;
	private long end;
	
	public SimpleTimer() {
		begin = 0;
		end = 0;
	}

	public SimpleTimer(long begin) {
		this.begin = begin;
		end = 0;
	}
	
	public SimpleTimer(long begin, long end) {
		this.begin = begin;
		this.end = end;
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
		int hour = t / 3600;
		int minute = (t - hour * 3600) / 60;
		int second = t - hour * 3600- minute * 60;
		StringBuilder sb = new StringBuilder();
		if(hour > 0){
			sb.append(hour).append(" hour ");
		}
		if(minute > 0){
			sb.append(minute).append(" min ");
		}
		if(second > 0){
			sb.append(second).append(" sec ");
		}
		sb.append(millSecond).append(" ms");
		return sb.toString();
	}
}
