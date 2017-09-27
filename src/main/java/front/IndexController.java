package front;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class IndexController {

	@Value("${service.auth.url}")
	private String serviceUrl;
	
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String name, 
    		@RequestParam(required = true) String pass) {
    	
    	RestTemplate restTemplate = new RestTemplate();
    	MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
    	
    	parametersMap.add("name", name); 
    	parametersMap.add("pass", pass);
    	try {
    		ResponseEntity<String> httpResponse = restTemplate.postForEntity(serviceUrl, parametersMap, String.class);
    	} catch (RestClientException restException) {
    		return "redirect:/notallowed";
    	}
    	
        return "redirect:/allowed";
    }
    
    @RequestMapping("/allowed")
    public String allowed() {
        return "allowed";
    }
    
    @RequestMapping("/notallowed")
    public String notallowed() {
        return "notallowed";
    }

}