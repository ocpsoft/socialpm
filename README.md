By using or contributing to SocialPM, you hereby agree to the terms defined in LICENCE and COPYING.

System Requirements
==================
* Java 6 or higher
* JBoss AS 7 or higher
* A positive outlook on life

Development Username/Password is:
=================================
lincoln/password

Building and deploying
=====================
From the root directory of SocialPM, run a complete maven build:
* `cd socialpm/`
* `mvn clean install`
* Deploy `gwt/target/socialpm-gwt.war` (If developing SocialPM, you should do this from your IDE - enable automatic publishing)

Developing in JBoss Tools
=========================

* Install Google Eclipse Plugins (repository comes with JBoss Tools)
* Run socialpm-gwt/ on JBossAS 7.x (see last step in "Building and Deploying"
* In a terminal, from the gwt/ sub-directory, run `mvn gwt:run`
* Open http://localhost:8080/socialpm-gwt/?gwt.codesvr=127.0.0.1:9997 in Chrome
* When prompted, install the Google Dev Mode Chrome Plugin
* You can now modify client code and refresh browser to see changes
* Changes made to server side code may still require a re-deployment if modifying services or beans (you can use something like JRebel to avoid this)

DEPLOYMENT ISSUES
=================
* Caused by: org.apache.maven.project.DependencyResolutionException: Could not resolve dependencies for project com.ocpsoft.socialpm:socialpm-gwt:war:1.0.0-SNAPSHOT: Failure to find com.ocpsoft.socialpm:socialpm-model:jar:1.0.0-SNAPSHOT

You did not run a full build of all project modules. Run `mvn clean install` from the `socialpm/` root directory.

* java.lang.ClassNotFoundException: org.jcp.xml.dsig.internal.dom.XMLDSigRI on JBoss AS 7.1.1.Final - https://community.jboss.org/message/723241#723241
