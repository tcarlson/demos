package demo.behavior;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;


public class UpdateMLS implements ActivityBehavior
{
	@Override
	public void execute(ActivityExecution execution) throws Exception 
	{
		String address = (String) execution.getVariable("address");
		Long price = (Long) execution.getVariable("price");

		System.out.println(String.format("Updating listing service for the following property under purchase contract:\nAddress = %s\nPurchase Price = $%d", address, price));
		
		// Call a service to update the listing...
		Thread.sleep(3000);
		System.out.println("Updated");
	}
}
