package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.MessageDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase {
    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto dto = ContactDto.builder()
                .name("John")
                .lastName("Smith")
                .email("john2@gm.com")
                .phone("1234567890")
                .address("Koblenz")
                .description("goalkeeper")
                .build();
        String message =
                given()
                        .contentType("application/json")
                        .header(AUTH, token)
                        .body(dto)
                        .when()
                        .post("contacts")
                        .then()
                        .assertThat().statusCode(200)
                        .extract().path("message");
        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest() {
//        MessageDto dto =
                given()
                .header(AUTH, token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
//                .extract().response().as(MessageDto.class);
//        System.out.println(dto.getMessage());
    }
}
