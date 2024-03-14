package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddContactTests extends TestBase {

    @Test
    public void addContactSuccessTest() {
        ContactDto dto = ContactDto.builder()
                .name("John")
                .lastName("Smith")
                .email("john2@gm.com")
                .phone("1234567890")
                .address("Koblenz")
                .description("goalkeeper")
                .build();
//        MessageDto message =
                given()
                .contentType("application/json")
                .header(AUTH, token)
                .body(dto)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was added!"));
//                .extract().response().as(MessageDto.class);
//        System.out.println(message.getMessage());
    }
}
