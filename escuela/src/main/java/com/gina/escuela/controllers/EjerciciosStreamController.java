package com.gina.escuela.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ejercicios")
public class EjerciciosStreamController {

    //1. Obtener los 3 números más grandes de una lista
    // Resultado esperado: [89, 76, 45]
    @GetMapping("/1")
    public ResponseEntity<List<Integer>> numerosMasGrandes() {
        List<Integer> lista = Arrays.asList(10, 45, 2, 89, 33, 76, 1);
        return ResponseEntity.ok(
                lista.stream()
                        .distinct()
                        .sorted(Comparator.reverseOrder())
                        .limit(3)
                        .toList()
        );
    }
    //2. Contar cuántas veces se repite cada palabra en una lista
    // Resultado esperado: {java=3, python=2, c=1}
    @GetMapping("/2")
    public ResponseEntity<List<String>> palabrasRepetidas() {
        List<String> palabras = Arrays.asList("java", "python", "java", "c", "python", "java");
        return ResponseEntity.ok(palabras.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList()
        );
    }
    //3. Obtener una lista sin duplicados, en orden alfabético
    // Resultado esperado: [Ana, Carlos, Pedro]
    @GetMapping("/3")
    public ResponseEntity<List<String>> sinDuplicados() {
        List<String> nombres = Arrays.asList("Carlos", "Ana", "Pedro", "Ana", "Carlos");
        return ResponseEntity.ok(nombres.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList()
        );
    }
    //4. Obtener el segundo número más pequeño de una lista
    // Resultado esperado: 3
    @GetMapping("/4")
    public ResponseEntity<Integer> segundoMasPequeño() {
        List<Integer> numeros = Arrays.asList(8, 3, 10, 1, 6);
        return ResponseEntity.ok(
                numeros.stream()
                        .filter(n -> n % 2 != 0)
                        .mapToInt(Integer::intValue)
                        .sum()
        );
    }
    //5. Concatenar los nombres en una sola cadena separados por coma
    // Resultado esperado: "Ana,Luis,Pedro"
    @GetMapping("/5")
    public ResponseEntity<String> nombresComa() {
        List<String> nombres = Arrays.asList("Ana", "Luis", "Pedro");
        /*return ResponseEntity.ok(
                lista.stream().reduce("", (a,b) -> a + b)
        );*/
        return ResponseEntity.ok(
                nombres.stream().collect(Collectors.joining(""))
        );
    }
    //6. Filtrar los strings que empiecen con una vocal y devolverlos en mayúsculas
    // Resultado esperado: ["ELEFANTE", "IGUANA", "OSO"]
    @GetMapping("/6")
    public ResponseEntity<List<String>> comienzaConA() {
        List<String> palabras = Arrays.asList("elefante", "tigre", "iguana", "oso", "mono");
        return ResponseEntity.ok(palabras.stream()
                .filter(cadena -> cadena.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList()
        );
    }
    //7. Separar los números en pares e impares en un Map
    // Resultado esperado: {pares=[2, 4, 6], impares=[1, 3, 5]}
    @GetMapping("/7")
    public ResponseEntity<List<Integer>> numerosParesEImpares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        return ResponseEntity.ok(
                numeros.stream().distinct().toList()
        );
    }
    //8. Agrupar palabras por su primera letra
    // Resultado esperado: {p=[perro, pato], g=[gato, gallo], r=[rana]}
    @GetMapping("/8")
    public ResponseEntity<List<String>> agruparPrimeraLetra() {
        List<String> palabras = Arrays.asList("perro", "pato", "gato", "gallo", "rana");
        return ResponseEntity.ok(palabras.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList()
        );
    }
    //9. Obtener los strings más largos de una lista (longitud máxima)
    // Resultado esperado: ["estrella"]
    @GetMapping("/9")
    public ResponseEntity<String> stringMasLargo() {
        List<String> palabras = Arrays.asList("sol", "luna", "estrella", "mar");
        /*return ResponseEntity.ok(
                lista.stream().reduce("", (a,b) -> a + b)
        );*/
        return ResponseEntity.ok(
                palabras.stream().collect(Collectors.joining(""))
        );
    }

    //10. Eliminar elementos vacíos o nulos de una lista de strings
    // Resultado esperado: ["hola", "adiós", " "]
    @GetMapping("/10")
    public ResponseEntity<List<String>> eliminarNulos() {
        List<String> datos = Arrays.asList("hola", "", null, "adiós", " ");
        return ResponseEntity.ok(datos.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList()
        );
    }
}