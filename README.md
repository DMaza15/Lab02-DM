¿Por qué nombre y precio son val pero cantidad es var? ¿Qué pasaría si intentas cambiar el precio?
- nombre y precio usan val (inmutables) porque los datos de un catálogo no cambian una vez definido el producto. cantidad usa var porque el usuario puede modificar cuántas unidades lleva en su carrito. Si se intenta modificar un val (como el precio), el compilador de Kotlin arrojará un error.  
