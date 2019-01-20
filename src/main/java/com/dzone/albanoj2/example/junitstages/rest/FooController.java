package com.dzone.albanoj2.example.junitstages.rest;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dzone.albanoj2.example.junitstages.domain.Foo;
import com.dzone.albanoj2.example.junitstages.repository.FooRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/foo")
public class FooController {
	
	@Autowired
	private FooRepository repository;

	@GetMapping("/{id}")
	public Mono<Foo> findById(@PathVariable Long id) {
		return repository.findById(id)
			.map(Mono::just)
			.orElseThrow();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
    public void handleNotFound() {}
}
