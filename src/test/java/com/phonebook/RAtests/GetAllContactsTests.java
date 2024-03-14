package com.phonebook.RAtests;

import com.phonebook.dto.AllContactsDto;
import com.phonebook.dto.ContactDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllContactsTests extends TestBase {

    @Test
    public void getAllContactsSuccessTest() {
        AllContactsDto contacts = given()
                .header(AUTH, token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(AllContactsDto.class);
        for (ContactDto contact: contacts.getContacts()) {
            System.out.println(contact.getId() + " *** " + contact.getName() + " " + contact.getLastName());
            System.out.println("****************");
        }
    }
}
