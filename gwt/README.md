Developing in JBoss Tools
=========================

* Install Google Eclipse Plugins (repository comes with JBoss Tools)
* Run socialpm-gwt/ on JBossAS 7.x (publish changes automatically)
* In a terminal, from the gwt/ sub-directory, run `mvn gwt:run`
* Open http://localhost:8080/socialpm-gwt/?gwt.codesvr=127.0.0.1:9997 in Chrome
* When prompted, install the Google Dev Mode Chrome Plugin
* You can now modify client code and refresh browser to see changes
* Changes made to server side code may still require a re-deployment if modifying services or beans (you can use something like JRebel to avoid this)
