package projet_grp8;

public class Histogram {
	private int[] values;
	private int   total;
	private int   oldMedian = 0;
	private int   countMedian = 0; //permet de savoir combien d'élément sont inférieur à oldMedian 
	private int   max = 0;
	private int   min =0;
	
	public Histogram() {
		values = new int[256];
	}
	
	public int getValue(int pos) {
		return values[pos];
	}
	public void addValue(int pos, int value) {
		total+=value;
		values[pos]+=value;
		if(values[pos]<min) min=values[pos];
		if(values[pos]>max) max=values[pos];
		
		if(pos<oldMedian)
			countMedian+=value;
	}
	public void incrementeValue(int pos) {
		total++;
		values[pos]++;
		if(values[pos]>max) max=values[pos];	
		
		
		if(pos<oldMedian)
			countMedian++;
	}
	public void decrementeValue(int pos) {
		total--;
		values[pos]--;
		if(values[pos]<min) min=values[pos];
		if(pos<oldMedian)
			countMedian--;
	}
	public int getTotal() {
		return total;
	}
	public int getMax() {
		return max;
	}
	public int getMin() {
		return min;
	}
	public int getMedian() {
		/*int count = 0;
		for(int i=0;i<256;i++) {
			count+=values[i];
			if(count>total/2) {
				return i;
			}
		}*/
		if(countMedian<total/2) {
			for(int i=oldMedian; i<256;i++) {
				countMedian+=values[i];
				if(countMedian>total/2) {
					oldMedian = i;
					countMedian-=values[i]; //on retire
					return i;
				}
			}
		}
		//sinon
		for(int i=oldMedian-1; i>=0;i--) {
			countMedian-=values[i];
			if(countMedian<total/2) {
				oldMedian = i;
				return i;
			}
		}
		
		return 0;
	}
}