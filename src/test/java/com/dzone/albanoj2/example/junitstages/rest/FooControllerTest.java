package com.dzone.albanoj2.example.junitstages.rest;

import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.dzone.albanoj2.example.junitstages.domain.Foo;
import com.dzone.albanoj2.example.junitstages.repository.FooRepository;

import static org.mockito.Mockito.doReturn;

@WebFluxTest
@IntegrationTest
@ContextConfiguration(classes = ControllerTestConfig.class)
public class FooControllerTest {

	@Autowired
    private WebTestClient webClient;
	
	@MockBean
	private FooRepository repository;
	
	@Test
	public void findByIdWithNoFoosResultsIn404() {
		
		webClient.get()
			.uri("/foo/{id}", 1)
		 	.accept(MediaType.APPLICATION_JSON)
 			.exchange()
	        .expectStatus().isNotFound();
	}
	
	@Test
	public void findByIdWithFooWithDifferentIdResultsInFooNotFound() {
		
		long desiredId = 1;
		long differentId = 2;
		Foo foo = new Foo(differentId);
		
		doReturn(Optional.of(foo)).when(repository).findById(eq(differentId));
		
		webClient.get()
			.uri("/foo/{id}", desiredId)
		 	.accept(MediaType.APPLICATION_JSON)
 			.exchange()
	        .expectStatus().isNotFound();
	}
	
	@Test
	public void findByIdWithMatchingFoosResultsInFooFound() {
		
		long id = 1;
		Foo foo = new Foo(id);
		
		doReturn(Optional.of(foo)).when(repository).findById(eq(id));
		
		webClient.get()
			.uri("/foo/{id}", id)
		 	.accept(MediaType.APPLICATION_JSON)
 			.exchange()
	        .expectStatus().isOk()
	        .expectBody(Foo.class);
	}
}
