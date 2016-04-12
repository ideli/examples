package _basic.innerclass;

import java.util.ArrayList;
import java.util.List;

public class ClientQueue {

	private List<Client> clients = new ArrayList<Client>();
	private List<ClientEventListener> listeners = new ArrayList<ClientEventListener>();
	
	public ClientQueue(){
		
	}
	
	public void registerListener(ClientEventListener listener) {
		listeners.add(listener);
	}
	
	public void addClient(Client client) {
		clients.add(client);
		for(int i = 0; i < listeners.size(); i++) {
			listeners.get(i).addClient(new ClientEvent(client));
		}
	}
	
	public void removeClient(Client client) {
		clients.remove(client);
		for(int i = 0; i < listeners.size(); i++) {
			listeners.get(i).removeClient(new ClientEvent(client));
		}
	}
}
