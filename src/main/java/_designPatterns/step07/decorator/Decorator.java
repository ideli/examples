package _designPatterns.step07.decorator;

public abstract class Decorator extends Component {

	protected Component component;
	
	public void setComponent(Component component) {
		this.component = component;
	}
	
	@Override
	public void request() {
		if(this.component != null) {
			this.component.request();
		}
	}
}
