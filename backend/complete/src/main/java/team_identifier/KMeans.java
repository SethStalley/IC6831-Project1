package team_identifier;

public class KMeans {
	//Number of clusters, we default to 2 because there are two soccer teams.
	private int NUM_CLUSTERS = 2;
	
	public KMeans(int numClusters) {
		this.NUM_CLUSTERS = numClusters;
	}
	
	
    /**
     * // Calculate Bhattacharyya distance.
     * @param X:Closely grouped Data
     * @param U: Means Sample Data
     * @return - double value.
     * @throws Exception 
     */
    private static double distance(HSV X[], HSV U[]) throws Exception {
    	double sum = 0.0;
    	
    	if(X.length != U.length) {
    		throw new Exception("Muestras no son del mismo largo!");
    	}
    	
    	for(int i = 0; i< X.length; i++) {
    		sum += Math.sqrt(Math.abs(X[i].value - U[i].value));
    	}
    	
        return sum;
    }
    
    public class HSV {
    	double value;
    }
    
}
