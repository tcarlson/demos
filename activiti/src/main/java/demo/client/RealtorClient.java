package demo.client;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class RealtorClient 
{
	ProcessEngine processEngine;	
	RuntimeService runtimeService;
	TaskService taskService;

	public static void main(String[] args) throws Exception 
	{
		RealtorClient client = new RealtorClient();
		client.homePurchase();
	}
	
	public RealtorClient() throws Exception 
	{
		processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
				  .setJdbcDriver("com.mysql.jdbc.Driver")
				  .setJdbcUrl("jdbc:mysql://localhost:3306/demo")
				  .setJdbcUsername("demo")
				  .setJdbcPassword("demo")
				  .buildProcessEngine();
		
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
	}
	
	public void homePurchase() throws Exception
	{
	    System.out.println("Starting process");
	    Map vars = new HashMap();
	    vars.put("address", "1001 Main St.");
	    ProcessInstance instance = runtimeService.startProcessInstanceByKey("homePurchase", vars);

	    listTasks(instance.getId());

	    Thread.sleep(5000);
	    
	    // Buyer: Submit Personal Documentation
	    Task task = taskService.createTaskQuery()
	    				.processInstanceId(instance.getId())
	    				.taskAssignee("buyer")
	    				.singleResult();
	    vars = new HashMap();
	    vars.put("name", "joe");
	    vars.put("ssn", "123456");
	    taskService.complete(task.getId(), vars);
	    System.out.println("buyer: tasks complete");

	    Thread.sleep(5000);

	    listTasks(instance.getId());

		// Seller: Prepare Offer
	    task = taskService.createTaskQuery()
				.processInstanceId(instance.getId())
				.taskAssignee("realtor")
				.singleResult();
		vars = new HashMap();
		vars.put("price", 250000);
		taskService.complete(task.getId(), vars);
	    System.out.println("realtor: tasks complete");
		
		Thread.sleep(5000);
		
		listTasks(instance.getId());
	    
	    System.out.println("...etc...");
	}
	
	private void listTasks(String processId)
	{
	    List<Task> tasks = taskService.createTaskQuery()
				    		.processInstanceId(processId)
				    		.list();
	    System.out.println("\n-----------------\nTASKS PENDING");
	    for (Task task : tasks)
	    {
		    System.out.println(String.format("%s : %s", task.getAssignee(), task.getName()));	    	
	    }
	    System.out.println("-----------------");
	}
}
