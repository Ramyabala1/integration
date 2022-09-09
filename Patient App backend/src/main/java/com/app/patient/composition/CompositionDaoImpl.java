package com.app.patient.composition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompositionDaoImpl implements CompositionDao {
	@Autowired
	CompositionRepo repo;

	@Override
	public Composition saveComposition(Composition c) {
		return repo.save(c);
	}

}
