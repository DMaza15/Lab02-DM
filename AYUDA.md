# Carrito de Compras POO - Tienda Tecsup
 
**Autor:** Juan Leon Suiyon
**Curso:** Programación en Móviles - Kotlin
**Instructor:** Juan León Suiyon
 
## Descripción
 
Programa de consola en Kotlin que simula un carrito de compras aplicando los
cuatro pilares de la Programación Orientada a Objetos. El sistema modela
distintos tipos de producto (electrónicos, alimentos, ropa), cada uno con su
propia forma de calcular el impuesto, y un `Carrito` que agrupa la lógica de
cálculo de subtotal, IGV, descuento y producto más caro.
 
## Funciones/clases implementadas
 
- `Producto` (clase abstracta): define la estructura común de todo producto.
- `ProductoElectronico`, `ProductoAlimento`, `ProductoRopa`: subclases que
  heredan de `Producto` y personalizan el cálculo del impuesto.
- `Carrito`: administra la lista de productos y calcula subtotal, IGV, total,
  descuento y producto más caro.
- `main()`: arma el carrito de ejemplo y muestra el reporte final por consola.
## Pilares de POO aplicados
 
### 1. Abstracción
 
`Producto` es una clase `abstract`: no se puede crear un `Producto` "genérico",
solo tiene sentido a través de sus subclases concretas. Define qué datos y
comportamientos tiene todo producto, sin decidir cómo se calcula el impuesto:
 
```kotlin
abstract class Producto(
    val nombre: String,
    precioInicial: Double,
    var cantidad: Int
) {
    abstract fun calcularImpuesto(): Double
}
```
 
### 2. Encapsulamiento
 
El precio no es una propiedad pública y editable libremente: es `private` y
solo se accede mediante `getPrecio()` y se modifica mediante `setPrecio()`,
que valida que el nuevo precio sea mayor a cero antes de aceptarlo:
 
```kotlin
private var precio: Double = precioInicial
 
fun setPrecio(nuevoPrecio: Double) {
    if (nuevoPrecio > 0) {
        precio = nuevoPrecio
    } else {
        println("Precio invalido para $nombre, no se actualizo.")
    }
}
```
 
La lista de productos dentro de `Carrito` también es `private`; el resto del
programa solo puede interactuar con ella a través de funciones como
`agregarProducto()`, `eliminarProducto()` o `buscarProducto()`.
 
### 3. Herencia
 
`ProductoElectronico`, `ProductoAlimento` y `ProductoRopa` heredan de
`Producto` con `: Producto(nombre, precio, cantidad)`, reutilizando toda la
lógica de `calcularSubtotal()` y las propiedades comunes sin reescribirlas:
 
```kotlin
class ProductoRopa(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val talla: String
) : Producto(nombre, precio, cantidad) {
    override fun calcularImpuesto(): Double = calcularSubtotal() * 0.18
}
```
 
### 4. Polimorfismo
 
Cada subclase sobrescribe (`override`) `calcularImpuesto()` con su propia
regla de negocio (electrónicos y ropa pagan 18% de IGV, alimentos están
exonerados). Cuando `Carrito` recorre su lista de productos, cada objeto
ejecuta su propia versión del método sin que `Carrito` necesite saber de qué
subtipo se trata:
 
```kotlin
fun calcularImpuestoGeneral(): Double =
    productos.sumOf { it.calcularImpuesto() }
```
 
Esa misma línea funciona igual sin importar si la lista mezcla productos
electrónicos, alimentos o ropa: cada `it.calcularImpuesto()` resuelve en
tiempo de ejecución a la implementación correcta de cada clase.
 
## Captura de la consola
 
*(Reemplaza esta sección con tu captura del resultado final en Android
Studio.)*
 
## Historial de commits
 
1. Proyecto inicial y clase abstracta Producto
2. Agrega subclases ProductoElectronico, ProductoAlimento y ProductoRopa (herencia)
3. Sobrescribe calcularImpuesto y mostrarInfo en cada subclase (polimorfismo)
4. Agrega clase Carrito con lógica de subtotal, impuesto y total
5. Agrega descuento con when, producto más caro, buscar y eliminar producto
6. Agrega main con reporte final formateado y README
