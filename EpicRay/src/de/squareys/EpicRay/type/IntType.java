package de.squareys.epicray.type;

public class IntType implements IType<Integer> {

	private int m_val;
	
	public IntType () {
		m_val = 0;
	}

	public IntType (int val) {
		m_val = val;
	}
	
	@Override
	public Integer[] createArray(int size) {
		return new Integer[size];
	}

	@Override
	public Integer get() {
		return m_val;
	}

	@Override
	public void set(Integer value) {
		m_val = value;
	}

}
