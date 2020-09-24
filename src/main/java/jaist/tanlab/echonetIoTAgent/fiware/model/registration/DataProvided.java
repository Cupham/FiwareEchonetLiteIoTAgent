package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

import java.util.List;

public class DataProvided {
	public DataProvided() {
		
	}
	public DataProvided(List<RegistrationEntity> e) {
		this.setEntities(e);
	}
	public DataProvided(List<RegistrationEntity> e, List<String> a) {
		this.setEntities(e);
		this.setAttrs(a);
	}

	List<RegistrationEntity> entities;
	List<String> attrs;
	String expression;
	public List<RegistrationEntity> getEntities() {
		return entities;
	}
	public void setEntities(List<RegistrationEntity> entities) {
		this.entities = entities;
	}
	public List<String> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
