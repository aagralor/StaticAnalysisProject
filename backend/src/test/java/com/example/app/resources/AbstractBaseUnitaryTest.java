package com.example.app.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class AbstractBaseUnitaryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseUnitaryTest.class);
	
	@Test
	public void contextLoads() {
		LOGGER.debug("AbstractBaseServiceTest: contextLoads()");
	}


}
