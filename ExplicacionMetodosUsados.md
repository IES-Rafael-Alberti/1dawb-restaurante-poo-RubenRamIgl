## Explicación con ejemplos de los siguientes métodos utilizados en el proyecto

Las siguientes funciones son muy útiles para trabajar con colecciones en Kotlin, permitiéndote realizar operaciones complejas de manera concisa y funcional.

1. `find{}`:
   - **Descripción**: `find{}` es una función de extensión de las colecciones en Kotlin que devuelve el primer elemento que cumple con la condición especificada o `null` si ningún elemento cumple con la condición.
   - **Uso**: Se utiliza pasando una expresión lambda que define la condición que debe cumplir el elemento. La búsqueda se realiza en el orden de iteración de la colección.
   - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4)
     val firstEven = numbers.find { it % 2 == 0 } // Devuelve 2, que es el primer número par
     ```

2. `let{}`:
   - **Descripción**: `let{}` es una función de alcance que se usa para ejecutar un bloque de código con un objeto como contexto. El objeto sobre el que se invoca `let` se proporciona dentro del bloque mediante la variable implícita `it`.
   - **Uso**: Se usa para realizar operaciones en un objeto no nulo y para encadenar operaciones evitando la repetición de la variable.
   - **Ejemplo**:
     ```kotlin
     val number: Int? = 3
     number?.let {
         val triple = it * 3
         println(triple) // Imprime 9
     }
     ```

3. `lastOrNull()`:
   - **Descripción**: `lastOrNull()` es una función que devuelve el último elemento de una colección o `null` si la colección está vacía.
   - **Uso**: Se usa cuando quieres obtener el último elemento de una colección, pero también quieres manejar de forma segura el caso de que la colección pueda estar vacía.
   - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4)
     val lastNumber = numbers.lastOrNull() // Devuelve 4
     val emptyList = emptyList<Int>()
     val lastOfEmpty = emptyList.lastOrNull() // Devuelve null
     ```

4. `all{}`:
   - **Descripción**: `all{}` es una función que verifica si todos los elementos de una colección cumplen con una condición dada.
   - **Uso**: Se utiliza para validar si todos los elementos en una colección satisfacen una condición especificada en una expresión lambda.
   - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4)
     val areAllEven = numbers.all { it % 2 == 0 } // Devuelve false porque no todos los números son pares
     ```

5. `map{}`:
   - **Descripción**: `map{}` es una función que transforma cada elemento de una colección aplicando una función proporcionada a cada elemento.
   - **Uso**: Se utiliza para aplicar una transformación a cada elemento de una colección y obtener una lista de los resultados.
   - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3)
     val squaredNumbers = numbers.map { it * it } // Devuelve [1, 4, 9]
     ```

6. `flatMap{}`:
   - **Descripción**: `flatMap{}` es una función que transforma cada elemento de una colección en una lista de otros elementos. Luego, concatena todas las listas en una sola lista.
   - **Uso**: Se usa cuando necesitas transformar los elementos de una colección y luego aplanar varias colecciones en una sola.
   - **Ejemplo**:
     ```kotlin
     val fruits = listOf("apple", "banana")
     val characters = fruits.flatMap { it.toList() } // Devuelve ['a', 'p', 'p', 'l', 'e', 'b', 'a', 'n', 'a', 'n', 'a']
     ```

7. `ifEmpty{}`:
   - **Descripción**: `ifEmpty{}` es una función que devuelve el objeto original si no está vacío, o el resultado de la función lambda proporcionada si el objeto está vacío.
   - **Uso**: Se usa para proporcionar un valor predeterminado o realizar una acción en caso de que una colección esté vacía.
   - **Ejemplo**:
     ```kotlin
     val emptyList = emptyList<Int>()
     val defaultList = emptyList.ifEmpty { listOf(1, 2, 3) } // Devuelve [1, 2, 3]
     ```

8. `count{}`:
   - **Descripción**: `count{}` cuenta el número de elementos en una colección que cumplen con una condición especificada.
   - **Uso**: Se usa para contar elementos que satisfacen una condición sin tener que filtrar la colección primero.
   - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4)
     val evenCount = numbers.count { it % 2 == 0 } // Devuelve 2
     ```

9. `groupingBy{}`:
    - **Descripción**: `groupingBy{}` es una función que crea una instancia de `Grouping` a partir de una colección, clasificando los elementos por la clave devuelta por la función lambda proporcionada.
    - **Uso**: Se utiliza para agrupar elementos de una colección por alguna clave y luego realizar operaciones de agregación en los grupos.
    - **Ejemplo**:
     ```kotlin
     val words = listOf("a", "abc", "ab", "def", "abcd")
     val byLength = words.groupingBy { it.length }
     ```

10. `eachCount()`:
    - **Descripción**: `eachCount()` es una función que se utiliza junto con `groupingBy{}` para contar el número de elementos en cada grupo.
    - **Uso**: Se usa después de `groupingBy{}` para realizar un recuento de elementos por grupo.
    - **Ejemplo**:
     ```kotlin
     // Continuando del ejemplo anterior
     val countByLength = byLength.eachCount() // Devuelve un mapa {1=1, 3=2, 2=1, 4=1}
     ```

11. `maxByOrNull{}`:
    - **Descripción**: `maxByOrNull{}` devuelve el primer elemento que tiene el mayor valor de la función proporcionada o `null` si la colección está vacía.
    - **Uso**: Se usa para encontrar un elemento con el valor máximo basado en una condición específica.
    - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4)
     val maxNumber = numbers.maxByOrNull { it } // Devuelve 4
     ```

12. `filter{}`:
    - **Descripción**: `filter{}` es una función que evalúa cada elemento de una colección y devuelve una nueva colección que contiene solo los elementos que cumplen con la condición especificada en la función lambda.
    - **Uso**: Se usa para filtrar elementos de una colección basándose en una condición. Solo los elementos que hagan que la función lambda devuelva `true` serán incluidos en la colección resultante.
    - **Ejemplo**:
     ```kotlin
     val numbers = listOf(1, 2, 3, 4, 5, 6)
     val evenNumbers = numbers.filter { it % 2 == 0 } // Devuelve [2, 4, 6]
     ```
