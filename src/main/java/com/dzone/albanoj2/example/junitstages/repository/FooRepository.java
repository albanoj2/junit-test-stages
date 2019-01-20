package com.dzone.albanoj2.example.junitstages.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dzone.albanoj2.example.junitstages.domain.Foo;

@Service
public class FooRepository {

	private final List<Foo> foos = new ArrayList<>();
	
	public void save(Foo foo) {
		foos.add(foo);
	}
	
	public Optional<Foo> findById(long id) {
		return foos.stream()
			.filter(foo -> foo.getId() == id)
			.findFirst();
	}
	
	public List<Foo> findAll() {
		return foos;
	}
}
