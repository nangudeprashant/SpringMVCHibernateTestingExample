package com.javaLive.springmvc.restClient;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
 
import org.springframework.web.client.RestTemplate;
 
import com.javaLive.springmvc.model.Employee;;
public class SpringRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/SpringMVCHibernateTestingExample";
	/* GET */
    @SuppressWarnings("unchecked")
    private static void listAllEmployees(){
        System.out.println("Testing listAllUsers API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> employeeMap  = restTemplate.getForObject(REST_SERVICE_URI+"/employeeList/", List.class);
         
        if(employeeMap!=null){
        	for(LinkedHashMap<String, Object> employee : employeeMap){
                System.out.println("Employee : id="+employee.toString());;
            }
        }else{
            System.out.println("No employee exist----------");
        }
    }
    /* GET */
    private static void getUser(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        Employee user = restTemplate.getForObject(REST_SERVICE_URI+"/employee/2", Employee.class);
        System.out.println(user);
    }
    public static void main(String args[]){
    	//listAllEmployees();
    	getUser();
    }
}
