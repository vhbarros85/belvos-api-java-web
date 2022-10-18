package com.vhbarros.belvo.FinaceAPIIntegration;

import com.vhbarros.belvo.FinaceAPIIntegration.backend.model.Token;
import com.vhbarros.belvo.FinaceAPIIntegration.backend.model.connect.BelvoAPIClient;
import com.vhbarros.belvo.FinaceAPIIntegration.model.Link;
import com.vhbarros.belvo.FinaceAPIIntegration.service.UserService;
import com.vhbarros.belvo.client.api.AccountsApi;
import com.vhbarros.belvo.FinaceAPIIntegration.model.User;
import com.vhbarros.belvo.client.api.InstitutionsApi;
import com.vhbarros.belvo.client.model.AccountFilters;
import com.vhbarros.belvo.client.model.InstitutionFilters;
import com.vhbarros.belvo.client.model.InstitutionsPaginatedResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.vhbarros.belvo.client.model.AccountsPaginatedResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class FinaceApiIntegrationApplication  {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(FinaceApiIntegrationApplication.class, args);
		UserService userService = applicationContext.getBean(UserService.class);
		/*try {
			Token token = new BelvoAPIClient().getAccessToken();
			token.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/
		//User user = userService.findUserByEmail("vhbarros@gmail.com");
		//List<Link> links = userService.findByUserId(user.getId());
		InstitutionsApi institutionsApi = userService.institutionsApi();
		InstitutionFilters filter = new InstitutionFilters();
		filter.setCountryCodeIn("br");
		filter.setName("erebor_br_retail");
		institutionsApi.listInstitutionsWithHttpInfo(1,100,filter);
		/*AccountsApi accountsApi = userService.accountApi();

		for(Link link : links){
			AccountFilters filters = new AccountFilters();
			filters.setLink(link.getLink());
			AccountsPaginatedResponse response = accountsApi.listAccounts(1,10,UUID.nameUUIDFromBytes(link.getLink().getBytes()),filters);
			response.getNext();
		}*/
	}

}
