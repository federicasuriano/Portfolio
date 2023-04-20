package enm;

public enum LivelloLavoratore {

	BASSO(0),
	MEDIO(1),
	ALTO(2);
	
	private final int livelloCode;

	private LivelloLavoratore(int livelloCode) {
		this.livelloCode = livelloCode;
	}
	public int getInt() {
		return this.livelloCode;
	}
	
    public static LivelloLavoratore fromInteger(int x) {
        switch(x) {
        case 0:
            return BASSO;
        case 1:
            return MEDIO;
            
        case 2:
        	return ALTO;
        }
        return null;
    }

}
