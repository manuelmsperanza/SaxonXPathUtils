#Create a new project
mvn archetype:generate -DarchetypeCatalog=http://repo.maven.apache.org/maven2/archetype-catalog.xml -Dfilter=maven-archetype-quickstart -DgroupId=me.hoffnungland -DartifactId=SaxonXPathUtils -Dpackage=me.hoffnungland.xpath -Dversion=1.0.1-SNAPSHOT

#Build settings
##Add prerequisites

	<prerequisites>
		<maven>3.0</maven>
	</prerequisites>
##Configure the compiler
Update to java 1.7<br>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.source.version>1.7</java.source.version>
		<java.target.version>1.7</java.target.version>
	</properties>
	<build>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-compiler-plugin</artifactId>
					<!-- version>3.5.1</version -->
					<configuration>
						<encoding>UTF-8</encoding>
						<source>${java.source.version}</source>
						<target>${java.target.version}</target>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>

#Relationship
##Add dependencies
Update jUnit, add log4j, jdbc, saxon<br>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.apache.logging.log4j</groupId>
				<artifactId>log4j-bom</artifactId>
				<version>2.6.2</version>
				<scope>import</scope>
				<type>pom</type>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<dependencies>
		<!-- https://mvnrepository.com/artifact/junit/junit -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
		<!-- https://maven.oracle.com -->
		<dependency>
			<groupId>com.oracle.jdbc</groupId>
			<artifactId>ojdbc7</artifactId>
			<version>12.1.0.2</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
		</dependency>
		<!-- https://mvnrepository.com/artifact/net.sf.saxon/Saxon-HE -->
		<dependency>
			<groupId>net.sf.saxon</groupId>
			<artifactId>Saxon-HE</artifactId>
			<version>9.7.0-7</version>
		</dependency>
	</dependencies>

#Backup
##Create assembly configuration file backup.xml
[descriptorRef src](http://maven.apache.org/plugins/maven-assembly-plugin/descriptor-refs.html#src)
##Package the source code in a zip file
mvn clean assembly:single -Ddescriptor=src/main/assembly/backup.xml -DfinalName=${project.artifactId} -DoutputDirectory=${user.home}