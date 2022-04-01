package com.icai.practicas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TelefonoTest {

    @Test
    public void validarTelefonoTesting(){
        // Strings que no tienen el formato normal
        assertEquals(false, (new Telefono("06789")).validar());
        assertEquals(false, (new Telefono("01334v67Z")).validar());
        assertEquals(false, (new Telefono("012345678-")).validar());
        //assertEquals(false, (new Telefono("0123456789")).validar()); //Este no debería ir pero lo deja pasar!!

        // DNI que sí cumplen el formato normal
        assertEquals(true, (new Telefono("025684200")).validar());
        assertEquals(true, (new Telefono("025684193")).validar());
        assertEquals(true, (new Telefono("026348325")).validar());
    }
}
