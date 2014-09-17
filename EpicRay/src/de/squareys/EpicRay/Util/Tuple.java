package de.squareys.epicray.util;

public class Tuple<A, B> {

	private A m_a;
	private B m_b;

	public Tuple(A a, B b) {
		m_a = a;
		m_b = b;
	}

	public A getA() {
		return m_a;
	}

	public B getB() {
		return m_b;
	}

	public void setA(A a) {
		m_a = a;
	}

	public void setB(B b) {
		m_b = b;
	}
}
