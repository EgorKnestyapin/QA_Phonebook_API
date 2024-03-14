package com.phonebook.RAtests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiam9objFA" +
            "Z20uY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MTEwMTE2NTQsImlhdCI6MTcxMDQxMTY1NH0.NvKEDqcim4X-pqB3" +
            "AsTIiVyZIT1gwVS05gcqcOI6hBQ";

    public static final String AUTH = "Authorization";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
