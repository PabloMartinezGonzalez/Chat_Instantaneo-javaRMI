# Chat_de_Texto-ClienteServidor
Chat instantáneo de texto modelo "WhatsApp" siguiendo el modelo cliente servidor programado mediante java utilizando base de datos.

Aplicación distribuida que consiste en un sistema de mensajería instantánea utilizando JAVA RMI.
Existe un servidor que aceptará conexiones de múltiples clientes a la vez.
Los clientes pueden darse de alta como usuarios, establecer amigos entre sí, y chatear con ellos.
En el momento en el que un cliente se conecta, a todos sus amigos se les informa instantáneamente mediante "Callbacks". 
Cada cliente dispone de un sistema de envío y recepción de mensajes, los cuales no pasan a través de conexiones entre el servidor.
Los clientes tienen grupos de amistad, de forma que pueden hacerse amigos a partir de los nombres de usuario de los demás. 
Mediante la base de datos disponible, se almacena toda la información referente a los usuarios, solicitudes de amistad, y gruopos de amistad.

La aplicación cuenta con una interfaz gráfica donde se realizarse todas las acciones por parte de los usuarios.

EL proyecto contiene el fichero "baseDatos.properties", donde se encuentra toda la configuración de la base de datos. Dentro de la carpeta ScriptsBD, se encuentran los scripts SQL para la creación tanto de las tablas como el script para insertar datos de ejemplo.
La carpeta Callback contiene los archivos java para implementar el sistema de callbacks con javaRMI, mientras que el resto de archivos se encuentran en la carpeta src.
