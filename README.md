# Projet Pharma8 - Application de Gestion de Médicaments (Spring XML, HikariCP, JdbcTemplate)

Ce dépôt contient la huitième itération du projet "Pharma". Cette version majeur **introduit l'utilisation de `JdbcTemplate` de Spring** pour simplifier la couche d'accès aux données (DAO), rendant le code plus propre et moins verbeux pour les opérations JDBC. L'externalisation des propriétés de la base de données via `datasource.properties` et l'utilisation de HikariCP sont maintenues.

## Contexte

- **Pharma1-6** : Progression de l'injection de dépendances, de la configuration Spring et de l'intégration initiale de la base de données.
- **Pharma7** : Introduction de l'externalisation des informations sensibles de la base de données dans `datasource.properties` pour une meilleure sécurité et configurabilité.
- **Pharma8** : **Simplification de la couche DAO via `JdbcTemplate`** :
  - Les méthodes CRUD du `MedicamentDao` utilisent désormais `JdbcTemplate` pour exécuter les requêtes SQL, éliminant le besoin de gérer manuellement `Connection`, `Statement`, et `ResultSet`.
  - Le `JdbcTemplate` est configuré via Spring et reçoit le `DataSource` (`HikariDataSource`).
  - Le fichier `datasource.properties` est toujours exclu du contrôle de version (`.gitignore`).
  - L'application continue d'utiliser HikariCP pour un pool de connexions performant et interagit avec une base de données MySQL réelle.
  - Log4j est toujours intégré pour la journalisation.

## Fonctionnalités (Démo 8)

- **Gestion des Médicaments** : Opérations CRUD (Création, Lecture, Mise à jour, Suppression) entièrement fonctionnelles et persistées dans une base de données MySQL.
- **Configuration de la Base de Données via Fichier de Propriétés** :
  - Les détails de connexion à la base de données sont définis dans `datasource.properties`.
  - Spring injecte dynamiquement ces valeurs dans le bean `HikariDataSource` via un `PropertySourcesPlaceholderConfigurer` configuré dans `demo-beans.xml`.
- **Pool de Connexions HikariCP** : Gestion efficace et performante des connexions à la base de données.
- **Conteneur Spring avec Configuration XML** : Le fichier `demo-beans.xml` définit la structure des beans et leur interconnexion.
- **Couche DAO simplifiée avec `JdbcTemplate`** : Réduit le boilerplate JDBC.
- **Gestion des Logs avec Log4j**.

## Technologies Utilisées

- Java (JDK 8 ou supérieur recommandé)
- **Maven** (pour la gestion des dépendances et du build)
- **Spring Framework** (`spring-context`, **`spring-jdbc`**)
- **HikariCP** (pool de connexions JDBC)
- **MySQL Database** (le projet attend une base de données MySQL locale).
- **MySQL Connector/J** (dépendance Maven pour le driver JDBC)
- **Log4j 2** (pour la journalisation)
  Pour tester cette application, suivez ces étapes :

1.  **Installer et Démarrer MySQL :** Assurez-vous d'avoir un serveur de base de données MySQL opérationnel et accessible sur `localhost:3306`.

2.  **Créer la Base de Données :** Connectez-vous à MySQL et créez une base de données nommée `syspharma`.

    ```sql
    CREATE DATABASE syspharma;
    USE syspharma;
    ```

3.  **Créer la Table `Medicament` :** Exécutez le script SQL suivant dans votre base de données `syspharma` :

    ```sql
    CREATE TABLE Medicament (
        id INT AUTO_INCREMENT PRIMARY KEY,
        designation VARCHAR(255) NOT NULL,
        prix DOUBLE NOT NULL,
        description TEXT,
        image VARCHAR(255),
        designationCategorie VARCHAR(255)
    );
    ```

4.  **Créer l'Utilisateur MySQL :** Créez un utilisateur MySQL et donnez-lui les droits nécessaires sur la base `syspharma`. Par exemple, si votre utilisateur est `springuser` et son mot de passe est `springpassword` :

    ```sql
    CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'springpassword';
    GRANT ALL PRIVILEGES ON syspharma.* TO 'springuser'@'localhost';
    FLUSH PRIVILEGES;
    ```

    _C'est cet utilisateur et ce mot de passe que vous configurerez dans `datasource.properties`._

5.  **Prérequis Java/Maven :**

    - Assurez-vous d'avoir le [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) installé (version 8 ou supérieure).
    - Assurez-vous d'avoir [Maven](https://maven.apache.org/download.cgi) installé et configuré.

