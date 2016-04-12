package _basic.innerclass;

public class ClientEvent {

	public ClientEvent(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	private Client client;
}
