package com.cf.basketball.stock;

/**
 * �����ƶ�ƽ�� ������ߵ�ʱ��ʹ��
 */
public class SMMACurve {
	private final static int MAX_DATA_COUNT = 241; // ���������

	private int Period; // �ƶ�����

	public int Count; // ��ݸ���

	public double Price[] = new double[MAX_DATA_COUNT]; // �۸�

	public double Volume[] = new double[MAX_DATA_COUNT];// �ɽ���

	public double Amount[] = new double[MAX_DATA_COUNT]; // �ƶ�ƽ����

	public double SMMA[] = new double[MAX_DATA_COUNT]; // �ƶ�ƽ����

	public SMMACurve() {
	}

	public SMMACurve(int nPeriod, int dataCount) {
		Period = nPeriod;

		Price = new double[dataCount]; // �۸�

		Volume = new double[dataCount];// �ɽ���

		SMMA = new double[dataCount]; // �ƶ�ƽ����

		Amount = new double[dataCount];

		Count = dataCount;

	}

	public boolean ccMathMA(boolean isMline) {

		double Sum = 0;
		if (isMline) {
			for (int i = 0; i < Count; i++) {
				SMMA[i] = (Price[i] / Volume[i]);
			}
		} else {
			if (Count < Period) {
				SMMA = null;
				return true;
			}
			for (int i = 0; i < ((Period < Count) ? Period : Count); i++) {
				Sum += Price[i];
				SMMA[i] = (float) (Sum / (i + 1));
			}

			for (int i = Period; i < Count; i++) {
				Sum += Price[i] - Price[i - Period];
				SMMA[i] = (float) (Sum / Period);
			}
		}
		return true;
	}
}