package de.squareys.EpicRay.type;

public class DoubleType implements IType<Double> {
	private double m_val;
	
	public DoubleType() {
		m_val = 0.0;
	}
	
	public DoubleType(double d) {
		m_val = d;
	}
	
	@Override
	public Double[] createArray(int size) {
		return new Double[size];
	}

	@Override
	public Double get() {
		return m_val;
	}

	@Override
	public void set(Double value) {
		m_val = value;
	}

}
