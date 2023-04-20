
public enum LivelloDiTraffico {
	L0(0),
	L1(1),
	L2(2),
	L3(3),
	L4(4),
	L5(5);


	private final int levelCode;

	private LivelloDiTraffico(int levelCode) {
		this.levelCode = levelCode;
	}
	public int getInt() {
		return this.levelCode;
	}
}