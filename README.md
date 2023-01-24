<h1 align="center"> Gestión Activa de Construcciones </h1>

 <p align="left">
   <img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green">
   </p>

## :hammer:Funcionalidades del proyecto

- `Funcionalidad 1`: Registrar Estructuras y ubicarlas en el plano -
- `Funcionalidad 1a`: Listar todos los puentes, opción de ver en detalle -
- `Funcionalidad 1b`: Modificar y eliminar puentes -
- `Funcionalidad 2`: Registrar inspectores de campo -
- `Funcionalidad 2a`: Listar todos los inspectores, opción de ver en detalle -
- `Funcionalidad 2b`: Modificar y eliminar inspectores -
- `Funcionalidad 3`: Registrar inspecciones asociadas al puente desde el listado general de puentes -
- `Funcionalidad 3`: Realizar foto del daño al crear una inspección -
- `Funcionalidad 3a`: Listar todas las inspecciones, opción de ver en detalle -
- `Funcionalidad 3b`: Modificar y eliminar inspecciones -

La aplicación que he realizado, es una app móvil para la gestión de inspecciones en puentes.
El nombre para esta aplicación será GAC (Gestión Activa de Construcciones).

Esta app está diseñada para intentar cubrir las necesidades que surgen a los inspectores de campo, a la hora de realizar una inspección técnica sobre el estado de la infraestructura de un puente.
Para ello se diseña una app, la cual tiene varias funciones.

1. Registrar los detalles de un puente y su ubicación en el plano.
2. Registrar inspectores de campo que realizan las inspecciones.
3. Registrar inspecciones de puentes, y obtener documentación gráfica de los posibles daños encontrados.

En primer lugar deberemos crear el puente que queremos inspeccionar con su localización y el detalle de su estructura.
A continuación se deberá crear el inspector de campo que va a realizar la inspección, con sus datos personales y número de licencia.
Para poder registrar una inspección, tendremos que irnos al listado general de puentes y apretar sobre el botón registrar inspección asociado al puente que vamos a inspeccionar.
Deberemos disponer en esta primera versión del Id del inspector para poder registrarla, recibiremos un aviso antes de empezar a crear la inspección. Podremos obtener nuestro id desde la lista de detalle del inspector.
Una vez dentro del formulario de la inspección, podremos ir seleccionando los distintos apartados a inspeccionar, y realizar una foto del daño en cuestión.
Posibles mejoras de versiones posteriores, informe por daños, informe por tipo de daño, inspecciones asociadas a un puente, inspecciones realizadas por inspector.
Exportación de inspecciones a pdf.

## :hammer: Puntos Obligatorios

- `Funcionalidad 1`: La aplicación contará con, al menos, 7 Activities, utilizando controles ImageView, TextView, Button, CheckBox y RecyclerView para recoger y presentar información en pantalla y se hará, como mínimo, en dos idiomas -
- `Funcionalidad 2`: Se deberán usar Bases de datos para almacenar información. El usuario deberá ser capaz de registrar, modificar, eliminar y visualizar en un RecyclerView esa información con un adaptador personalizado (Un CRUD completo). El modelo de datos de la aplicación estará compuesto, al menos, de 3 clases. -
- `Funcionalidad 3`: La aplicación contará con un menú de opciones o ActionBar desde donde se podrá acceder a las acciones que el usuario pueda realizar en cada Activity.  -
- `Funcionalidad 4`: Añadir alguna función que interactúe con otras aplicaciones del dispositivo (cámara, contactos, . . .) -
- `Funcionalidad 5`: Se mostrará información útil para la aplicación en un mapa (GoogleMaps o MapBox) de forma que el usuario pueda interactuar con el mismo para llevar a cabo alguna acción de utilidad para la aplicación -

## :hammer: Puntos Extras
- `Funcionalidad 6`: Utilizar diálogos siempre que sea necesario (al modificar o eliminar información, por ejemplo) -
- `Funcionalidad 7`: Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo -
- `Funcionalidad 8`: Utilizar imágenes como atributos de algún objeto (y almacenarlo en la base de datos) -
