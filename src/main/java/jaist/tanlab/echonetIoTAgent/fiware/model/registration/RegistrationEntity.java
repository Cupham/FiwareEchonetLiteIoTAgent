package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

public class RegistrationEntity {
    
    String id;


    String idPattern;

   
    String  type;
    
    String typePattern;

    public RegistrationEntity() {
    }

    public RegistrationEntity(String id) {
        this.id = id;
    }
    public RegistrationEntity(String id, String type) {
        this.id = id;
        this.type = type;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPattern() {
		return idPattern;
	}

	public void setIdPattern(String idPattern) {
		this.idPattern = idPattern;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypePattern() {
		return typePattern;
	}

	public void setTypePattern(String typePattern) {
		this.typePattern = typePattern;
	}
    

}
