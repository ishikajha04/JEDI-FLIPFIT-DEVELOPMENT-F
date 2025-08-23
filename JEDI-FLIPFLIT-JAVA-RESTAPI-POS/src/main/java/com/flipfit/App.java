package com.flipfit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flipfit.restController.*;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<Configuration>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }

    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");

        // Register existing Hello controller
        e.jersey().register(new HelloRestController());

        // Register Customer REST Controller
        e.jersey().register(new FlipfitCustomerRestController());

        // Register Gym Owner REST Controller
        e.jersey().register(new FlipfitGymOwnerRestController());

        // Register Admin REST Controller
        e.jersey().register(new FlipfitAdminRestController());

        LOGGER.info("All REST controllers registered successfully");
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
