package io.ky.cb.cucumber.stepdefs;

import io.ky.cb.CblogApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CblogApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
