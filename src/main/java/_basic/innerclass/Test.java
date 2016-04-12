package _basic.innerclass;

public class Test {

	public void demo() {
		
		ClientMonitor monitor = new ClientMonitor();
		ClientQueue queue = new ClientQueue();
		queue.registerListener(monitor);
		queue.registerListener(new ClientEventListener(){

			@Override
			public void addClient(ClientEvent e) {
				System.out.println("InnerClass: " + e.getClient().getName() + " added");
				
			}

			@Override
			public void removeClient(ClientEvent e) {
				System.out.println("InnerClass: " + e.getClient().getName() + " removed");
			}
			
		});
		queue.addClient(new Client("client111", "127.0.0.1"));
	}
}

class ClientMonitor implements ClientEventListener {

	@Override
	public void addClient(ClientEvent e) {
		System.out.println("ClientMonitor: " + e.getClient().getName() + " added");
	}

	@Override
	public void removeClient(ClientEvent e) {
		System.out.println("ClientMonitor: " + e.getClient().getName() + " removed");
	}
	
}
