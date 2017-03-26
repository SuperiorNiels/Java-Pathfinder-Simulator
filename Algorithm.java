
public interface Algorithm {
	public char[][] solve();
	
	default int[] toArray(int[][] matrix) {
		int[] res = new int[matrix[0].length*matrix[1].length];
		int k=0;
		for(int i=0;i<matrix[0].length;i++){
			for(int j=0;j<matrix[1].length;j++){
				res[k]=matrix[i][j];
				k++;
			}
		}
		return res;
	}
	
	default int getStartX(int [][] matrix) {
		for(int i=0;i<matrix[0].length;i++){
			for(int j=0;j<matrix[1].length;j++){
				if(matrix[i][j]==2){
					return i;
				}
			}
		}
		return 0;
	}
	
	default int getStartY(int [][] matrix) {
		for(int i=0;i<matrix[0].length;i++){
			for(int j=0;j<matrix[1].length;j++){
				if(matrix[i][j]==2){
					return j;
				}
			}
		}
		return 0;
	}
	
	default int getStopX(int [][] matrix) {
		for(int i=0;i<matrix[0].length;i++){
			for(int j=0;j<matrix[1].length;j++){
				if(matrix[i][j]==2){
					return i;
				}
			}
		}
		return 0;
	}
	
	default int getStopY(int [][] matrix) {
		for(int i=0;i<matrix[0].length;i++){
			for(int j=0;j<matrix[1].length;j++){
				if(matrix[i][j]==2){
					return j;
				}
			}
		}
		return 0;
	}
}
