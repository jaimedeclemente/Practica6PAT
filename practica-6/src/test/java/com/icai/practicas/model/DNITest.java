package com.icai.practicas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DNITest {
    
    @Test
    public void validarDNItesting(){

        // DNIs que no son válidos según el Ministerio de interior
        assertEquals(false, (new DNI("00000000M")).validar());
        assertEquals(false, (new DNI("00000001F")).validar());
        assertEquals(false, (new DNI("99999999X")).validar());

        // Strings que no tienen el formato normal
        assertEquals(false, (new DNI("06789")).validar());
        assertEquals(false, (new DNI("01334v67Z")).validar());
        assertEquals(false, (new DNI("012345678-")).validar());
        assertEquals(false, (new DNI("0123456789")).validar());

        // DNIs que sí cumplen el formato normal
        assertEquals(true, (new DNI("02568420X")).validar());
        assertEquals(true, (new DNI("02568419D")).validar());
        assertEquals(true, (new DNI("02634832K")).validar());
  }
}
