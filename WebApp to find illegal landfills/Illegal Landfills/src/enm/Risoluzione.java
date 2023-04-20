package enm;

public enum Risoluzione {

	BASSA(0),
	MEDIA(1),
	ALTA(2);
	
	private final int risoluzioneCode;

	private Risoluzione(int risoluzioneCode) {
		this.risoluzioneCode = risoluzioneCode;
	}
	public int getInt() {
		return this.risoluzioneCode;
	}

    public static Risoluzione fromInteger(int x) {
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
