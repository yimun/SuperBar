/**@wangyan*/
package com.xyworm.superbar.util;

/*
 * ����Ϊ��̬�������ľ�̬�����࣬�ṩ��̬����������
 */
public class RotateUtil
{
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��x����ת�任����
		{
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		   //ԭ��Ϊ��{0,-Math.sin(angle),Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��y����ת�任����
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}		
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��z����ת�任����
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	
	//��������ϵ->��������ϵ
	//���ԡ�����������У�ʹ�ø�����ת�ǣ����塪�����Ծ����У�ʹ��������ת��
	//���ڴ���������������ϵ�µ���ת�Ƕȣ���˽Ƕȱ为��Ϊ�����ֵ����֡�
	public static float[] getDirectionDotLeftHand_ObjectsToInertia(double[] values,double[] vector)
	{
		double yawAngle=Math.toRadians(values[0]);//��ȡYaw����ת�ǶȻ���
		double pitchAngle=Math.toRadians(values[1]);//��ȡPitch����ת�ǶȻ���
		double rollAngle=Math.toRadians(values[2]);//��ȡRoll����ת�ǶȻ���
		
		
		/*
		 * ���ָ��Ϸ�ʽ����ȵģ�
		 * �ǣ�
		 * ������ϵE�µ�x����ת������ת����ΪRx,
		 * ������ϵE�µ�y����ת�µ���ת����ΪRy,
		 * ������ϵE�µ�z����תr����ת����ΪRz,
		 * 
		 * ������ϵE�µ�z����תr����ת����ΪRr��Rr=Rz����
		 * �� ����ϵE����z����תr�����ϵE'�µ�y����ת�µ���ת����ΪRb��
		 * �� ����ϵE'����y����ת�º����ϵE''�µ�x����ת������ת����ΪRa��
		 * 
		 * 
		 */
		
		//roll��ָ�
		vector=RotateUtil.rollRotate(rollAngle,vector);
		//pitch��ָ�
		vector=RotateUtil.pitchRotate(pitchAngle,vector);	
		//yaw��ָ�
		vector=RotateUtil.yawRotate(yawAngle,vector);
		
		//Log.v("gVector", "x:"+(int)gVector[0]+" y:"+(int)gVector[1]+" z:"+(int)gVector[2]);
		double mapX=vector[0];
		double mapY=vector[1];	
		double mapZ=vector[2];
		float[] result={(float)mapX,(float)mapY,(float)mapZ};
		return result;
	}	
	
	public static float getHeadBeta(float[] values){
		float camerBeta=0;
		float point[]={0,0,0};
		double[] lookvector={0,0,-100,1};//�ֻ���ʼ����
		double angle[]={0,0,0};//float תdouble
		angle[0]=values[0];
		angle[1]=values[1];
		angle[2]=values[2];
		//��������������ϵ�Ľ�
		//��������ϵ->��������ϵ
		point=RotateUtil.getDirectionDotLeftHand_ObjectsToInertia(angle,lookvector);
		camerBeta=(float) Math.toDegrees( Math.asin(point[2]/100f));
		
		
		return camerBeta;
	}
	
	
}