6.  **Cloner le dépôt :**

    ```bash
    git clone [https://github.com/votre_utilisateur/Pharma8.git](https://github.com/votre_utilisateur/Pharma8.git)
    cd Pharma8
    ```

    _(**Important :** Remplacez `votre_utilisateur` par votre nom d'utilisateur GitHub réel et `Pharma8.git` par le nom de votre dépôt si différent.)_

7.  **Créer le fichier `datasource.properties` localement :**

    - Créez un nouveau fichier nommé `datasource.properties` dans le dossier `src/main/resources`.
    - Ajoutez-y le contenu suivant, en remplaçant les placeholders par vos informations MySQL réelles :
      ```properties
      jdbc.driverClassName=com.mysql.cj.jdbc.Driver
      jdbc.url=jdbc:mysql://localhost:3306/syspharma
      jdbc.username=springuser # REMPLACEZ PAR VOTRE UTILISATEUR MYSQL
      jdbc.password=springpassword # REMPLACEZ PAR VOTRE MOT DE PASSE MYSQL
      hikari.maximumPoolSize=10
      hikari.minimumIdle=2
      ```
    - **(Optionnel mais recommandé)** : Pour référence, vous pouvez créer un fichier `datasource.properties.example` dans le même dossier `src/main/resources` (qui lui, sera committé sur Git) avec des placeholders pour indiquer la structure attendue.

8.  **Vérifier et mettre à jour la configuration XML (`demo-beans.xml`) :** \* Ouvrez `src/main/resources/demo-beans.xml`. Il doit maintenant inclure la définition du bean `JdbcTemplate` et l'injection dans le `MedicamentDao`.

    ````xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans)"
              xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
              xmlns:context="[http://www.springframework.org/schema/context](http://www.springframework.org/schema/context)"
              xsi:schemaLocation="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans) [http://www.springframework.org/schema/beans/spring-beans.xsd](http://www.springframework.org/schema/beans/spring-beans.xsd)
                  [http://www.springframework.org/schema/context](http://www.springframework.org/schema/context) [http://www.springframework.org/schema/context/spring-context.xsd](http://www.springframework.org/schema/context/spring-context.xsd)">

              <context:property-placeholder location="classpath:datasource.properties"/>

              <bean id="dataSourceSk" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
                <property name="driverClassName" value="<span class="math-inline">\{jdbc\.driverClassName\}" /\>

    <property name\="jdbcUrl" value\="</span>{jdbc.url}" />
    <property name="username" value="<span class="math-inline">\{jdbc\.username\}" /\>
    <property name\="password" value\="</span>{jdbc.password}" />
    <property name="maximumPoolSize" value="<span class="math-inline">\{hikari\.maximumPoolSize\}" /\>
    <property name\="minimumIdle" value\="</span>{hikari.minimumIdle}" />
    </bean>

              <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
                <property name="dataSource" ref="dataSourceSk" />
              </bean>

              <bean id="medicamentDao" class="com.sidoCop.sysPharma.dao.MedicamentDao" init-method="initialisation" destroy-method="destruction">
                <property name="jdbcTemplate" ref="jdbcTemplate"></property> </bean>

              <bean id="serviceMedicament" class="com.sidoCop.sysPharma.service.ServiceMedicament" init-method="initialisation" destroy-method="destruction">
                <property name="imedicamentDao" ref="medicamentDao" />
              </bean>

            </beans>
            ```
            *Assurez-vous que l'espace de nommage `context` est bien déclaré dans les balises `<beans>` (`xmlns:context` et `xsi:schemaLocation` pour `spring-context.xsd`).*

    ````

9.  **Construire le projet et télécharger les dépendances (via Maven) :**

    ```bash
    mvn clean install
    ```

    Cela compilera le code et téléchargera les dépendances nécessaires.

10. **Exécuter l'application (depuis l'IDE) :**
    - Importez le projet `Pharma8` dans votre IDE (IntelliJ IDEA, Eclipse, VS Code) comme un projet Maven existant.
    - Exécutez la classe `com.sidoCop.sysPharma.launcher.Laucher` en tant qu'application Java.
    - Vous devriez voir les messages de console indiquant le chargement du contexte Spring, l'utilisation du pool de connexions HikariCP, et les interactions réelles avec la base de données via `JdbcTemplate`. Les logs de Log4j devraient également s'afficher.

## Prochaines Étapes Possibles (Après Pharma8)

- **Migration vers Spring Data JPA / Hibernate** : Pour une approche ORM (Object-Relational Mapping) qui abstrait complètement la couche JDBC, permettant une gestion des données orientée objets et réduisant encore plus le code DAO.
- **Introduction des profils Spring** : Pour gérer différentes configurations (développement, production, test) plus facilement, sans avoir à modifier le fichier `datasource.properties` manuellement.
- **Passer à la configuration Java-based de Spring** : Remplacer le `demo-beans.xml` par des classes de configuration Java (`@Configuration`, `@Bean`, `@ComponentScan`, `@PropertySource`).

---

**Auteur :** Sidonie 
- sidoniedjuissifohouo@gmail.com
- www.linkedin.com/in/sidonie-djuissi-fohouo

**Date :** 12 juin 2025

