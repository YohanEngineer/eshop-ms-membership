# On spécifie l'image de base de laquelle on part pour construire. 
# En l'occurence une image avec un JDK 11.
FROM adoptopenjdk/openjdk11

# On spécifie le maintainer de l'image.
MAINTAINER Yohan Bouali

# On crée un volume qui contiendra des données.
VOLUME ["/eshop-ms-membership"]

# On se place dans le volume créé
WORKDIR /eshop-ms-membership

# On créer un argument qui correspond au chemin où se trouve notre jar.
ARG JAR_FILE=target/eshop-ms-membership-1.0.0-SNAPSHOT.jar

# On copie le jar dans l'image et on renomme le jar en app.jar.
COPY ${JAR_FILE} app.jar  

# On spécifie une commande qui sera executée à la création d'un container utilisant
# notre image. 
CMD ["java","-jar", "app.jar"]

# On indique que les containers utilisant cette image écoute sur ce port.
EXPOSE 8070
