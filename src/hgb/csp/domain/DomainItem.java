package hgb.csp.domain;

public class DomainItem {
	private boolean available;
	private Object value;
	
	public DomainItem(Object value) {
		this.value = value;
		available = true;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getValue() {
		return value;
	}
}
