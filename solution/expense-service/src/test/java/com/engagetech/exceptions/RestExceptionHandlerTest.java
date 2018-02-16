/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.exceptions;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BindException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,ConstraintViolationImpl.class})
public class RestExceptionHandlerTest {

	@TestSubject
	RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

	@Mock
	private HttpHeaders mockHeaders;
	@Mock
	private WebRequest mockRequest;
	@Mock
	private MethodArgumentNotValidException mockMethodArgumentNotValidException;
	@Mock
	private BindingResult mockBindingResult;
	@Mock
	private TypeMismatchException mockTypeMismatchException;
	@Mock
	private MissingServletRequestPartException mockMissingServletRequestPartException;
	@Mock
	private WebRequest mockWebRequest;
	@Mock
	private ConstraintViolationException mockConstraintViolationException;
	@Mock
	private NoHandlerFoundException mockNoHandlerFoundException;
	@Mock
	private HttpRequestMethodNotSupportedException mockHttpRequestMethodNotSupportedException;
	@Mock
	private HttpMediaTypeNotSupportedException mockHttpMediaTypeNotSupportedException;
	
	private FieldError fieldError;
	private List<FieldError> fieldErrors;
	private ObjectError objectError;
	private List<ObjectError> globalErrors;
	private HttpStatus httpStatus;
	
	@Before
	public void setUp() {
		
		fieldError = new FieldError("testObject", "testField", "testMessage");
		fieldErrors = new LinkedList<>();
		fieldErrors.add(fieldError);
		objectError = new ObjectError("testObject", "testMessage");
		globalErrors = new LinkedList<>();
		globalErrors.add(objectError);
		
		mockHeaders = EasyMock.createMock(HttpHeaders.class);
		mockRequest = EasyMock.createMock(WebRequest.class);
		mockMethodArgumentNotValidException = EasyMock.createMock(MethodArgumentNotValidException.class);
		mockBindingResult = EasyMock.createMock(BindingResult.class);
		mockTypeMismatchException = EasyMock.createMock(TypeMismatchException.class);
		mockMissingServletRequestPartException = EasyMock.createMock(MissingServletRequestPartException.class);
		mockWebRequest = EasyMock.createMock(WebRequest.class);
		mockConstraintViolationException = EasyMock.createMock(ConstraintViolationException.class);
		mockNoHandlerFoundException = EasyMock.createMock(NoHandlerFoundException.class);
		mockHttpRequestMethodNotSupportedException = EasyMock.createMock(HttpRequestMethodNotSupportedException.class);
		mockHttpMediaTypeNotSupportedException = EasyMock.createMock(HttpMediaTypeNotSupportedException.class);
		
	}
	
	@Test
	public void testContructor() {
		restExceptionHandler = new RestExceptionHandler();
		assertNotNull(restExceptionHandler);
	}
	
	@Test
	public void testHandleMethodArgumentNotValid() {
		EasyMock.expect(mockMethodArgumentNotValidException.getBindingResult()).andReturn(mockBindingResult).atLeastOnce();		
		EasyMock.expect(mockBindingResult.getFieldErrors()).andReturn(fieldErrors);
		EasyMock.expect(mockBindingResult.getGlobalErrors()).andReturn(globalErrors);
		EasyMock.expect(mockMethodArgumentNotValidException.getLocalizedMessage()).andReturn("Error Occured");
		EasyMock.expect(mockHeaders.isEmpty()).andReturn(true);
		
		EasyMock.replay(mockMethodArgumentNotValidException);
		EasyMock.replay(mockHeaders);
		EasyMock.replay(mockRequest);
		EasyMock.replay(mockBindingResult);		
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleMethodArgumentNotValid(mockMethodArgumentNotValidException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockMethodArgumentNotValidException);
		EasyMock.verify(mockHeaders);
		EasyMock.verify(mockRequest);
		EasyMock.verify(mockBindingResult);
	}
	
