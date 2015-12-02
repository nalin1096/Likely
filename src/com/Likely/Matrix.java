package com.Likely;

public class Matrix 
{
	private double [][]  Data;
	private int numOfRows;
	private int numOfCols;
	
	public Matrix(double [] param1,double [] param2)
	{
		this.Data[0]=param1;
		this.Data[1]=param2;
		numOfRows = Data.length;
		numOfCols = Data[0].length;
	}
	
	public Matrix(double [] knownValues)
	{
		Data = new double[knownValues.length][1];
		this.Data[0]=knownValues;
		numOfRows = knownValues.length;
		numOfCols = 1;
	}
	
	public Matrix(double[][] Data) {
        this.Data = Data;
        numOfRows = Data.length;
        numOfCols = Data[0].length;
    }

    public Matrix(int nrow, int ncol) {
        numOfRows = nrow;
        numOfCols = ncol;
        Data = new double[numOfRows][numOfCols];
    }
    
    public static Matrix multiplyMatrices(Matrix matrix1,Matrix matrix2)
    {
    	Matrix resultMatrix;
    	
    	int mA = matrix1.Data.length;
        int nA = matrix1.Data[0].length;
        int mB = matrix2.Data.length;
        int nB = matrix2.Data[0].length;
        
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] C = new double[mA][nB];
        for (int i = 0; i < mA; i++)
        {
        	for (int j = 0; j < nB; j++)
        	{    
        		for (int k = 0; k < nA; k++)
        		{
        			C[i][j] += matrix1.Data[i][k] * matrix2.Data[k][j];
        		}
        	}
        }
        resultMatrix = new Matrix(C);
    	return resultMatrix; 
    }
    
    public static Matrix multiplyByScalar(Matrix matrix,double x)
    {
    	Matrix result = new Matrix(matrix.numOfCols,matrix.numOfRows);;
    	for (int i = 0; i < result.numOfRows ; i++)
            for (int j = 0; j < result.numOfCols; j++)
                result.Data[i][j]=matrix.Data[i][j]*x;
		return result;
    }
    
    public static Matrix transpose(Matrix matrix)
    {
    	Matrix resultMatrix = new Matrix(matrix.numOfCols,matrix.numOfRows);
    	for (int i=0;i<matrix.numOfRows;i++) {
            for (int j=0;j<matrix.numOfCols;j++) {
                resultMatrix.Data[j][i] = matrix.Data[i][j];
            }
        }
    	return resultMatrix;
    }
    
    public static double determinant(Matrix matrix)
    {
    	 double sum=0; 
    	 int temp;
    	 if(matrix.Data.length==1)
    	 {  
    	      return(matrix.Data[0][0]);
    	 }
    	 for(int i=0;i<matrix.Data.length;i++)
    	 {
    		 Matrix smaller= new Matrix(matrix.Data.length-1,matrix.Data.length-1); //creates smaller matrix- values not in same row, column
    	     for(int a=1;a<matrix.Data.length;a++)
    	     {
    	        for(int b=0;b<matrix.Data.length;b++)
    	        {
    	        	if(b<i)
    	        	{
    	        		smaller.Data[a-1][b]=matrix.Data[a][b];
    	        	}
    	        	else if(b>i)
    	        	{
    	            smaller.Data[a-1][b-1]=matrix.Data[a][b];
    	        	}
    	        }
    	     }
    	     if(i%2==0)
    	     {
    	    	 temp=1;
    	     }
    	     else
    	     {
    	        temp=-1;
    	     }
    	     sum+=temp*matrix.Data[0][i]*(determinant(smaller)); 
    	 }
    	 return(sum);
    }
    
    public static Matrix createSubMatrix(Matrix matrix, int exclRow, int exclCol) 
    {
        Matrix result = new Matrix(matrix.numOfRows-1, matrix.numOfCols-1);
        int r = -1;
        for (int i=0;i<matrix.numOfRows;i++) 
        {
            if (i==exclRow)
            {
            	continue;
            }
            r++;
            int c = -1;
            for (int j=0;j<matrix.numOfCols;j++) 
            {
                if (j==exclCol)
                {
                	continue;
                }
                result.Data[r][++c] = matrix.Data[i][j];
            }
        }
        return result;
    } 
    
    public static Matrix cofactor(Matrix matrix)
    {
    	Matrix result = new Matrix(matrix.numOfRows,matrix.numOfCols);
        for (int i=0;i<matrix.numOfRows;i++) 
        {
            for (int j=0; j<matrix.numOfCols;j++) 
            {
            	double val = (int)(Math.pow(-1, i + j))*Matrix.determinant(createSubMatrix(matrix, i, j));
            	result.Data[i][j] = val;
            }
        }
        
        return result;
    }
    
    public static Matrix inverse(Matrix matrix)
    {
        Matrix cofactorMatrix = Matrix.cofactor(matrix);
        Matrix AdjacencyMatrix = Matrix.transpose(cofactorMatrix);
        Matrix ResultMatrix = Matrix.multiplyByScalar(AdjacencyMatrix, Matrix.determinant(matrix));
    	return ResultMatrix;
    }
    
    public double[][] getData() {
		return Data;
	}

	public void setData(double[][] data) {
		Data = data;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public int getNumOfCols() {
		return numOfCols;
	}

	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}
  
}

