
public interface Algorithm {
	public int[][] solve();

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
	
	default int[][] createAdjMatrix(int[][] matrix){
		int [] mazeArray = toArray(matrix);
		int max = mazeArray.length;
		int X_size = (int) Math.sqrt(max);
		int[][] adjMatrix = new int[max][max];
		int k=0;
		for(int i=0;i<mazeArray.length;i++) {			
			if(i<max-X_size){
				if(mazeArray[i+X_size]==0 || mazeArray[i+X_size]==2 || mazeArray[i+X_size]==3){
					adjMatrix[i][i+X_size]=1;
				}
			}
			if(i>=X_size){
				if(mazeArray[i-X_size]==0 || mazeArray[i-X_size]==2 || mazeArray[i-X_size]==3){
					adjMatrix[i][i-X_size]=1;
				}	
			}
			if(i!=0 && k !=0){
				if(mazeArray[i-1]==0 || mazeArray[i-1]==2 || mazeArray[i-1]==3){
					adjMatrix[i][i-1]=1;
				}
			}
			if(i!=(mazeArray.length-1) && k!=(X_size-1)){
				if(mazeArray[i+1]==0 || mazeArray[i+1]==2 || mazeArray[i+1]==3){
					adjMatrix[i][i+1]=1;
				}
			}
			k++;
			if(k>=X_size){
				k=0;
			}
		}
		return adjMatrix;
	}
	
	default int[][] toMatrix(int[] array){
		int max = (int) Math.sqrt(array.length);
		int[][] matrix = new int[max][max];
		for(int x=0;x<array.length;x++){
			int k = array[x];
			int count = 0;
			for(int i=0;i<max;i++){
				for(int j=0;j<max;j++){
					if(k==-1){
						return matrix;
					}
					if(count == k){
						matrix[i][j]=5;
					}
				count++;
				}
			}
		}
		return matrix;
	}
	
	default int getStartPos(int[][] matrix) {
		int [] mazeArray = toArray(matrix);
		for(int i=0;i<mazeArray.length;i++){
			if(mazeArray[i]==2){
				return i;
			}
		}
		return 0;
	}
	
	default int getStopPos(int[][] matrix) {
		int [] mazeArray = toArray(matrix);
		for(int i=0;i<mazeArray.length;i++){
			if(mazeArray[i]==3){
				return i;
			}
		}
		return 0;
	}

	public void printMaze();
}
