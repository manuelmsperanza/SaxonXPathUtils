#Create a new project
mvn archetype:generate -Dfilter="org.apache.maven.archetypes:maven-archetype-quickstart" -DgroupId="com.hoffnungland" -DartifactId=SaxonXPathUtils -Dpackage="com.hoffnungland.xpath" -Dversion="1.0.1-SNAPSHOT"

#Build settings
##Add prerequisites

	<prerequisites>
		<maven>3.0.5</maven>
	</prerequisites>

Update to java 1.8<br>
	
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.source.version>1.8</java.source.version>
		<java.target.version>1.8</java.target.version>
	</properties>

##Configure the plugins
	
	<build>
		<pluginManagement><!-- lock down plugins versions to avoid using Maven 
				defaults (may be moved to parent pom) -->
			<plugins>
				<plugin>
					<artifactId>maven-clean-plugin</artifactId>
					<version>3.1.0</version>
				</plugin>
				<!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
				<plugin>
					<artifactId>maven-resources-plugin</artifactId>
					<version>3.1.0</version>
				</plugin>
				<plugin>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.8.0</version>
					<configuration>
						<encoding>UTF-8</encoding>
						<source>${java.source.version}</source>
						<target>${java.target.version}</target>
					</configuration>
				</plugin>
				<plugin>
					<artifactId>maven-surefire-plugin</artifactId>
					<version>3.0.0-M2</version>
				</plugin>
				<plugin>
					<artifactId>maven-jar-plugin</artifactId>
					<version>3.1.1</version>
				</plugin>
				<plugin>
					<artifactId>maven-install-plugin</artifactId>
					<version>3.0.0-M1</version>
				</plugin>
				<plugin>
					<artifactId>maven-deploy-plugin</artifactId>
					<version>3.0.0-M1</version>
				</plugin>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-enforcer-plugin</artifactId>
					<version>3.0.0-M2</version>
				</plugin>
			</plugins>
		</pluginManagement>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-enforcer-plugin</artifactId>
				<executions>
					<execution>
						<id>enforce-maven</id>
						<goals>
							<goal>enforce</goal>
						</goals>
						<configuration>
							<rules>
								<requireMavenVersion>
									<version>3.0.5</version>
								</requireMavenVersion>
								<requireJavaVersion>
									<version>1.8.0</version>
								</requireJavaVersion>
							</rules>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
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


#add .gitignore to mandatory empty directory
	# Ignore everything in this directory
	*
	# Except this file
	!.gitignore
