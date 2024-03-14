package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateContactTests extends TestBase {
    String id;
    ContactDto contactDto;

    @BeforeMethod
    public void precondition() {
        contactDto = ContactDto.builder()
                .name("John")
                .lastName("Smith")
                .email("john1@gm.com")
                .phone("1234567890")
                .address("Koblenz")
                .description("goalkeeper")
                .build();
        String message =
                given()
                        .contentType("application/json")
                        .header(AUTH, token)
                        .body(contactDto)
                        .when()
                        .post("contacts")
                        .then()
                        .assertThat().statusCode(200)
                        .extract().path("message");
        String[] split = message.split(": ");
        id = split[1];
        contactDto.setId(id);
    }

    @Test
    public void updateContactSuccessTest() {
        contactDto.setAddress("Berlin"); // change address
        contactDto.setLastName("Johnson"); // change last name

//        MessageDto dto =
        given()
                .contentType("application/json")
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was updated"));
    }

    @Test
    public void updateContactInvalidPhoneNumberTest() {
        contactDto.setLastName("Johnson");
        contactDto.setPhone("123"); // invalid phone number

        ErrorDto errorDto = given()
                .contentType("application/json")
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage() + " *** " + errorDto.getError());
    }

    @Test
    public void updateContactWithUnauthorizedUser() {
        contactDto.setLastName("Johnson");

        ErrorDto errorDto = given()
                .contentType("application/json")
                .header(AUTH, "32523652")
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage() + " *** " + errorDto.getError());
        Assert.assertEquals(errorDto.getMessage(), "JWT strings must contain exactly 2 period characters. " +
                "Found: 0");
    }

    @Test
    public void updateContactWithNonExistingContactId() {
        contactDto.setLastName("Johnson");
        contactDto.setId("6237627");

        ErrorDto errorDto = given()
                .contentType("application/json")
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage() + " *** " + errorDto.getError());
        Assert.assertEquals(errorDto.getMessage(), "Contact with id: 6237627 not found in your contacts!");
    }
}
