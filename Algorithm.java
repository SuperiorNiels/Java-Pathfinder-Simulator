
public interface Algorithm {
	public int[][] solve(Boolean diagonal);

	default int[] toArray(int[][] matrix) {
		int[] res = new int[matrix[0].length*matrix.length];
		int k=0;
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				res[k]=matrix[i][j];
				k++;
			}
		}
		return res;
	}
	
	default int[][] createAdjMatrix(int[][] matrix, Boolean diagonal){
		int [] mazeArray = toArray(matrix);
		int max = mazeArray.length;
		int X_size = matrix.length;
		int Y_size = matrix[0].length;
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
			if(diagonal==true){
				if(k!=(X_size-1) && i<max-X_size){
					if(mazeArray[i+X_size+1]==0 || mazeArray[i+X_size+1]==2 || mazeArray[i+X_size+1]==3){
						adjMatrix[i][i+X_size+1]=1;
					}
				}
				if(k!=0 && i<max-X_size){
					if(mazeArray[i+X_size-1]==0 || mazeArray[i+X_size-1]==2 || mazeArray[i+X_size-1]==3){
						adjMatrix[i][i+X_size-1]=1;
					}
				}
				if(k!=(X_size-1) && i>=X_size){
					if(mazeArray[i-X_size+1]==0 || mazeArray[i-X_size+1]==2 || mazeArray[i-X_size+1]==3){
						adjMatrix[i][i-X_size+1]=1;
					}
				}
				if(k!=0 && i>=X_size){
					if(mazeArray[i-X_size-1]==0 || mazeArray[i-X_size-1]==2 || mazeArray[i-X_size-1]==3){
						adjMatrix[i][i-X_size-1]=1;
					}
				}
			}
			k++;
			if(k>=X_size){
				k=0;
			}
		}
		return adjMatrix;
	}
	
	default int[][] toMatrix(int[] array,int type, int X){
		int max_X= X;
		int max_Y= array.length/X;
		int[][] matrix = new int[max_X][max_Y];
		for(int x=0;x<array.length;x++){
			int k = array[x];
			int count = 0;
			for(int i=0;i<max_X;i++){
				for(int j=0;j<max_Y;j++){
					if(k==-1){
						return matrix;
					}
					if(count == k){
						matrix[i][j]=type;
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
	
	public void dispose();

}
