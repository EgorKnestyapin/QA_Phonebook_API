package com.phonebook.RAtests;

import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class LoginTests extends TestBase {
    AuthRequestDto auth = AuthRequestDto.builder()
            .username("john1@gm.com")
            .password("Qwerty123$")
            .build();

    @Test
    public void loginSuccessTest() {
        AuthResponseDto dto = given()
                .contentType("application/json")
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(dto.getToken());
    }

    @Test
    public void loginSuccessTest2() {
        String responseToken = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .extract().path("token");
        System.out.println(responseToken);
    }

    @Test
    public void loginErrorWithWrongEmailTest() {
        ErrorDto errorDto = given()
                .contentType(ContentType.JSON)
                .body(AuthRequestDto.builder()
                        .username("john1@gmcom")
                        .password("Qwerty123$").build())
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage() + " *** " + errorDto.getError());
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
    }
}