	@Test
	public void testHandleBindException() {
		BindException mockBindException = PowerMock.createMock(BindException.class);
		
		EasyMock.expect(mockBindException.getBindingResult()).andReturn(mockBindingResult).atLeastOnce();		
		EasyMock.expect(mockBindingResult.getFieldErrors()).andReturn(fieldErrors);
		EasyMock.expect(mockBindingResult.getGlobalErrors()).andReturn(globalErrors);
		EasyMock.expect(mockBindException.getLocalizedMessage()).andReturn("Error Occured");
		EasyMock.expect(mockHeaders.isEmpty()).andReturn(true);

		PowerMock.replay(mockBindException);
		EasyMock.replay(mockHeaders);
		EasyMock.replay(mockRequest);
		EasyMock.replay(mockBindingResult);		

		ResponseEntity<Object> responseEntity = restExceptionHandler.handleBindException(mockBindException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		PowerMock.verify(mockBindException);
		EasyMock.verify(mockHeaders);
		EasyMock.verify(mockRequest);
		EasyMock.verify(mockBindingResult);
	}
	
	@Test
	public void testHandleTypeMismatch() {
		EasyMock.expect(mockTypeMismatchException.getValue()).andReturn("value");
		EasyMock.expect(mockTypeMismatchException.getPropertyName()).andReturn("name");
		EasyMock.expect(mockTypeMismatchException.getRequiredType()).andReturn(null);
		EasyMock.expect(mockTypeMismatchException.getLocalizedMessage()).andReturn("Error Occured");

		EasyMock.replay(mockTypeMismatchException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleTypeMismatch(mockTypeMismatchException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockTypeMismatchException);
	}
	
	@Test
	public void testHandleMissingServletRequestPart() {		
		EasyMock.expect(mockMissingServletRequestPartException.getRequestPartName()).andReturn("value");
		EasyMock.expect(mockMissingServletRequestPartException.getLocalizedMessage()).andReturn("Error Occured");

		EasyMock.replay(mockMissingServletRequestPartException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleMissingServletRequestPart(mockMissingServletRequestPartException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockMissingServletRequestPartException);
	}

	@Test
	public void testHandleMissingServletRequestParameter() {		
		
		MissingServletRequestParameterException mockMissingServletRequestParameterException = PowerMock.createMock(MissingServletRequestParameterException.class);
		
		EasyMock.expect(mockMissingServletRequestParameterException.getParameterName()).andReturn("value");
		EasyMock.expect(mockMissingServletRequestParameterException.getLocalizedMessage()).andReturn("Error Occured");

		EasyMock.replay(mockMissingServletRequestParameterException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleMissingServletRequestParameter(mockMissingServletRequestParameterException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockMissingServletRequestParameterException);
	}
	@Test
	public void testHandleMethodArgumentTypeMismatch() {			
		Throwable mockThrowable = EasyMock.createMock(Throwable.class);
		MethodParameter mockParameter= EasyMock.createMock(MethodParameter.class);
		
		MethodArgumentTypeMismatchException mockMethodArgumentTypeMismatchException = new MethodArgumentTypeMismatchException(new Object(), MethodArgumentTypeMismatchException.class, "name", mockParameter, mockThrowable);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleMethodArgumentTypeMismatch(mockMethodArgumentTypeMismatchException, mockWebRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testHandleConstraintViolation() {	
		ConstraintViolationImpl mockViolation = PowerMock.createMock(ConstraintViolationImpl.class);
		Path mockPath = EasyMock.createMock(Path.class);
		Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
		constraintViolations.add(mockViolation);

		EasyMock.expect(mockConstraintViolationException.getConstraintViolations()).andReturn(constraintViolations);

		EasyMock.expect(mockViolation.getRootBeanClass()).andReturn(ConstraintViolationImpl.class);
		EasyMock.expect(mockViolation.getPropertyPath()).andReturn(mockPath);
		EasyMock.expect(mockViolation.getMessage()).andReturn("message");
		EasyMock.expect(mockConstraintViolationException.getMessage()).andReturn("Error Occured");
		EasyMock.expect(mockConstraintViolationException.getLocalizedMessage()).andReturn("Error Occured");
		EasyMock.expect(mockConstraintViolationException.getStackTrace()).andReturn(null);
		EasyMock.expect(mockConstraintViolationException.getCause()).andReturn(null);

		EasyMock.replay(mockConstraintViolationException);
		EasyMock.replay(mockViolation);
		EasyMock.replay(mockPath);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleConstraintViolation(mockConstraintViolationException, mockWebRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockConstraintViolationException);
		EasyMock.verify(mockViolation);
		EasyMock.verify(mockPath);
	}
	
	@Test
	public void testHandleNoHandlerFoundException() {	

		EasyMock.expect(mockNoHandlerFoundException.getHttpMethod()).andReturn("get");
		EasyMock.expect(mockNoHandlerFoundException.getRequestURL()).andReturn("www.test.com");
		EasyMock.expect(mockNoHandlerFoundException.getLocalizedMessage()).andReturn("message");

		EasyMock.replay(mockNoHandlerFoundException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleNoHandlerFoundException(mockNoHandlerFoundException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockNoHandlerFoundException);
	}
	@Test
	public void testHandleHttpRequestMethodNotSupported() {	
		Set<HttpMethod> supportedMethods = new HashSet<>();
		supportedMethods.add(HttpMethod.DELETE);
		supportedMethods.add(HttpMethod.GET);
		supportedMethods.add(HttpMethod.PUT);
		supportedMethods.add(HttpMethod.POST);
		EasyMock.expect(mockHttpRequestMethodNotSupportedException.getMethod()).andReturn("get");
		EasyMock.expect(mockHttpRequestMethodNotSupportedException.getSupportedHttpMethods()).andReturn(supportedMethods);
		EasyMock.expect(mockHttpRequestMethodNotSupportedException.getLocalizedMessage()).andReturn("message");

		EasyMock.replay(mockHttpRequestMethodNotSupportedException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleHttpRequestMethodNotSupported(mockHttpRequestMethodNotSupportedException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockHttpRequestMethodNotSupportedException);
	}	
	
	@Test
	public void testHandleHttpMediaTypeNotSupported() {	
		List<MediaType> supportedMediaTypes = new LinkedList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);

		EasyMock.expect(mockHttpMediaTypeNotSupportedException.getContentType()).andReturn(MediaType.APPLICATION_JSON);
		EasyMock.expect(mockHttpMediaTypeNotSupportedException.getSupportedMediaTypes()).andReturn(supportedMediaTypes);
		EasyMock.expect(mockHttpMediaTypeNotSupportedException.getLocalizedMessage()).andReturn("message");

		EasyMock.replay(mockHttpMediaTypeNotSupportedException);
		
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleHttpMediaTypeNotSupported(mockHttpMediaTypeNotSupportedException, mockHeaders, httpStatus, mockRequest);
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getMessage());
		assertNotNull(apiErrorVO.getErrors());
		
		EasyMock.verify(mockHttpMediaTypeNotSupportedException);
	}
	
	
	@Test
	public void testHandleAll() {
		Exception ex = new NullPointerException();
		ResponseEntity<Object> responseEntity = restExceptionHandler.handleAll(ex, mockWebRequest);			
		assertNotNull(responseEntity);
		
		APIErrorVO apiErrorVO = (APIErrorVO) responseEntity.getBody();
		assertNotNull(apiErrorVO);
		assertNotNull(apiErrorVO.getStatus());
		assertNotNull(apiErrorVO.getErrors());
	}
}
