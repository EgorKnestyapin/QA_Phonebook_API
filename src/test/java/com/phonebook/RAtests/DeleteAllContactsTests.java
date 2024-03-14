package com.phonebook.RAtests;

import com.phonebook.dto.MessageDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteAllContactsTests extends TestBase {
    @Test
    public void deleteAllContactsSuccessTest() {
//        MessageDto dto =
                given()
                .header(AUTH, token)
                .when()
                .delete("contacts/clear")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("All contacts was deleted!"));
//                .extract().response().as(MessageDto.class);
//        System.out.println(dto.getMessage());
    }
}
