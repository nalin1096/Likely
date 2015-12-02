package com.Likely;

public class MLR 
{
	private Matrix X;
	private Matrix Y;
	public MLR(Matrix X,Matrix Y)
	{
		this.X=X;
		this.Y=Y;
	}
	
	public Matrix calculateBetaValues()
	{
		Matrix Xtr = Matrix.transpose(X); //X'
		Matrix XXtr = Matrix.multiplyMatrices(Xtr,X); //X'X
		Matrix inverse_of_XXtr = Matrix.inverse(XXtr); //(X'X)^-1
		if (inverse_of_XXtr == null) {
			System.out.println("Matrix X'X does not have any inverse.Thus, MLR failed to create the model for these data.");
			return null;
		}
		Matrix XtrY = Matrix.multiplyMatrices(Xtr,Y); //X'Y
		return Matrix.multiplyMatrices(inverse_of_XXtr,XtrY);
	}
}

