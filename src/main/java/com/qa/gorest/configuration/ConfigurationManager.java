package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

//This class is responsible for reading Config.properties 
public class ConfigurationManager {

	private Properties prop;
	private FileInputStream ip;

	public Properties initProp() {
		prop = new Properties();

		// maven: Command line argument
		// maven Clean install -Denv="qa"
		String envName = System.getProperty("env");

		try {
			if (envName == null) {
				System.out.println("No environment was given. Hence running tests in defualt environment: QA");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");				
			}

			else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					System.out.println("Test execution started in qa environment");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					System.out.println("Test execution started in stage environment");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					System.out.println("Test execution started in dev environment");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					System.out.println("Test execution started in prod environment");
					break;
				default:
					System.out.println("Please pass the rigth environment. " + envName + " is not a valid environment");
					throw new APIFrameworkException("NO ENVIRONMENT IS GIVEN");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

}
