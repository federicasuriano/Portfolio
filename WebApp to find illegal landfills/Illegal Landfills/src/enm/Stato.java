package enm;

public enum Stato {
    CREATA(0),
	APERTA(1),
	CHIUSA(2);
	

	private final int statoCode;

	private Stato(int statoCode) {
		this.statoCode = statoCode;
	}
	public int getInt() {
		return this.statoCode;
	}

    public static Stato fromInteger(int x) {
        switch(x) {
        case 0:
            return CREATA;
        case 1:
            return APERTA;
            
        case 2:
        	return CHIUSA;
        }
        return null;
    }
    
    public static Stato fromString(String x) {
     
    	switch(x) {
        case "CREATA":
            return CREATA;
        case "APERTA":
            return APERTA;
            
        case "CHIUSA":
        	return CHIUSA;
       
        
    case "creata":
        return CREATA;
    case "aperta":
        return APERTA;
        
    case "chiusa":
    	return CHIUSA;
        }
        return null;
    }

}
