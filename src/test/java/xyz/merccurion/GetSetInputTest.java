package xyz.merccurion;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


class GetSetInputTest {
   static GetSetInput pojo = new GetSetInput();

    @Test
    @DisplayName ("Input should not be null.")
    public void checkForNullInputTest() {   
        assertThrows(NullPointerException.class,
            () -> {
                pojo.checkForNullInput("");
            });
    }


}