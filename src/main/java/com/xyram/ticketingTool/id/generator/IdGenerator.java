package com.xyram.ticketingTool.id.generator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Id;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdGenerator implements IdentifierGenerator{
	
	private final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	public static final String ID_GENERATOR = "idGenerator";

	public static final Random RANDOM = new Random();

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		String id = null;
		String prefix = null;
		boolean isIdPrefixPresent = false;
		IdPrefix idPrefix = null;

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field feild : fields) {
			if (feild.isAnnotationPresent(Id.class) && feild.isAnnotationPresent(IdPrefix.class)) {
				isIdPrefixPresent = true;
				idPrefix = feild.getAnnotation(IdPrefix.class);
				break;
			}
		}

		if (null != idPrefix)
			prefix = idPrefix.value();
		else if (!isIdPrefixPresent) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"IdPrefix not found");
		}

		return prefix + RandomStringUtils.randomAlphanumeric(5) + RandomStringUtils.randomAlphabetic(2) + ThreadLocalRandom.current().nextInt(99, 1000);
	}

}
