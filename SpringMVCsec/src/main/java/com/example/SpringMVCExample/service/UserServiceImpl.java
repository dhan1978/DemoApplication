package com.example.SpringMVCExample.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.SpringMVCExample.dao.UserDAO;
import com.example.SpringMVCExample.model.Users;
import com.example.SpringMVCExample.util.ResponseDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

@Configuration
@Service
public class UserServiceImpl  implements UserService {
	private UserDAO userDAO;
//--------------------
	
	@Autowired
	protected RestTemplate restTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	protected Gson gson = new GsonBuilder().create();
//-----------------------------------------------------------
	@Value("${name.baseUrl}")
	private String baseUrl;
	//="dhanapp.eastus.cloudapp.azure.com:8094";
	
	private static final  String getUserRequest = "/demo/all";
	
	private static final String  postUserRequest = "/demo/add?name=";
	//------------------------
	

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Transactional
	public void updateUser(Users p) {
		// TODO Auto-generated method stub
	//	userDAO.updateUser(p);
	}

	@Transactional
	public Users getUserById(int id) {
		// TODO Auto-generated method stub
	//	return userDAO.getUserById(id);
		return null;
	}
	@Transactional
	public void removeUser(int id) {
		// TODO Auto-generated method stub
		//userDAO.removeUser(id);
	}
	

	//------------------------
		@Transactional
		public void addUser(Users p) {
			// TODO Auto-generated method stub
			//userDAO.addUser(p);
			insertUser("post",p);
		}
		
		@Transactional
		public List<Users> listUsers() {
			// TODO Auto-generated method stub
		
			
			return getUserDetail("get");
		}
		
	//----------------------------------------------
		
		protected HttpHeaders createHttpHeader() {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Content-Type", "application/json");
			return headers;
		}
		private StringBuilder getUrl(String request)   {
			StringBuilder urlBuilder = new StringBuilder("http://" +baseUrl);
			if("get".equals(request)) {
				urlBuilder.append(getUserRequest);
			} else {
				urlBuilder.append(postUserRequest);
			}
			return urlBuilder;
		}
		public List<Users> getUserDetail(String requestType)   {
			StringBuilder urlBuilder = getUrl(requestType);
			List<Users> userList=new ArrayList<Users>();
			
			HttpHeaders headers = createHttpHeader();
			ResponseDetails clientResponse = new ResponseDetails(new Date(), "", "", "", "");
			LOGGER.info("triggerFoundationDataSync fDataURL:{} ", urlBuilder.toString());
			try {
				ResponseEntity<String> response = exec(headers, urlBuilder.toString(), HttpMethod.GET);
				printResponse(response);
				clientResponse = prepareResponse(response);
				userList = getGroupDetailResponse(response.getBody());
			} catch (Exception exception) {
				try {
					try {
						Thread.sleep(60000);
					} catch (InterruptedException exception3) {
					}
					ResponseEntity<String> response = exec(headers, urlBuilder.toString(), HttpMethod.GET);
					printResponse(response);
					
				} catch (HttpClientErrorException exception2) {
					LOGGER.error("", exception);
				}
				
			}
			return  userList;
		}

		
		//@Override
		public ResponseDetails insertUser(String requestType,Users user) {
			Users groupuser= new Users();
			
			List<Users> groupList=new ArrayList<Users>();
			groupList.add(groupuser);
			LOGGER.info("GroupList:[{}]", groupList);
		//	UserDetail defaultUser = fetchUserDetailByUserId(defaultUserId);
		//	HttpHeaders headers = createHttpHeader(defaultUser.getJwtToken());
			HttpHeaders headers = createHttpHeader();
			StringBuilder urlBuilder = getUrl(requestType);
			urlBuilder.append(user.getName());
			urlBuilder.append("&email=");
			urlBuilder.append(user.getEmail());
			LOGGER.info("group url:{} ", urlBuilder.toString());
			ResponseDetails clientResponse = new ResponseDetails(new Date(), "", "", "", "");
			String payLoad = gson.toJson(groupList);
			LOGGER.info("insert group payload:{} ", payLoad);
			try {
				ResponseEntity<String> response = exec(payLoad, headers, urlBuilder.toString(), HttpMethod.POST);
				printResponse(response);
				clientResponse = prepareResponse(response);

			} catch (HttpClientErrorException exception) {
				clientResponse = buildClientErrorResponse(exception, payLoad);
			}
			return clientResponse;
		}
		
		
		
		
		
		
		protected void printResponse(ResponseEntity<String> response) {
			LOGGER.info("Result - status ([{}]) has body:[{}] ", response.getStatusCode(), response.hasBody());
			if (response.hasBody()) {

				LOGGER.info("Result - response:{}", response);
			}
		}
		protected ResponseDetails prepareResponse(ResponseEntity<String> response) {
			String syncMessage = "Job executed with status: " + response.getStatusCode().toString();
			String statusCode = "" + response.getStatusCode().value(); 
			if( response.hasBody() && response.getBody().startsWith("Problem Encountered in DataSynch Today")) {
				syncMessage = response.getBody();
				statusCode = "500";
			}
			return new ResponseDetails(new Date(), syncMessage, "", statusCode, "");
		}

		/**
		 * Exec.
		 *
		 * @param headers    the headers
		 * @param url        the url
		 * @param httpMethod the http method
		 * @return the response entity
		 * @throws RestClientException the rest client exception
		 */
		protected ResponseEntity<String> exec(HttpHeaders headers, String url, HttpMethod httpMethod)
				throws RestClientException {
			HttpEntity<Object> entityReq = new HttpEntity<Object>(headers);
			LOGGER.info("url="+url);
			URI targetUrl = UriComponentsBuilder.fromUriString(url).build().encode().toUri(); // Build base url
			LOGGER.info("targetUrl="+targetUrl);
			return restTemplate.exchange(targetUrl, httpMethod, entityReq, String.class);
		}

		/**
		 * Exec.
		 *
		 * @param payLoad    the pay load
		 * @param headers    the headers
		 * @param url        the url
		 * @param httpMethod the http method
		 * @return the response entity
		 * @throws RestClientException the rest client exception
		 */
		protected ResponseEntity<String> exec(String payLoad, HttpHeaders headers, String url, HttpMethod httpMethod)
				throws RestClientException {
			HttpEntity<Object> entityReq = new HttpEntity<Object>(payLoad, headers);
			URI targetUrl = UriComponentsBuilder.fromUriString(url).build().encode().toUri(); // Build base url

			return restTemplate.exchange(targetUrl, httpMethod, entityReq, String.class);
		}
		protected ResponseDetails buildClientErrorResponse(HttpClientErrorException exception2, String payload) {
			return new ResponseDetails(new Date(), exception2.getMessage(), exception2.getStatusCode().toString(), ""+exception2.getStatusCode().value(), payload);
		}

		public List<Users> getGroupDetailResponse(String responsePayload) {
			Users[] groupDetail;
			List<Users> userList=null;
			try {
				groupDetail = gson.fromJson(responsePayload, Users[].class);
				userList=Arrays.asList(groupDetail);
			} catch (JsonSyntaxException exception) {
				userList = new ArrayList<Users>();
			}

			return userList;
		}
}
