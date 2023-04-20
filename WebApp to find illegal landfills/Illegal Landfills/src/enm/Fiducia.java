package enm;

public enum Fiducia {

	BASSA(0),
	MEDIA(1),
	ALTA(2);
	
	private final int fiduciaCode;

	private Fiducia(int fiduciaCode) {
		this.fiduciaCode = fiduciaCode;
	}
	public int getInt() {
		return this.fiduciaCode;
	}

    public static Fiducia fromInteger(int x) {
        switch(x) {
        case 0:
            return BASSA;
        case 1:
            return MEDIA;
            
        case 2:
        	return ALTA;
        }
        return null;
    }
}
